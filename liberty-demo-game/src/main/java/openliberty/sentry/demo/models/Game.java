package openliberty.sentry.demo.models;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.ws.rs.ProcessingException;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.eclipse.microprofile.rest.client.RestClientBuilder;

import openliberty.sentry.demo.client.LeaderboardClient;
import openliberty.sentry.demo.client.UnknownUrlException;
import openliberty.sentry.demo.client.UnknownUrlExceptionMapper;

@ApplicationScoped
public class Game {

	GameSession session;
	boolean isRanked;
	
	private final String DEFAULT_PORT = "9082";
	
	private final String DEFAULT_HOST = "leaderboard";
	
	public boolean isCurrentSessionRunning() {
		return session.isRunning();
	}

	public void newGameSession(String pid, boolean ranked) throws Exception {
		session = new GameSession(pid, ranked);
		session.startGameSession();
		isRanked = ranked;
	}

	public void stopCurrentSession() throws Exception {
		session.deactivateTarets();
		if (session.isRankedGame) {
			GameStat stat = new GameStat();
			stat.setPid(session.getPID());
			stat.setScore(session.getSessionScore());
			writeStatWithGivenHostName(DEFAULT_HOST, stat);
		}
	}

	public void startSession() throws Exception {
		session.activateTargets();
	}

	public int getScore() {
		return session.score;
	}
	
	public boolean isRanked() {
		return isRanked;
	}
	
	public List<HashMap> getTopScores(){
		return getTopScoreWithGivenHostName(DEFAULT_HOST);
	}

	public void waitForHitUpdate() {
		session.waitForHitUpdate();
	}

	// tag::builder[]
	private void writeStatWithGivenHostName(String hostname, GameStat gamestat) {
		String customURLString = "http://" + hostname + ":" + DEFAULT_PORT + "/liberty-demo-leaderboard/app/leaderboard";
		URL customURL = null;
		System.out.println("customURLString is: " + customURLString);
		try {
			Jsonb jsonb = JsonbBuilder.create(); 
			customURL = new URL(customURLString);
			LeaderboardClient customRestClient = RestClientBuilder.newBuilder().baseUrl(customURL)
					.register(UnknownUrlExceptionMapper.class).build(LeaderboardClient.class);
			String jsonStat = jsonb.toJson(gamestat);
			customRestClient.writeStat(jsonStat);
		} catch (ProcessingException ex) {
			handleProcessingException(ex);
		} catch (UnknownUrlException e) {
			System.err.println("The given URL is unreachable.");
		} catch (MalformedURLException e) {
			System.err.println("The given URL is not formatted correctly.");
		}
	}
	
	private List<HashMap> getTopScoreWithGivenHostName(String hostname){
		String customURLString = "http://" + hostname + ":" + DEFAULT_PORT + "/liberty-demo-leaderboard/app/leaderboard";
		URL customURL = null;
		System.out.println("customURLString is: " + customURLString);
		try {
			customURL = new URL(customURLString);
			LeaderboardClient customRestClient = RestClientBuilder.newBuilder().baseUrl(customURL)
					.register(UnknownUrlExceptionMapper.class).build(LeaderboardClient.class);
			return customRestClient.listLeaderBoard();
			
		} catch (ProcessingException ex) {
			handleProcessingException(ex);
		} catch (UnknownUrlException e) {
			System.err.println("The given URL is unreachable.");
		} catch (MalformedURLException e) {
			System.err.println("The given URL is not formatted correctly.");
		}
		return null;
	}

	private void handleProcessingException(ProcessingException ex) {
		Throwable rootEx = ExceptionUtils.getRootCause(ex);
		if (rootEx != null && rootEx instanceof UnknownHostException) {
			System.err.println("The specified host is unknown.");
		} else {
			throw ex;
		}
	}

}

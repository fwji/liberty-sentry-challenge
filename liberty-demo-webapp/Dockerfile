# Download latest Open Liberty from DockerHub
FROM open-liberty:latest

# Create a Liberty server instance called "liberty-demo-webappServer"
RUN /opt/ol/wlp/bin/server create liberty-demo-webappServer

# Copy over the customized server.xml to the Liberty server
COPY src/main/liberty/config/server.xml /opt/ol/wlp/usr/servers/liberty-demo-webappServer/

# Copy over the jvm.options to the Liberty server
#COPY src/main/liberty/config/resources/jvm.options /opt/ol/wlp/usr/servers/liberty-demo-webappServer/

#Copy over the bootstrap.properties to the Liberty Server
COPY bootstrap.properties /opt/ol/wlp/usr/servers/liberty-demo-webappServer/

# Copy the SentryTargetChallenge-1.0-SNAPSHOT.war  WAR application to the Liberty server
COPY target/liberty-demo-webapp*.war /opt/ol/wlp/usr/servers/liberty-demo-webappServer/apps/liberty-demo-webapp.war

# Set Path Shortcuts, redirect all Liberty logs to "/logs"
ENV LOG_DIR=/logs

# Start the Liberty server
CMD ["/opt/ol/wlp/bin/server", "run", "liberty-demo-webappServer"]
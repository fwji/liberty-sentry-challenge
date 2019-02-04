# liberty-sentry-challenge

## Introduction
Liberty Sentry Challenge is a fun and competitive laser tagging game build using Open Liberty, Arduinos and other IoT devices and circuitries.

## Prerequesites

1. Latest docker is installed
2. Git is installed
3. Bash Terminal or Git bash on Windows

## Local Deployment Instruction

1. Git clone the repository
```
git clone git@github.com:fwji/liberty-sentry-challenge.git
```

2. Run the following command
```
docker network create -d bridge --subnet 172.25.0.0/16 demo_net --attachable
```

3. Run the following command
```
./install.sh
```

## Start/Stop the Game

1. Start the Game
```
./start.sh
```

2. Go to the following URL to access the game.
```
http://localhost:9048
```

3. Stop the Game
```
./stop.sh
```

## Uninstallation
```
./nuke_all.sh
```

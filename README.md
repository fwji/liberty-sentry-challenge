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

2. run the following command
```
docker network create -d bridge --subnet 172.25.0.0/16 demo_net --attachable
```

2. run the following command
```
./deploy_admin.sh
./deploy_nginx.sh
./deploy_leaderboard.sh
./deploy_game.sh
```

## Uninstall
```
./nuke_all.sh
```

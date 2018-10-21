#!/bin/bash
docker build --tag=liberty-demo-webapp liberty-demo-webapp
docker run --network=demo_net --ip=172.25.3.5 -itd --name webapp -p 9080:9080 -p 9443:9443  liberty-demo-webapp
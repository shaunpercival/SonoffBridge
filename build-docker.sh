#/bin/bash
docker kill tomcat
docker rm tomcat
date > marker && docker build -t tomcat-ws:v1 .
#docker run -it -d -p9080:9080 -p9443:9443 --name tomcat  -v /home/bearingpoint/wk-devops/SonoffWebSockets/build/tmp:/sonoff tomcat-ws:v1
#docker run -it -d --net=host  --name tomcat  -v /home/bearingpoint/wk-devops/SonoffWebSockets/build/tmp:/sonoff tomcat-ws:v1
docker run -it -d --net=host -v /tmp/sonoff-webapps:/tmp/sonoff-webapps --name tomcat  tomcat-ws:v1

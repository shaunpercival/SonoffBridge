https://github.com/Unidata/tomcat-docker

https://certbot.eff.org/lets-encrypt/ubuntubionic-apache

sudo apt-get update
sudo apt-get install software-properties-common
sudo add-apt-repository universe
sudo add-apt-repository ppa:certbot/certbot
sudo apt-get update
sudo apt-get install python-certbot-apache

Executing: /tmp/tmp.Rv1Q1bcmhd/gpg.1.sh --keyserver
hkp://keyserver.ubuntu.com:80
--recv-keys
75BCA694
gpg: requesting key 75BCA694 from hkp server keyserver.ubuntu.com
gpg: key 75BCA694: public key "Launchpad PPA for certbot" imported
gpg: Total number processed: 1
gpg:               imported: 1  (RSA: 1)



# Clean ou an old java

sudo update-alternatives --remove "java" "/usr/lib/jvm/java-8-openjdk-amd64/bin/java"
sudo update-alternatives --remove "javac" "/usr/lib/jvm/java-8-openjdk-amd64/bin/javac"
sudo update-alternatives --remove "javaws" "/usr/lib/jvm/java-8-openjdk-amd64/bin/javaws"
sudo rm -r /usr/lib/jvm/java-8-openjdk-amd64

sudo update-alternatives --config java


https://stackoverflow.com/questions/32758465/ubuntu-oracle-jdk-8-is-not-installed
down vote
Try this,

First update the apt-get repos

$ sudo apt-get update
Add the java 8 repo to apt-get

$ sudo add-apt-repository ppa:webupd8team/java
Again update the apt-get repo

$ sudo apt-get update
Finally install java 8

$ sudo apt-get install oracle-java8-installer



docker run -it -d -p 8085:8080 -p 8086:8443  --name tomcat4 tomcat-ws:v1
docker exec -it tomcat /bin/bash

docker run -it -d --net=host  --name tomcat  -v /home/bearingpoint/wk-devops/SonoffWebSockets/build/tmp:/sonoff tomcat-ws:v1
docker kill tomcat
docker rm tomcat



scp -r /home/bearingpoint/wk-devops/SonoffWebSockets/build-docker.sh  pi@192.168.1.180:/home/pi/sonoff-docker
scp -r /home/bearingpoint/wk-devops/SonoffWebSockets/build-docker.sh  pi@192.168.1.180:/home/pi/sonoff-docker
scp -r /home/bearingpoint/wk-devops/SonoffWebSockets/src/docker-resources pi@192.168.1.180:/home/pi/sonoff-docker
scp -r /home/bearingpoint/wk-devops/SonoffWebSockets/src/main/resources/ pi@192.168.1.180:/home/pi/sonoff-docker
scp -/home/bearingpoint/wk-devops/SonoffWebSockets/build/libs/sonoffwebsockets.war pi@192.168.1.180:/tmp/sonoff-webapps


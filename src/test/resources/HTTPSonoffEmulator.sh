#!/usr/bin/env bash

jre=/usr/lib/jvm/java-1.9.0-openjdk-amd64/bin/java

build_dir=/home/bearingpoint/wk-devops/SonoffWebSockets/build/classes/java/main/
lib_dir=/home/bearingpoint/wk-devops/SonoffWebSockets/lib/*
test_dir=/home/bearingpoint/wk-devops/SonoffWebSockets/build/classes/test
#provider_jars=/opt/glassfish5/glassfish/modules/*
//og4j_jar=/home/bearingpoint/.gradle/caches/modules-2/files-2.1/org.apache.logging.log4j/log4j-api/2.11.1/268f0fe4df3eefe052b57c87ec48517d64fb2a10/log4j-api-2.11.1.jar


//onejar=/home/bearingpoint/.gradle/caches/modules-2/files-2.1/org.mock-server/mockserver-client-java/5.5.0/673649e76d7a768d3b2ebe5299092cb505501cab/mockserver-client-java-5.5.0-sources.jar
//twojar=/home/bearingpoint/.gradle/caches/modules-2/files-2.1/org.mock-server/mockserver-client-java/5.5.0/ecc60e5cabd75d1fb4f612e137ecde366f875582/mockserver-client-java-5.5.0.jar


//provider_dir=/opt/glassfish5/glassfish/modules
//provider_libs=$(find "$provider_dir" -name '*.jar' -printf '%p:' | sed 's/:$//')

//echo $provider_libs


proj_cp=$log4j_jar:$onejar:$twojar:$build_dir

#printenv;
echo "cp" + $proj_cp
echo "hi shaun"
$jre -version
$jre -cp $proj_cp org.sonoff.client.HTTPSonoffMockServer
#!/usr/bin/env bash

jre=/usr/lib/jvm/java-1.9.0-openjdk-amd64/bin/java

build_dir=/home/bearingpoint/wk-devops/SonoffWebSockets/build/classes/java/main/
lib_dir=/home/bearingpoint/wk-devops/SonoffWebSockets/lib/*
test_dir=/home/bearingpoint/wk-devops/SonoffWebSockets/build/classes/test
#provider_jars=/opt/glassfish5/glassfish/modules/*
log4j_jar=/home/bearingpoint/.gradle/caches/modules-2/files-2.1/org.apache.logging.log4j/log4j-api/2.11.1/268f0fe4df3eefe052b57c87ec48517d64fb2a10/log4j-api-2.11.1.jar

provider_dir=/opt/glassfish5/glassfish/modules
provider_libs=$(find "$provider_dir" -name '*.jar' -printf '%p:' | sed 's/:$//')

echo $provider_libs


proj_cp=$lib_dir:$build_dir:$provider_libs:$CLASSPATH:$log4j_jar

#printenv;
echo $proj_cp

$jre -version
$jre -cp $proj_cp org.sonoff.client.SonoffClient -verbose
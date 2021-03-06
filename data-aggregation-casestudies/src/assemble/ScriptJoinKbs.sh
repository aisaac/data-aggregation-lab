#!/bin/sh

export CLASSPATH=
for jar in `ls lib/*.jar`
do
  export CLASSPATH=$CLASSPATH:$jar
done
export CLASSPATH=$CLASSPATH

java -Djsse.enableSNIExtension=false -Dsun.net.inetaddr.ttl=0 -Xmx4G -cp casestudy:classes:$CLASSPATH inescid.dataaggregation.casestudies.ontologies.reasoning.ScriptJoinKbs data $1 $2 $3 $4 

cd D:\Documents\Kurse\DistributedSystems\Teaching\11_JMS\11_JMS\bin

set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;..\lib\activemq-all-5.8.0.jar
set CLASSPATH=%CLASSPATH%;..\lib\log4j-1.2.17.jar
set CLASSPATH=%CLASSPATH%;..\lib\slf4j-api-1.6.6.jar




java ch.fhnw.ds.jms.topic.durable.JmsMsgPublisher

java ch.fhnw.ds.jms.topic.durable.JmsMsgListener key1
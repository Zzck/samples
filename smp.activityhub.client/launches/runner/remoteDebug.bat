java -Dfelix.config.properties=file:felix/config.ini -Dfelix.system.properties=file:felix/system.properties -Xdebug -Xnoagent -Djava.compiler=NONE -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=5006 -jar bundles/org.apache.felix.main_*.jar

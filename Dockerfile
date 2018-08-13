FROM a142857/mycompany.tomcat:1.0-SNAPSHOT

RUN apt-get update && apt-get -y upgrade
COPY target/notifier.war /usr/local/tomcat/webapps

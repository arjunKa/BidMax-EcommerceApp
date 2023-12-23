FROM tomcat:10.1-jre21 
#COPY /target/Bezos-0.0.1-SNAPSHOT.war /target/app.war
#COPY /src/main/webapp /src/main/webapp

COPY /target/Bezos-0.0.1-SNAPSHOT.war /usr/local/tomcat/webapps/Bezos.war


EXPOSE 8080


CMD ["catalina.sh", "run"]
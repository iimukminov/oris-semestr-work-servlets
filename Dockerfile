FROM tomcat:9.0-jdk21-temurin

ENV CATALINA_OPTS="-Xms512m -Xmx1024m"

RUN rm -rf /usr/local/tomcat/webapps/ROOT

COPY target/service-desk.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

CMD ["catalina.sh", "run"]

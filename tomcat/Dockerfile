FROM openjdk:8-jre
LABEL maintainer="chitrarath.kumar@volantetech.net"
ENV CATALINA_HOME /usr/local/tomcat
ENV PATH $CATALINA_HOME/bin:$PATH
RUN mkdir -p "$CATALINA_HOME"
WORKDIR $CATALINA_HOME
COPY apache-tomcat-8.5.20 $CATALINA_HOME
COPY wait-for-it.sh $CATALINA_HOME/wait-for-it.sh
RUN chmod +x $CATALINA_HOME/wait-for-it.sh
EXPOSE 8080
CMD ["catalina.sh", "run"]

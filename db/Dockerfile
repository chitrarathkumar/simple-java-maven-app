FROM openjdk:8-jre
LABEL maintainer="chitrarath.kumar@volantetech.net"
ENV DB_HOME /usr/local/db
ENV PATH $DB_HOME:$PATH
RUN mkdir -p "$DB_HOME"
WORKDIR $DB_HOME
COPY hsql $DB_HOME
COPY wait-for-it.sh $DB_HOME/wait-for-it.sh
RUN chmod +x $DB_HOME/wait-for-it.sh
EXPOSE 9001
CMD ["sh","-c","if [ ! -d database ]; then rm -rf database; fi; rundb.sh"]

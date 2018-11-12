FROM openjdk:8-jre
LABEL maintainer="chitrarath.kumar@volantetech.net"
ENV KAFKA_HOME /usr/local/kafka
ENV PATH $KAFKA_HOME/bin:$PATH
RUN mkdir -p "$KAFKA_HOME"
WORKDIR $KAFKA_HOME
COPY kafka_2.11-0.10.1.0 $KAFKA_HOME
COPY wait-for-it.sh $KAFKA_HOME/wait-for-it.sh
RUN chmod +x $KAFKA_HOME/wait-for-it.sh
EXPOSE 2181
EXPOSE 9092

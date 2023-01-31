FROM postgres:14

RUN apt-get update && apt-get install -y openjdk-17-jre openjdk-17-jdk
RUN mkdir /jar
COPY --chown=postgres:postgres schema.sql /docker-entrypoint-initdb.d/
COPY target/RabbitTracker-0.0.1-SNAPSHOT.jar /jar
COPY docker/java-rabbit.sh /usr/local/bin/
COPY docker/multi-service-entrypoint.sh /usr/local/bin/

ENTRYPOINT ["multi-service-entrypoint.sh"]

CMD ["rabbit-habit"]
EXPOSE 4278

ENV POSTGRES_PASSWORD=postgres
ENV FIREBASE_ID=your-firebase-projectId
FROM openjdk:8-jdk-alpine

ENV APP_FILE msvc-accounts-0.0.1-SNAPSHOT.jar
ENV APP_HOME /app
ENV APP_SOURCE ./build/libs
COPY $APP_SOURCE/$APP_FILE $APP_HOME/
RUN mkdir -p $APP_HOME/src/main/resources
WORKDIR $APP_HOME
ENTRYPOINT ["sh", "-c"]
CMD ["exec java -Djava.security.egd=file:/dev/./urandom -jar $APP_FILE"]
EXPOSE 8081
FROM openjdk:8-jdk-alpine
VOLUME /tmp
ARG JAR_FILE
COPY ${JAR_FILE} app.jar
RUN apk add --no-cache tzdata
ENV TZ=Asia/Kolkata
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar", "--db.username=stkdb", "--db.password=stkdb", "--db.host=db", "--db.schema=stkdb", "--tracker.host=stock-tracker"]
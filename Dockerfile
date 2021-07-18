FROM gradle:7.1.1-jdk11 AS build
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src
RUN gradle build --no-daemon

FROM openjdk:11

EXPOSE 8080

WORKDIR /app

COPY --from=build /home/gradle/src/build/libs/tic-tac-toe-api-1.0.jar /app/tic-tac-toe-api-1.0.jar

CMD ["java", "-jar", "/app/tic-tac-toe-api-1.0.jar"]

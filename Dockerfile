FROM openjdk:11

EXPOSE 8080

WORKDIR /app

COPY build/libs/tic-tac-toe-api-1.0.jar /app/tic-tac-toe-api-1.0.jar

CMD ["java", "-jar", "/app/tic-tac-toe-api-1.0.jar"]
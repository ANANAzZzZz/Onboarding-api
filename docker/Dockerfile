FROM openjdk:21-jdk-slim
ARG JAR_FILE
COPY ${JAR_FILE} /app.jar
EXPOSE 8085
ENV SERVER_PORT=8085
CMD ["java", "-jar", "/app.jar"]
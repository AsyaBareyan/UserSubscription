FROM openjdk:17-jdk-slim

ARG JAR_FILE=target/*.jar

COPY ${JAR_FILE} application.jar

CMD apt-get update -y

ENTRYPOINT ["java", "-Xmx2048M", "-jar", "/application.jar"]

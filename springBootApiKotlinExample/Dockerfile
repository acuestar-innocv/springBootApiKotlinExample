FROM openjdk:8-jdk-alpine
RUN apk add --no-cache curl
ADD target/*jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
EXPOSE 8080
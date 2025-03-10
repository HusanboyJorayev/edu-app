FROM openjdk:17-jdk-alpine
ENV TZ=Asia/Tashkent
EXPOSE 8080
WORKDIR /app
COPY target/edu_app.jar /app/edu_app.jar
ENTRYPOINT ["java", "-jar", "/app/edu_app.jar"]

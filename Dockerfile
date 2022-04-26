FROM openjdk:11-jre-slim
COPY applications/app-demo/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]

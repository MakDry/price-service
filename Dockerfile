FROM bellsoft/liberica-openjdk-debian:17
WORKDIR /app
COPY target/dependency ./lib
COPY target/*.jar ./application.jar
ENTRYPOINT ["java", "-jar", "./application.jar"]
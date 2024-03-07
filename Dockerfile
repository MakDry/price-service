FROM maven AS build
WORKDIR /app
COPY src ./src
COPY pom.xml .
RUN mvn clean package -P prod

FROM bellsoft/liberica-openjdk-debian:17
COPY --from=build /app/target/priceservice-0.0.1-SNAPSHOT.jar /application.jar
CMD ["java", "-jar", "./application.jar"]
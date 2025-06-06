FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src

RUN chmod +x ./mvnw
RUN ./mvnw clean package -DskipTests

# Runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

COPY --from=build /app/target/project-manager-0.0.1-SNAPSHOT.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]

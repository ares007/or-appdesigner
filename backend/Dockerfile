FROM maven:3.6.3-openjdk-11-slim AS build
RUN mkdir -p /workspace
WORKDIR /workspace
COPY backend/pom.xml /workspace
COPY backend/src /workspace/src
RUN mvn -B package --file pom.xml -DskipTests

FROM openjdk:11-slim
COPY --from=build /workspace/target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]
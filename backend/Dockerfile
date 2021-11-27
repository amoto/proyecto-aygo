FROM maven:3.8.4-jdk-8-slim AS build
COPY src /code/src
COPY pom.xml /code
RUN mvn -f /code/pom.xml clean package


FROM openjdk:8-jre-slim
RUN mkdir /code
COPY --from=build /code/target/*.jar /code
EXPOSE 8080
ENTRYPOINT ["sh","-c","java -jar /code/*.jar"]

FROM eclipse-temurin:21-jdk AS build
RUN apt-get update && apt-get install -y findutils

WORKDIR /app
COPY build.gradle .
COPY settings.gradle .
COPY src src

COPY gradlew .
COPY gradle gradle

RUN chmod +x ./gradlew
RUN ./gradlew build -x test

FROM eclipse-temurin:21-jre
VOLUME /tmp

COPY --from=build /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
EXPOSE 8080
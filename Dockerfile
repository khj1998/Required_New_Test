FROM gradle:8.5-jdk17 AS builder
WORKDIR /build

COPY build.gradle settings.gradle /build/
COPY src /build/src

RUN gradle build --no-daemon -x test

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=builder /build/build/libs/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]

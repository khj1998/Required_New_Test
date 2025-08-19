FROM gradle:8.5-jdk17 AS builder
WORKDIR /build

COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle

RUN ./gradlew dependencies --no-daemon

COPY src ./src
COPY libs ./libs

RUN ./gradlew clean build --no-daemon -x test

FROM openjdk:17-jdk-slim
WORKDIR /app

COPY --from=builder /build/build/libs/*.jar ./app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
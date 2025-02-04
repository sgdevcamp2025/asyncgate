# 빌드 단계
FROM amazoncorretto:17-alpine AS builder

WORKDIR /app

COPY . /app

RUN chmod +x ./gradlew
RUN ./gradlew clean build -x test

# 실행 단계
FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]

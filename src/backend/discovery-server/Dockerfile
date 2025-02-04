# 빌드 단계
FROM amazoncorretto:17-alpine AS builder

WORKDIR /app

# Gradle 설치
RUN apk add --no-cache gradle

COPY . /app

RUN ./gradlew clean build -x test

# 실행 단계
FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

CMD ["java", "-jar", "app.jar"]

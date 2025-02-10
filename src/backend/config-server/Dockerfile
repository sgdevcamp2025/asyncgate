# 빌드 단계
FROM amazoncorretto:17-alpine AS builder

WORKDIR /app

# Gradle 설치
RUN apk add --no-cache gradle

COPY . /app

# Gradle 빌드 실행 (gradlew 없이 Gradle 직접 실행)
RUN gradle clean build -x test

# 실행 단계
FROM amazoncorretto:17-alpine
WORKDIR /app

COPY --from=builder /app/build/libs/*.jar app.jar

CMD ["java", "-Xms128m", "-Xmx256m", "-jar", "app.jar"]

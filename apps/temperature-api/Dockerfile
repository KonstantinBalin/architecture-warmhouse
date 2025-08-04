# Этап сборки
FROM gradle:8.14.2-jdk17 AS build

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем build.gradle и настройки зависимостей
COPY build.gradle settings.gradle ./
COPY gradle gradle

# Копируем исходные коды приложения
COPY src src

# Собираем проект
RUN gradle build --no-daemon

# Этап выполнения
FROM openjdk:17-jdk-slim

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем собранный jar файл из этапа сборки
COPY --from=build /app/build/libs/*.jar app.jar

# Запускаем приложение
ENTRYPOINT ["java", "-jar", "app.jar"]
FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Копируем файл с зависимостями
COPY pom.xml .
RUN mvn dependency:go-offline -B

# Копируем исходники и собираем JAR
COPY src ./src
RUN mvn clean package -DskipTests -B

# Финальный образ с JRE
FROM eclipse-temurin:17-jre
WORKDIR /app

# Устанавливаем русскую локаль и кодировку
ENV LANG=ru_RU.UTF-8 \
    LANGUAGE=ru_RU:ru \
    LC_ALL=ru_RU.UTF-8 \
    JAVA_OPTS="-Dfile.encoding=UTF-8"

# Копируем JAR из билд-образа
COPY --from=build /app/target/*.jar app.jar

# Копируем документы прямо в образ (чтобы не искать их на диске)
COPY src/main/resources/static/help-docs /app/help-docs

# Создаём символическую ссылку, чтобы программа нашла документы
RUN mkdir -p /home/sanyamopzzz && \
    ln -sf /app/help-docs /home/sanyamopzzz/Реформаторы_Документы

EXPOSE 8080
ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS -jar app.jar"]

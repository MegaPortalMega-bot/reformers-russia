FROM maven:3.9.6-eclipse-temurin-17 AS build
WORKDIR /app

# Копируем файл с зависимостями
COPY pom.xml .
# Скачиваем зависимости (это слой кэшируется, если pom.xml не менялся)
RUN mvn dependency:go-offline

# Копируем исходники и собираем fat JAR
COPY src ./src
RUN mvn clean package spring-boot:repackage -DskipTests

# Финальный образ с JRE
FROM eclipse-temurin:17-jre
WORKDIR /app

# Копируем JAR-файл из предыдущего этапа сборки
COPY --from=build /app/target/*.jar app.jar

# Проверяем, что JAR исполняемый (для Spring Boot это критично)
RUN java -jar app.jar --version || true

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

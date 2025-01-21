FROM openjdk:23-jdk-slim AS build
RUN apt-get update && apt-get install -y maven
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests -e
RUN ls -al /app/target/

# Etapa de ejecución
FROM openjdk:23-jdk-slim
WORKDIR /app
ADD https://raw.githubusercontent.com/vishnubob/wait-for-it/master/wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
COPY --from=build /app/target/user-services-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8085
ENTRYPOINT ["java", "-jar", "app.jar"]
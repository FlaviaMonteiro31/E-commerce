FROM maven:3.8.4 AS builder
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:17
WORKDIR /app
COPY --from=builder /app/target/*.jar MS_Usuarios.jar

ENV SPRING_DATASOURCE_URL=ms-postgres
ENV PG_USER=postgres
ENV PG_PASSWORD=postgres
ENV API_SECRET=ecommercefiap2024
ENV SPRING_CLOUD=/controle-usuario

CMD ["java", "-jar", "MS_Usuarios.jar"]
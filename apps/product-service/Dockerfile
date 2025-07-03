# Stage 1: Build
FROM eclipse-temurin:21-jdk-alpine AS builder

WORKDIR /build
RUN apk add --no-cache maven
# Copia tudo do monorepo para dentro da imagem (exceto .dockerignore)
COPY . .

# Ajuste aqui para o serviço correto, substitua o caminho do pom se necessário
ARG SERVICE_PATH=apps/product-service

# Faz o build só desse serviço e suas dependências
RUN mvn clean install -DskipTests -pl ${SERVICE_PATH} -am

# Stage 2: Run
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

ARG SERVICE_PATH=apps/product-service

# Copia o JAR buildado para o container final
COPY --from=builder /build/${SERVICE_PATH}/target/*.jar app.jar

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]

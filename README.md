# Galleria Bank Backend

Backend da aplicacao Galleria Bank, feito com Spring Boot, Maven, PostgreSQL e Flyway.

## Requisitos

Antes de rodar o projeto, instale:

- Java 25
- Docker
- Docker Compose

Nao precisa instalar Maven separadamente, porque o projeto usa Maven Wrapper.

## Como rodar

### 1. Subir o banco de dados

Na raiz do projeto, rode:

```bash
docker compose up -d
```

Isso vai subir um banco PostgreSQL local com as configuracoes ja usadas pela aplicacao.

## Como buildar

No Windows:

```bash
mvnw.cmd clean package
```

No Linux ou macOS:

```bash
./mvnw clean package
```

O arquivo `.jar` sera gerado na pasta `target`.

## Como rodar o JAR

Depois de buildar, rode:

```bash
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

O banco de dados precisa estar rodando antes de iniciar o JAR.

## Swagger

Com a aplicacao rodando, acesse:

```text
http://localhost:8080/swagger-ui/index.html
```
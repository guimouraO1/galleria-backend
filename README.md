# Galleria Bank Backend

Backend da aplicação Galleria Bank, feito com Spring Boot, Maven, PostgreSQL e Flyway.

## Requisitos

Antes de rodar o projeto, instale:

- Java 25
- Docker
- Docker Compose

Não precisa instalar Maven separadamente, porque o projeto usa Maven Wrapper.

## Como rodar

### 1. Subir o banco de dados

Na raiz do projeto, rode:

```bash
docker compose up -d
```

Isso vai subir um banco PostgreSQL local com as configurações já usadas pela aplicação.

### Dados iniciais

O seed do banco já cria alguns dados para teste. Se quiser, você pode usar os usuários abaixo para acessar a aplicação:

| Login | Senha |
| --- | --- |
| `guimoura` | `myPass@01` |
| `anasouza` | `myPass@01` |

## Como buildar

No Windows:

```bash
mvnw.cmd clean package
```

No Linux ou macOS:

```bash
./mvnw clean package
```

O arquivo `.jar` será gerado na pasta `target`.

## Como rodar os testes

No Bash, Linux ou macOS:

```bash
./mvnw test
```

No Windows:

```bash
mvnw.cmd test
```

Os testes em `test/**/service` são testes unitários dos services. Eles usam mocks para simular repositories e outras dependências, então não dependem diretamente do banco para validar as regras principais de cada service.

O teste `BackendApplicationTests` é um teste de contexto da aplicação Spring. Ele sobe o contexto para verificar se a aplicação consegue inicializar com as configurações atuais.

## Como rodar o JAR

Depois de buildar, rode:

```bash
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

O banco de dados precisa estar rodando antes de iniciar o JAR.

## Swagger

Com a aplicação rodando, acesse:

```text
http://localhost:8080/swagger-ui/index.html
```

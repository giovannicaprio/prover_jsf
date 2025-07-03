# Prover JSF Application

Aplicação Java Web utilizando JSF, PrimeFaces, CDI, JPA e JMS.

## Requisitos

- Docker
- Docker Compose
- Maven 3.x (para build local)

## Executando com Docker

1. Build do projeto:
```bash
mvn clean package
```

2. Iniciar o container:
```bash
docker-compose up --build
```

A aplicação estará disponível em:
- Aplicação: http://localhost:8080/prover-jsf
- Console de Administração: http://localhost:9990
  - Usuário: admin
  - Senha: admin123

## Desenvolvimento Local (Alternativo)

Se preferir desenvolver localmente sem Docker, você precisará:

1. Java 8 JDK
2. Maven 3.x
3. WildFly 10.0.0.Final

### Configuração do Banco de Dados no WildFly

1. Adicione o driver do PostgreSQL no diretório `WILDFLY_HOME/standalone/deployments`
2. Adicione a seguinte configuração no arquivo `WILDFLY_HOME/standalone/configuration/standalone.xml`:

```xml
<datasource jndi-name="java:jboss/datasources/ProverDS" pool-name="ProverDS" enabled="true" use-java-context="true">
    <connection-url>jdbc:postgresql://localhost:5432/prover_db</connection-url>
    <driver>postgresql</driver>
    <security>
        <user-name>seu_usuario</user-name>
        <password>sua_senha</password>
    </security>
</datasource>
```

## Tecnologias Utilizadas

- Java 8
- JSF (JavaServer Faces)
- PrimeFaces 6.1
- CDI (Contexts and Dependency Injection)
- JPA + Hibernate
- JMS (Java Message Service)
- WildFly 10.0.0.Final

## Compatibilidade

A aplicação foi testada e é compatível com:
- Google Chrome
- Microsoft Edge 
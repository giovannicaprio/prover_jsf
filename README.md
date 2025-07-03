# Prover JSF Application

Aplicação Java Web utilizando JSF, PrimeFaces, CDI, JPA e JMS.

## Requisitos

- Java 8
- Maven 3.x
- WildFly 10.0.0.Final

## Configuração do Ambiente

1. Instale o Java 8 JDK
2. Instale o Maven
3. Baixe e extraia o WildFly 10.0.0.Final

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

## Compilação e Deployment

1. Clone o repositório:
```bash
git clone git@github.com:giovannicaprio/prover_jsf.git
cd prover_jsf
```

2. Compile o projeto:
```bash
mvn clean package
```

3. Deploy no WildFly:
```bash
mvn wildfly:deploy
```

## Acessando a Aplicação

Após o deployment, acesse a aplicação em:
```
http://localhost:8080/prover-jsf
```

## Compatibilidade

A aplicação foi testada e é compatível com:
- Google Chrome
- Microsoft Edge 
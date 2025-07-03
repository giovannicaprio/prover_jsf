# Prover JSF - Analisador de Frases

Aplicação Java Web para análise de frases, desenvolvida como parte do teste técnico da Prover.

## Validações Técnicas Realizadas

### 1. Fluxo da Aplicação ✅
- Interface gráfica recebe a frase do usuário
- Disparo da análise via botão
- Envio da frase para o backend
- Análise realizada no backend:
  - Cálculo de palavras distintas
  - Contagem de ocorrências por palavra
- Retorno do resultado para interface
- Exibição do resultado formatado

### 2. Backend ✅
- Processamento sincronizado (uma requisição por vez)
  - Implementado através do modificador `synchronized` no método `analisarTexto`
  - Garantia de thread safety através do escopo `@ApplicationScoped`
- Análise de texto implementada com:
  - Normalização do texto (remoção de pontuação, conversão para minúsculas)
  - Contagem de palavras distintas
  - Cálculo de ocorrências

### 3. Comunicação Frontend-Backend ✅
- Binding de componentes via Expression Language (EL)
- Exemplos de implementação:
  ```xml
  <p:inputTextarea value="#{analisadorFraseBean.frase}" />
  <p:commandButton action="#{analisadorFraseBean.analisarFrase}" />
  ```
- Atualização dinâmica da interface via AJAX

### 4. Requisitos Técnicos ✅
- **Java 8**
  - Configurado no pom.xml: `<maven.compiler.source>1.8</maven.compiler.source>`
  - Compatibilidade garantida em todo o código
- **JSF**
  - Implementação com JSF 2.2.14
  - Configurado no web.xml
  - Uso de Facelets templates
- **PrimeFaces**
  - Versão 6.1 implementada
  - Componentes utilizados:
    - p:inputTextarea
    - p:commandButton
    - p:dataTable
    - p:progressBar
    - p:dialog (loading)

### 5. Compatibilidade com Servidores ✅
- **WildFly 10.0.0.Final**
  - Configuração específica no pom.xml
  - Datasource configurado
  - Deployment via war

### 6. Compatibilidade com Navegadores ✅
- **Google Chrome**
- **Microsoft Edge**
- Design responsivo implementado
- Meta tags adequadas:
  ```html
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  ```

### 7. Controle de Versão ✅
- Repositório Git configurado
- Disponível em: [GitHub - Prover JSF](https://github.com/giovannicaprio/prover_jsf)

## Tecnologias Utilizadas

- Java 8
- JSF 2.2.14
- PrimeFaces 6.1
- CDI 1.2
- Maven
- WildFly 10.0.0.Final
- Git

## Executando o Projeto

### Pré-requisitos
- Docker
- Docker Compose
- Maven 3.x (para build local)
- Java 8 JDK

### Passos para Execução

1. Clone o repositório:
```bash
git clone https://github.com/giovannicaprio/prover_jsf.git
cd prover_jsf
```

2. Build do projeto:
```bash
mvn clean package
```

3. Iniciar containers:
```bash
docker-compose up --build
```

A aplicação estará disponível em:
- Aplicação: http://localhost:8080/prover-jsf
- Console Admin: http://localhost:9990
  - Usuário: admin
  - Senha: admin123

## Estrutura do Projeto

```
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── prover/
│   │           ├── bean/
│   │           ├── model/
│   │           ├── service/
│   │           └── web/
│   ├── resources/
│   │   └── META-INF/
│   └── webapp/
│       ├── resources/
│       │   ├── css/
│       │   └── images/
│       └── WEB-INF/
└── test/
    └── java/
```

## Funcionalidades Implementadas

1. **Análise de Texto**
   - Processamento de frases
   - Contagem de palavras distintas
   - Cálculo de ocorrências

2. **Interface Responsiva**
   - Design moderno
   - Feedback visual durante processamento
   - Exibição clara dos resultados

3. **Validações**
   - Entrada de texto
   - Processamento sincronizado
   - Feedback de erros

## Autor

Giovanni Caprio

## Licença

Este projeto está sob a licença MIT. 
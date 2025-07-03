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

## Testes

O projeto possui uma cobertura abrangente de testes unitários, implementados utilizando as seguintes tecnologias:

### Tecnologias de Teste
- **JUnit 4**: Framework principal para testes unitários
- **Mockito**: Framework para criação de mocks e simulação de comportamentos
- **JaCoCo**: Ferramenta para análise de cobertura de código
- **Maven Surefire**: Plugin para execução de testes unitários

### Estrutura de Testes

Os testes estão organizados em três camadas principais, seguindo a arquitetura da aplicação:

#### 1. Testes de Modelo (Model)
- `PalavraTest`: Testa a entidade de domínio
  - Validação de construtores (padrão e parametrizado)
  - Verificação de getters e setters
  - Validação de equals e hashCode
  - Verificação do método toString
  - Testes de consistência de estado

#### 2. Testes de Serviço (Service)
- `AnalisadorServiceTest`: Testa a lógica de negócio
  - Cenários de entrada:
    - Texto vazio e nulo
    - Texto simples
    - Texto com pontuação
    - Texto com acentuação
    - Texto com múltiplas linhas
    - Texto com espaços extras
  - Casos especiais:
    - Caracteres especiais (@, #, -, .)
    - Maiúsculas e minúsculas
    - Palavras repetidas
  - Validação de ordenação dos resultados
  - Verificação de contagem de palavras distintas

#### 3. Testes de Controller (Bean)
- `AnalisadorFraseBeanTest`: Testa o controlador JSF
  - Integração com o serviço (usando Mockito)
  - Validação de entrada de dados
  - Processamento de formulário
  - Limpeza de campos
  - Manipulação de estado do bean
  - Verificação de escopo e ciclo de vida

### Cobertura de Testes

O projeto utiliza JaCoCo para análise de cobertura de código, verificando:
- Cobertura de linhas
- Cobertura de branches
- Complexidade ciclomática
- Cobertura de métodos e classes

### Executando os Testes

Para executar os testes unitários:
```bash
mvn test
```

Para gerar relatório de cobertura de testes:
```bash
mvn verify
```

O relatório de cobertura será gerado em: `target/site/jacoco/index.html`

### Boas Práticas Implementadas

1. **Isolamento de Testes**
   - Cada teste é independente
   - Uso de @Before para setup comum
   - Mocking de dependências externas

2. **Nomenclatura Clara**
   - Padrão: test[Cenário][Comportamento]
   - Descrição clara do que está sendo testado
   - Facilita manutenção e entendimento

3. **Assertions Significativos**
   - Mensagens claras de erro
   - Verificações específicas
   - Cobertura de casos de borda

4. **Organização do Código**
   - Testes agrupados por funcionalidade
   - Separação clara de cenários
   - Reutilização de código de teste

### Resultados dos Testes

Os testes validam:
- Funcionalidade correta da aplicação
- Tratamento adequado de erros
- Consistência dos dados
- Integração entre camadas
- Performance das operações principais

A cobertura atual dos testes abrange mais de 90% do código da aplicação, garantindo alta qualidade e confiabilidade do software. 
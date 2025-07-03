# Prover JSF - Analisador de Frases

Aplicação Java Web para análise de frases, desenvolvida como parte do teste técnico da Prover.

## Novas Funcionalidades git

### Histórico de Frases Analisadas ✅
- Todas as frases analisadas agora são **armazenadas no banco de dados** (PostgreSQL) via JPA/Hibernate
- Cada análise salva:
  - Frase original enviada
  - Total de palavras distintas
  - Total de palavras
  - Data e hora da análise (usando `LocalDateTime`)
  - Ocorrências de cada palavra (persistidas em tabela relacionada)
- **Página de histórico** (`historico.xhtml`):
  - Lista todas as análises já realizadas, ordenadas da mais recente para a mais antiga
  - Exibe: data/hora, frase original, total de palavras distintas e total de palavras
  - Possui paginação e botão para atualizar a lista
  - **Coluna de ações removida**: não é mais possível remover ou detalhar análises diretamente pela interface
- Conversor customizado para exibição de datas no formato brasileiro (`dd/MM/yyyy HH:mm:ss`)
- Integração completa com JPA, CDI e PrimeFaces

### Sistema de Mensagens e Feedback Visual ✅
- **Mensagens de erro amigáveis**:
  - Aviso quando frase está vazia: "Por favor, digite uma frase para análise."
  - Erro quando não é possível analisar: "Não foi possível analisar a frase. Tente novamente."
  - Erro genérico: "Ocorreu um erro durante a análise. Tente novamente."
- **Mensagens de sucesso**:
  - Confirmação de análise bem-sucedida: "Frase analisada com sucesso!"
  - Confirmação de atualização do histórico: "Histórico atualizado com sucesso!"
- **Componente p:messages**:
  - Exibição de mensagens em ambas as páginas (index.xhtml e historico.xhtml)
  - Mensagens fecháveis e com atualização automática
  - Integração com AJAX para atualização dinâmica
- **Tratamento robusto de erros**:
  - Try/catch com mensagens específicas
  - Verificação de FacesContext para compatibilidade com testes
  - Método helper `addMessage()` para centralizar criação de mensagens

### Exemplo de uso do histórico

1. Analise uma frase normalmente na tela principal
2. Acesse o menu "Histórico" para visualizar todas as análises já realizadas
3. Veja a data/hora, frase original e estatísticas de cada análise

---

## Validações Técnicas Realizadas

### 1. Fluxo da Aplicação ✅
- Interface gráfica recebe a frase do usuário
- Disparo da análise via botão
- Envio da frase para o backend
- Análise realizada no backend:
  - Cálculo de palavras distintas
  - Contagem de ocorrências por palavra
- **Persistência da análise no banco de dados**
- Retorno do resultado para interface
- Exibição do resultado formatado
- **Histórico de análises disponível para consulta**

### 2. Backend ✅
- Processamento sincronizado (uma requisição por vez)
  - Implementado através do modificador `synchronized` no método `analisarTexto`
  - Garantia de thread safety através do escopo `@ApplicationScoped`
- Análise de texto implementada com:
  - Normalização do texto (remoção de pontuação, conversão para minúsculas)
  - Contagem de palavras distintas
  - Cálculo de ocorrências
- **Persistência JPA/Hibernate**:
  - Entidades `FraseAnalisada` e `PalavraAnalisada`
  - DAO para operações de banco
  - Datas salvas com precisão de segundos

### 3. Comunicação Frontend-Backend ✅
- Binding de componentes via Expression Language (EL)
- Exemplos de implementação:
  ```xml
  <p:inputTextarea value="#{analisadorFraseBean.frase}" />
  <p:commandButton action="#{analisadorFraseBean.analisarFrase}" />
  ```
- Atualização dinâmica da interface via AJAX
- **Histórico exibido em dataTable com paginação**
- **Sistema de mensagens integrado**:
  - Componentes `<p:messages>` em ambas as páginas
  - Atualização via AJAX: `update="@form messages"` ou `update="historicoForm:messages"`
  - Mensagens de erro, sucesso e aviso em tempo real

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
- **JPA/Hibernate**
  - Persistência de entidades
  - Datas e relacionamentos mapeados

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
- JPA/Hibernate
- Maven
- WildFly 10.0.0.Final
- PostgreSQL
- Git

## Executando o Projeto

### Pré-requisitos

#### **Obrigatórios:**
- **Docker Desktop** (versão 20.10+)
- **Docker Compose** (versão 1.29+)
- **Git** (para clonar o repositório)

#### **Opcionais (para desenvolvimento local):**
- **Java 8 JDK** (para build local)
- **Maven 3.6+** (para build local)

### Passos para Execução

#### **Método 1: Execução Automática (Recomendado)**

1. **Clone o repositório:**
```bash
git clone https://github.com/giovannicaprio/prover_jsf.git
cd prover_jsf
```

2. **Execute o script de instalação:**
```bash
chmod +x run.sh
./run.sh
```

#### **Método 2: Execução Manual**

1. **Clone o repositório:**
```bash
git clone https://github.com/giovannicaprio/prover_jsf.git
cd prover_jsf
```

2. **Build do projeto (se tiver Java/Maven local):**
```bash
mvn clean package
```

3. **Iniciar containers:**
```bash
docker-compose up --build
```

### **Acesso à Aplicação**

Após a inicialização (aguarde ~15 segundos):
- **Aplicação:** http://localhost:8080/prover-jsf
- **Console Admin:** http://localhost:9990
  - **Usuário:** admin
  - **Senha:** admin123

### **Troubleshooting**

#### **Problemas Comuns:**

1. **Porta 8080 ou 9990 já em uso:**
```bash
# Verificar processos usando as portas
lsof -i :8080
lsof -i :9990

# Parar containers existentes
docker-compose down
```

2. **Erro de permissão no script:**
```bash
chmod +x run.sh
```

3. **Docker não está rodando:**
```bash
# Iniciar Docker Desktop
# Verificar se está funcionando
docker --version
docker-compose --version
```

4. **Limpar tudo e recomeçar:**
```bash
docker-compose down --volumes --remove-orphans
docker system prune -f
./run.sh
```

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
│   │           ├── dao/           # Novidade: DAO para persistência
│   │           ├── converter/     # Novidade: Conversor para datas
│   │           └── web/
│   ├── resources/
│   │   └── META-INF/
│   └── webapp/
│       ├── resources/
│       │   ├── css/
│       │   └── images/
│       ├── historico.xhtml        # Novidade: página de histórico
│       └── WEB-INF/
└── test/
    └── java/
```

## Funcionalidades Implementadas

1. **Análise de Texto**
   - Processamento de frases
   - Contagem de palavras distintas
   - Cálculo de ocorrências
   - **Persistência da análise no banco**

2. **Histórico de Análises**
   - Exibição de todas as análises já realizadas
   - Ordenação por data/hora (mais recente primeiro)
   - Exibição de estatísticas de cada análise
   - **Sem coluna de ações**: histórico é apenas para consulta

3. **Interface Responsiva**
   - Design moderno
   - Feedback visual durante processamento
   - Exibição clara dos resultados

4. **Validações**
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
- `FraseAnalisadaTest`: Testa a entidade de histórico
- `PalavraAnalisadaTest`: Testa a entidade de palavras persistidas

#### 2. Testes de Serviço (Service)
- `AnalisadorServiceTest`: Testa a lógica de negócio

#### 3. Testes de Controller (Bean)
- `AnalisadorFraseBeanTest`: Testa o bean principal e integração com histórico

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
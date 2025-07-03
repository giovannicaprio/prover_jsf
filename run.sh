#!/bin/bash

echo "🚀 Iniciando o processo de build e deploy do projeto Prover JSF..."

# Configura o Java 8
export JAVA_HOME="/Library/Java/JavaVirtualMachines/adoptopenjdk-8.jdk/Contents/Home"
export PATH="$JAVA_HOME/bin:$PATH"

# Verifica a versão do Java
java_version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
if [[ ! $java_version =~ ^1\.8\. ]]; then
    echo "❌ Versão incorreta do Java. Necessário Java 8, encontrado: $java_version"
    echo "💡 Verifique se o JAVA_HOME está configurado corretamente"
    exit 1
fi

# Verifica se o Docker está rodando
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker não está rodando. Por favor, inicie o Docker e tente novamente."
    exit 1
fi

echo "📦 Compilando o projeto com Maven..."
"$JAVA_HOME/bin/java" -version
mvn clean package -Dmaven.compiler.source=1.8 -Dmaven.compiler.target=1.8

if [ $? -ne 0 ]; then
    echo "❌ Falha na compilação do projeto. Verifique os erros acima."
    exit 1
fi

echo "🐳 Construindo e iniciando os containers Docker..."
docker-compose down --remove-orphans
docker-compose up --build -d

if [ $? -ne 0 ]; then
    echo "❌ Falha ao iniciar os containers Docker. Verifique os erros acima."
    exit 1
fi

echo "⏳ Aguardando o WildFly iniciar..."
sleep 15

echo "✅ Projeto iniciado com sucesso!"
echo "📱 Acesse a aplicação em: http://localhost:8080/prover-jsf"
echo "⚙️  Console de administração em: http://localhost:9990"
echo "   Usuario: admin"
echo "   Senha: admin123"
echo ""
echo "📋 Logs do container:"
docker-compose logs -f 
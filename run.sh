#!/bin/bash

echo "🚀 Iniciando o processo de build e deploy do projeto Prover JSF..."

# Verifica se o Java está disponível (opcional para Docker)
if command -v java &> /dev/null; then
    java_version=$(java -version 2>&1 | head -n 1 | cut -d'"' -f2)
    if [[ ! $java_version =~ ^1\.8\. ]]; then
        echo "⚠️  Aviso: Java 8 não encontrado localmente ($java_version)"
        echo "💡 O build será feito dentro do container Docker"
    else
        echo "✅ Java 8 encontrado: $java_version"
    fi
else
    echo "⚠️  Java não encontrado localmente"
    echo "💡 O build será feito dentro do container Docker"
fi

# Verifica se o Docker está rodando
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker não está rodando. Por favor, inicie o Docker e tente novamente."
    exit 1
fi

# Tenta fazer o build local se Maven estiver disponível
if command -v mvn &> /dev/null; then
    echo "📦 Compilando o projeto com Maven local..."
    mvn clean package -Dmaven.compiler.source=1.8 -Dmaven.compiler.target=1.8
    
    if [ $? -ne 0 ]; then
        echo "⚠️  Falha no build local. Tentando build via Docker..."
    else
        echo "✅ Build local realizado com sucesso!"
    fi
else
    echo "📦 Maven não encontrado localmente. Build será feito via Docker..."
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
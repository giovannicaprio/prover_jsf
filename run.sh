#!/bin/bash

echo "🚀 Iniciando o processo de build e deploy do projeto Prover JSF..."

# Verifica se o Docker está rodando
if ! docker info > /dev/null 2>&1; then
    echo "❌ Docker não está rodando. Por favor, inicie o Docker e tente novamente."
    exit 1
fi

echo "📦 Compilando o projeto com Maven..."
mvn clean package

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
sleep 10

echo "✅ Projeto iniciado com sucesso!"
echo "📱 Acesse a aplicação em: http://localhost:8080/prover-jsf"
echo "⚙️  Console de administração em: http://localhost:9990"
echo "   Usuario: admin"
echo "   Senha: admin123"
echo ""
echo "📋 Logs do container:"
docker-compose logs -f 
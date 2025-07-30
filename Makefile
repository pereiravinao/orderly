# Makefile para gerenciar aplicação com múltiplos microsserviços Docker
# Este arquivo foi projetado para ser multiplataforma (Windows/Linux/macOS).

# NOTA PARA USUÁRIOS WINDOWS:
# Se os caracteres com acentos (🚀, 🛠️) aparecerem quebrados no seu terminal (PowerShell/CMD),
# pode ser um problema de codificação. Salvar este arquivo como "UTF-8" pode resolver.
# Alternativamente, executar 'chcp 65001' no terminal antes de 'make' pode ajudar.

# --- Configuração ---

# Nome do projeto. Usado para agrupar todos os contêineres e evitar conflitos.
PROJECT_NAME = orderly

# Para mudar para o comando moderno 'docker compose' (com espaço), altere esta variável.
# DOCKER_COMPOSE = docker compose
DOCKER_COMPOSE = docker-compose

# Lista de todos os arquivos docker-compose.yaml dos microsserviços.
# Usamos barras normais (/) para garantir a compatibilidade entre sistemas.
COMPOSE_LIST = \
    ./docker-compose.shared.yaml \
    ./apps/auth-service/docker-compose.yaml \
    ./apps/user-service/docker-compose.yaml \
    ./apps/product-service/docker-compose.yaml \
    ./apps/stock-service/docker-compose.yaml \
    ./apps/order-service/docker-compose.yaml \
    ./apps/payment-service/docker-compose.yaml \
    ./apps/order-receiver-producer/docker-compose.yaml

# Gera automaticamente as flags -f para cada arquivo da lista.
# Exemplo: -f ./apps/auth-service/docker-compose.yaml -f ./apps/user-service/docker-compose.yaml ...
COMPOSE_FILES = $(addprefix -f ,$(COMPOSE_LIST))

# --- Comandos Principais ---

# Comando padrão (executado se você apenas digitar 'make')
all: up

# Inicia todos os serviços em modo detached (em segundo plano).
up:
	@echo "🚀 Iniciando todos os serviços para o projeto '$(PROJECT_NAME)'..."
	$(DOCKER_COMPOSE) -p $(PROJECT_NAME) $(COMPOSE_FILES) up -d

# Constrói (ou reconstrói) as imagens e inicia todos os serviços.
build:
	@echo "🛠️  Construindo imagens e iniciando todos os serviços para o projeto '$(PROJECT_NAME)'..."
	$(DOCKER_COMPOSE) -p $(PROJECT_NAME) $(COMPOSE_FILES) up -d --build

# Para e remove todos os contêineres, volumes e redes do projeto.
clean:
	@echo "🧹 Parando e limpando todos os contêineres, volumes e redes do projeto '$(PROJECT_NAME)'..."
	$(DOCKER_COMPOSE) -p $(PROJECT_NAME) $(COMPOSE_FILES) down --volumes --remove-orphans

# Apenas para todos os serviços do projeto.
down:
	@echo "🛑 Parando todos os serviços do projeto '$(PROJECT_NAME)'..."
	$(DOCKER_COMPOSE) -p $(PROJECT_NAME) $(COMPOSE_FILES) down

# Lista os contêineres em execução do projeto.
ps:
	@echo "📋 Listando contêineres do projeto '$(PROJECT_NAME)'..."
	$(DOCKER_COMPOSE) -p $(PROJECT_NAME) $(COMPOSE_FILES) ps

# Exibe os logs de todos os serviços do projeto.
logs:
	@echo "📜 Exibindo logs do projeto '$(PROJECT_NAME)'... (Pressione Ctrl+C para sair)"
	$(DOCKER_COMPOSE) -p $(PROJECT_NAME) $(COMPOSE_FILES) logs -f

# --- Comandos de Teste ---

# Executa todos os testes com cobertura JaCoCo e gera relatórios centralizados.
test-coverage:
	@echo "🧪 Executando testes com cobertura JaCoCo para todos os serviços..."
	@echo "📊 Gerando relatórios de cobertura..."
	mvn clean test jacoco:report jacoco:report-aggregate
	@echo "✅ Testes concluídos! Relatórios disponíveis em: ./testes/jacoco-reports-centralized/"

# Executa apenas os testes sem gerar relatórios de cobertura.
test:
	@echo "🧪 Executando testes para todos os serviços..."
	mvn test
	@echo "✅ Testes concluídos!"

# --- Metas Auxiliares ---

# Declara que os alvos não são arquivos, evitando conflitos.
.PHONY: all up build clean down ps logs test test-coverage help

# Exibe uma mensagem de ajuda com os comandos disponíveis.
help:
	@echo "Comandos disponíveis:"
	@echo "  make up            - Inicia todos os serviços em segundo plano."
	@echo "  make build         - Reconstrói as imagens e inicia os serviços."
	@echo "  make down          - Para todos os serviços."
	@echo "  make clean         - Para e remove contêineres, volumes e redes."
	@echo "  make ps            - Lista os contêineres em execução."
	@echo "  make logs          - Exibe os logs de todos os serviços."
	@echo "  make test          - Executa todos os testes."
	@echo "  make test-coverage - Executa testes com cobertura JaCoCo."


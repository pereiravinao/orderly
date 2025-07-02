# Caminho dos docker-compose.yaml de cada microserviço
COMPOSE_AUTH=apps/auth-service/docker-compose.yaml
COMPOSE_USER=apps/user-service/docker-compose.yaml

up:
	docker compose -f $(COMPOSE_AUTH) up -d

build:
	docker compose -f $(COMPOSE_AUTH) up -d --build

down:
	docker compose -f $(COMPOSE_AUTH) down

ps:
	docker compose -f $(COMPOSE_AUTH) ps

logs:
	docker compose -f $(COMPOSE_AUTH) logs



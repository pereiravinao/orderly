# Caminho dos docker-compose.yaml de cada microserviço
COMPOSE_AUTH=apps/auth-service/docker-compose.yaml
COMPOSE_USER=apps/user-service/docker-compose.yaml
COMPOSE_PRODUCT=apps/product-service/docker-compose.yaml
COMPOSE_STOCK=apps/stock-service/docker-compose.yaml
COMPOSE_ORDER=apps/order-service/docker-compose.yaml
COMPOSE_PAYMENT=apps/payment-service/docker-compose.yaml
COMPOSE_ORDER_RECEIVER=apps/order-receiver-producer/docker-compose.yaml

COMPOSE_FILES = $(COMPOSE_AUTH) $(COMPOSE_USER) $(COMPOSE_PRODUCT) $(COMPOSE_STOCK) $(COMPOSE_ORDER) $(COMPOSE_PAYMENT) $(COMPOSE_ORDER_RECEIVER)

up:
	docker compose -f $(COMPOSE_FILES) up -d

build:
	docker compose -f $(COMPOSE_FILES) up -d --build

clean:
	docker compose -f $(COMPOSE_FILES) down --volumes --remove-orphans

down:
	docker compose -f $(COMPOSE_FILES) down

ps:
	docker compose -f $(COMPOSE_FILES) ps

logs:
	docker compose -f $(COMPOSE_FILES) logs
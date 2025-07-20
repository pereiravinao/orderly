# Orderly - Sistema de E-commerce Baseado em Microsserviços

Este projeto é uma implementação de um sistema de e-commerce utilizando uma arquitetura de microsserviços. Ele demonstra a comunicação entre diferentes serviços, gerenciamento de pedidos, produtos, estoque, pagamentos e autenticação de usuários.

## Visão Geral da Arquitetura

O sistema `Orderly` é composto por vários microsserviços independentes, cada um responsável por uma funcionalidade específica. A comunicação entre os serviços é realizada via REST (com OpenFeign) e mensageria (RabbitMQ).

## Módulos do Projeto

O projeto é dividido nos seguintes módulos principais:

*   **`apps/`**: Contém os microsserviços da aplicação.
    *   **`auth-service`**: Serviço responsável pela autenticação e autorização de usuários.
    *   **`order-receiver-producer`**: Serviço que recebe requisições de pedidos e os envia para uma fila de processamento.
    *   **`order-service`**: Serviço central para gerenciamento de pedidos, incluindo criação, consulta e atualização de status.
    *   **`payment-service`**: Serviço para processamento de pagamentos.
    *   **`product-service`**: Serviço para gerenciamento de produtos.
    *   **`stock-service`**: Serviço para gerenciamento de estoque de produtos.
    *   **`user-service`**: Serviço para gerenciamento de usuários.
*   **`commons/`**: Contém bibliotecas compartilhadas entre os microsserviços.
    *   **`common-auth`**: Componentes comuns para autenticação.
    *   **`common-feign`**: Configurações e clientes Feign comuns.
    *   **`common-queue`**: DTOs e configurações comuns para mensageria.
    *   **`common-utils`**: Utilitários gerais e classes de exceção.

## Tecnologias Utilizadas

*   **Linguagem:** Java 21
*   **Framework:** Spring Boot 3.x
*   **Gerenciador de Dependências:** Maven
*   **Banco de Dados:** PostgreSQL (via Spring Data JPA)
*   **Mensageria:** RabbitMQ
*   **Comunicação Inter-serviços:** OpenFeign
*   **Segurança:** Spring Security
*   **Geração de Código:** Lombok
*   **Cobertura de Código:** JaCoCo
*   **Testes:** JUnit 5, Mockito

## Pré-requisitos

Antes de iniciar, certifique-se de ter as seguintes ferramentas instaladas:

*   JDK 21
*   Apache Maven
*   Docker e Docker Compose (para rodar PostgreSQL e RabbitMQ)

## Configuração e Execução

Siga os passos abaixo para configurar e executar o projeto:

1.  **Clonar o Repositório:**
    ```bash
    git clone https://github.com/seu-usuario/orderly.git
    cd orderly
    ```

2.  **Configurar o Ambiente com Docker Compose:**
    Cada microsserviço possui um arquivo `docker-compose.yaml` em seu diretório (`apps/<nome-do-servico>/docker-compose.yaml`) para configurar suas dependências (PostgreSQL e RabbitMQ). Você pode iniciar as dependências de cada serviço individualmente ou, se preferir, consolidar os `docker-compose.yaml` em um único arquivo na raiz do projeto para iniciar tudo de uma vez.

    Para iniciar as dependências de um serviço específico (ex: `order-service`):
    ```bash
    cd apps/order-service
    docker-compose up -d
    ```
    Repita para outros serviços conforme necessário.

3.  **Construir o Projeto:**
    Na raiz do projeto, execute o comando Maven para construir todos os módulos:
    ```bash
    mvn clean install
    ```

4.  **Executar os Microsserviços:**
    Após a construção, você pode executar cada microsserviço individualmente. Navegue até o diretório de cada serviço e execute o JAR gerado:

    ```bash
    # Exemplo para order-service
    cd apps/order-service
    java -jar target/order-service-1.0.0.jar

    # Repita para os outros serviços:
    # cd apps/auth-service && java -jar target/auth-service-1.0.0.jar
    # cd apps/order-receiver-producer && java -jar target/order-receiver-producer-1.0.0.jar
    # cd apps/payment-service && java -jar target/payment-service-1.0.0.jar
    # cd apps/product-service && java -jar target/product-service-1.0.0.jar
    # cd apps/stock-service && java -jar target/stock-service-1.0.0.jar
    # cd apps/user-service && java -jar target/user-service-1.0.0.jar
    ```

## Testes

Para executar os testes unitários de um módulo específico e gerar o relatório de cobertura JaCoCo:

```bash
cd apps/<nome-do-servico>
mvn clean install jacoco:report
```

### Relatórios de Cobertura Centralizados

Após executar os testes com JaCoCo para cada microsserviço, você pode visualizar um relatório mestre que centraliza os links para todos os relatórios individuais. Este relatório é gerado automaticamente na raiz do projeto:

Abra o arquivo `target/jacoco-reports-centralized/index.html` no seu navegador.


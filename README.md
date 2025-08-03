# Orderly - Sistema de E-commerce Baseado em Microsserviços

Este projeto é uma implementação de um sistema de e-commerce utilizando uma arquitetura de microsserviços. Ele demonstra a comunicação entre diferentes serviços, gerenciamento de pedidos, produtos, estoque, pagamentos e autenticação de usuários.

## Visão Geral da Arquitetura

O sistema Orderly é composto por vários microsserviços independentes, cada um responsável por uma funcionalidade específica. A comunicação entre os serviços é realizada via REST (com OpenFeign) e mensageria (RabbitMQ).

## Módulos do Projeto

O projeto é dividido nos seguintes módulos principais:

**`apps/`**: Contém os microsserviços da aplicação.

- **`auth-service`**: Serviço responsável pela autenticação e autorização de usuários.
- **`order-receiver-producer`**: Serviço que recebe requisições de pedidos e os envia para uma fila de processamento.
- **`order-service`**: Serviço central para gerenciamento de pedidos, incluindo criação, consulta e atualização de status.
- **`payment-service`**: Serviço para processamento de pagamentos (mock simulado via adapter).
- **`product-service`**: Serviço para gerenciamento de produtos.
- **`stock-service`**: Serviço para gerenciamento de estoque de produtos.
- **`user-service`**: Serviço para gerenciamento de usuários.

**`commons/`**: Contém bibliotecas compartilhadas entre os microsserviços.

- **`common-auth`**: Componentes comuns para autenticação.
- **`common-feign`**: Configurações e clientes Feign comuns.
- **`common-queue`**: DTOs e configurações comuns para mensageria.
- **`common-utils`**: Utilitários gerais e classes de exceção.

## Tecnologias Utilizadas

- **Linguagem:** Java 21
- **Framework:** Spring Boot 3.x
- **Gerenciador de Dependências:** Maven
- **Banco de Dados:** PostgreSQL (via Spring Data JPA)
- **Mensageria:** RabbitMQ
- **Comunicação Inter-serviços:** OpenFeign
- **Segurança:** Spring Security
- **Geração de Código:** Lombok
- **Migrações:** Flyway
- **Cobertura de Código:** JaCoCo (mínimo 80%)
- **Testes:** JUnit 5, Mockito
- **Execução:** Docker e Docker Compose

## Requisitos Funcionais

O sistema permite:

- **Clientes**: Cadastro com nome, CPF (único), data de nascimento e endereço.
- **Produtos**: Cadastro com nome, SKU (único) e preço.
- **Estoque**: Controle de quantidade disponível por produto.
- **Pedidos**:
    - Recebem SKU(s), quantidade(s), cliente e dados do cartão de crédito.
    - Status inicial: `ABERTO`
    - Se estoque e pagamento forem aprovados: `FECHADO_COM_SUCESSO`
    - Se faltar estoque: estorna pagamento e define `FECHADO_SEM_ESTOQUE`
    - Se pagamento falhar: repõe estoque e define `FECHADO_SEM_CREDITO`

> O pagamento é simulado por meio de uma classe adapter mock.

## Pré-requisitos

Antes de iniciar, certifique-se de ter as seguintes ferramentas instaladas:

- JDK 21
- Apache Maven
- Docker e Docker Compose (para rodar PostgreSQL e RabbitMQ)

## Configuração e Execução

Siga os passos abaixo para configurar e executar o projeto:

### 1. Clonar o Repositório

```bash
git clone https://github.com/seu-usuario/orderly.git
cd orderly
```

### 2. Subir serviços com Docker Compose (modo manual)

Você pode subir os microsserviços de forma individual, acessando cada diretório:

```bash
cd apps/order-service
docker-compose up -d
```

Repita para os demais microsserviços.

### 3. Subir todos os serviços com Makefile (recomendado)

Para facilitar a execução completa do sistema, utilize o comando abaixo na raiz do projeto:

```bash
make up
```
Este comando inicia todos os microsserviços e suas dependências (RabbitMQ e bancos PostgreSQL) de uma só vez, combinando múltiplos arquivos `docker-compose`.

Para encerrar todos os serviços:
```bash
make down
```

### 4. Construir o Projeto:

Na raiz do projeto, execute o comando Maven para construir todos os módulos:
```bash
mvn clean install
```

### 5. Executar os Microsserviços:

Após a construção, você pode executar cada microsserviço individualmente. Navegue até o diretório de cada serviço e execute o JAR gerado:

```bash
# Exemplo para order-service
cd apps/order-service
java -jar target/order-service-1.0.0.jar

# Repita para os outros serviços:
cd apps/auth-service && java -jar target/auth-service-1.0.0.jar
cd apps/order-receiver-producer && java -jar target/order-receiver-producer-1.0.0.jar
cd apps/payment-service && java -jar target/payment-service-1.0.0.jar
cd apps/product-service && java -jar target/product-service-1.0.0.jar
cd apps/stock-service && java -jar target/stock-service-1.0.0.jar
cd apps/user-service && java -jar target/user-service-1.0.0.jar
```

## Testes

Para executar os testes unitários de um módulo específico e gerar o relatório de cobertura JaCoCo:

```bash
cd apps/<nome-do-servico>
mvn clean install jacoco:report
```

## Testes e Cobertura de Código

Para executar os testes com geração de relatório de cobertura de código, utilize o comando:

```bash
make test-coverage
```

### Relatórios de Cobertura Centralizados

Após executar os testes com JaCoCo para cada microsserviço, você pode visualizar um relatório mestre que centraliza os links para todos os relatórios individuais. Este relatório é gerado automaticamente na raiz do projeto:

Abra o arquivo `target/jacoco-reports-centralized/index.html` no seu navegador.


# Fluxo de Processamento de Pedidos — Sistema Orderly

Este documento descreve detalhadamente o fluxo completo pelo qual um pedido percorre dentro do sistema Orderly, desde o momento em que o cliente realiza a solicitação até a conclusão final, envolvendo todos os microsserviços relacionados.

---

## 1. Criação e Enfileiramento do Pedido

### 1.1. Solicitação do Pedido pelo Cliente

O cliente realiza uma requisição para o sistema contendo:

- SKU(s) dos produtos desejados  
- Quantidade de cada item  
- Identificador do cliente (user ID)  
- Dados para pagamento (ex: cartão de crédito, boleto, etc)

### 1.2. Recebimento pelo serviço **order-receiver-producer**

- O serviço **order-receiver-producer** (também chamado order-receiver-service) recebe a requisição de pedido via API REST.  
- O pedido é validado superficialmente para garantir que possui os dados mínimos.  
- O pedido recebe status inicial **ABERTO** (ou PENDING) para indicar que está aguardando processamento.  
- O pedido é então publicado em uma fila do **RabbitMQ** para processamento assíncrono.

---

## 2. Processamento do Pedido pelo serviço **order-service**

O **order-service** é responsável por consumir os pedidos da fila e orquestrar o processamento completo, seguindo estes passos:

### 2.1. Validação e Enriquecimento dos Produtos

- O **order-service** consulta o **product-service** para validar se os SKUs enviados existem no sistema e obter os IDs dos produtos.  
- Essa etapa garante que os produtos solicitados estão ativos e válidos para venda.

### 2.2. Verificação de Estoque (stock-service)

- Com os IDs dos produtos em mãos, o **order-service** chama o **stock-service** para verificar a disponibilidade de estoque de cada item do pedido.  
- O estoque deve ser suficiente para atender a quantidade solicitada.  
- Caso algum item não tenha estoque disponível ou a quantidade seja insuficiente, o **order-service** interrompe o processamento normal e:  
  - Se o pagamento ainda não foi processado, o pedido é finalizado com o status **FECHADO_SEM_ESTOQUE**, indicando que não foi possível atender o pedido por falta de estoque.  
  - Se o pagamento já tiver sido processado antes da confirmação do estoque, o valor é estornado para o cliente, e o pedido é marcado também como **FECHADO_SEM_ESTOQUE**.  
- O cliente pode então ser notificado para ajustar o pedido ou tentar novamente posteriormente.

### 2.3. Processamento do Pagamento (payment-service)

- Se o estoque estiver disponível, o **order-service** encaminha os dados de pagamento para o **payment-service**.  
- O pagamento pode ser simulado (mock) ou processado em ambiente real.  
- O resultado do pagamento (aprovado, recusado, pendente) é retornado ao **order-service**.  
- Se o pagamento for recusado, o pedido é finalizado com o status **FECHADO_SEM_CREDITO** e, se o estoque já estiver reservado, ele é restituído para evitar inconsistências.  
- O cliente é notificado para corrigir a forma de pagamento.

---

## 3. Estados Finais e Tratamento de Resultados

Após as etapas de estoque e pagamento, o pedido recebe um dos seguintes status finais:

### 3.1. FECHADO_COM_SUCESSO

- Estoque confirmado disponível.  
- Pagamento aprovado.  
- O pedido é finalizado com sucesso.  
- O estoque é atualizado para descontar os itens vendidos.  
- Notificações de confirmação podem ser enviadas ao cliente.

### 3.2. FECHADO_SEM_ESTOQUE

- Estoque insuficiente para algum dos produtos.  
- Caso o pagamento tenha sido processado antes da confirmação do estoque, é realizado o estorno do valor.  
- O pedido é finalizado com o status indicando falta de estoque.  
- O cliente pode ser informado para ajustar ou cancelar o pedido.

### 3.3. FECHADO_SEM_CREDITO

- Pagamento recusado (ex: falta de crédito ou dados inválidos).  
- Caso o estoque já tenha sido reservado, ele é restituído para evitar inconsistências.  
- O pedido recebe status indicando falha no pagamento.  
- O cliente deve ser notificado para corrigir a forma de pagamento.

---

## 4. Integração e Comunicação entre Microsserviços

- A comunicação entre os serviços é feita por:  

  - **RabbitMQ**: fila de mensagens usada para desacoplar o recebimento do pedido do processamento (order-receiver → order-service).  

  - **REST (Feign Client)**: para chamadas síncronas entre:  
    - order-service ↔ product-service  
    - order-service ↔ stock-service  
    - order-service ↔ payment-service  

- Outros serviços auxiliares como **user-service** e **auth-service** podem ser consultados para validações de usuário e autenticação, garantindo segurança e integridade.


### Fluxo Visual

![Flowchart.png](Flowchart.png)

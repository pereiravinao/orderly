Aqui está um **arquivo em Markdown** listando todas as tarefas requisitadas no projeto do Tech Challenge Fase 4, conforme solicitado. Estruturei em formato checklist para facilitar o acompanhamento.

---

# Tech Challenge Fase 4 — Checklist de Tarefas

## Objetivo Geral

* [ ] Criar um Sistema de Gerenciamento de Pedidos Integrado utilizando **Spring** e **Microsserviços**
* [ ] Garantir arquitetura modular, escalável e com comunicação eficiente entre serviços

---

## Requisitos Funcionais

* [x] Gerenciar dados de **clientes** (nome, CPF, data de nascimento, endereços)

  * [x] Proibir duplicidade de CPF
* [x] Gerenciar dados de **produtos** (nome, SKU, preço)

  * [x] Proibir duplicidade de SKU
* [x] Gerenciar **estoque** (quantidade disponível por produto)
* [x] Receber e processar **pedidos** dos clientes:

  * [x] SKU dos produtos e quantidade de itens
  * [x] Identificador do cliente
  * [x] Dados de pagamento (número do cartão de crédito)
  * [x] Status inicial do pedido: `ABERTO`
  * [x] Dar baixa no estoque conforme quantidade solicitada
  * [x] Processar pagamento (mock de sistema externo)
  * [x] Calcular valor total do pedido no backend
  * [x] Status final do pedido:

    * [x] `FECHADO_COM_SUCESSO` (tudo OK)
    * [x] `FECHADO_SEM_ESTOQUE` (estoque insuficiente, estornar pagamento)
    * [x] `FECHADO_SEM_CREDITO` (pagamento negado, retomar estoque)

---

## Requisitos Técnicos

* [x] O sistema deve conter **pelo menos 6 microsserviços**:

  * [x] Cliente
  * [x] Produto
  * [x] Pedido
  * [x] Pedido Receiver
  * [x] Pagamento
  * [x] Estoque
* [x] Implementar **Arquitetura Limpa** (pode ser variação mais simples)
* [x] Todo o projeto deve rodar via **docker-compose**

  * [x] Imagens disponíveis no Docker Hub ou equivalente
* [x] Utilizar **Flyway** para migração de banco de dados
* [x] Cobertura de testes de **pelo menos 80%** (aferida via Jacoco)
* [x] Utilizar **última versão do Spring e Java**
* [x] Pedidos recebidos devem ser salvos numa **fila** para processamento posterior
* [x] Processamento de pagamento deve ser **mockado** (adaptador)

  * [x] O core da aplicação não pode depender do mock (facilidade para trocar por integração real)
* [x] O resultado do processamento de pagamento deve chegar em endpoint específico

  * [x] Buscar status do pagamento (mock) via REST API e seguir fluxo
* [x] Seguir **modelo/sugestão de arquitetura** fornecida ([exemplo aqui](https://github.com/FIAP/POSTECH_TC4_ADJT_EXEMPLO))

---

## Entregáveis

* [ ] **Vídeo** explicando o código, arquitetura e mostrando sistema funcionando e testes via Postman (5–8 minutos)
* [ ] Projeto **publicado no GitHub** (cada microsserviço em seu repositório)
* [ ] **Relatório do Jacoco** mostrando a cobertura de testes
* [x] Arquivo **docker-compose** funcional, rodando o projeto inteiro

  * [ ] Pode estar em repositório próprio (exemplo: repo `infra`)

---

## Observações Finais

* [ ] Validar sempre todos os requisitos funcionais e técnicos antes de concluir a entrega.
* [ ] O sistema deve ser facilmente testável e extensível, seguindo boas práticas de arquitetura.

---

Se quiser detalhar alguma etapa (ex: tarefas de implementação de cada serviço), é só pedir!

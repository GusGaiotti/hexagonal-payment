# 🏦 Sistema Financeiro Distribuído (Microservices)

![Java](https://img.shields.io/badge/Java-21-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
![RabbitMQ](https://img.shields.io/badge/RabbitMQ-FF6600?style=for-the-badge&logo=rabbitmq&logoColor=white)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-316192?style=for-the-badge&logo=postgresql&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white)

## 🎯 Visão do Produto
O objetivo principal é demonstrar a implementação de padrões complexos de engenharia de software, como **Arquitetura Orientada a Eventos (EDA)** e **Consistência Eventual**, para resolver problemas reais de escalabilidade e concorrência em sistemas de pagamentos.

O sistema simula um cenário onde a alta disponibilidade do processamento de pagamentos é prioritária, garantindo que transações sejam aceitas (Core Bancário) e processadas assincronamente.

## 🏗️ Arquitetura e Design Patterns

O projeto adota a **Arquitetura Hexagonal (Ports & Adapters)** para garantir o desacoplamento total entre o Domínio (Regras de Negócio) e a Infraestrutura.

### Estratégias de Engenharia:
* **Microservices:** Separação estrita de responsabilidades entre *Payment* (Producer) e *Wallet* (Consumer).
* **Event-Driven Architecture:** Comunicação assíncrona via RabbitMQ para desacoplar a escrita do processamento.
* **Database per Service:** Isolamento total de dados. Payment e Wallet possuem bancos PostgreSQL distintos.

### Fluxo da Informação:
1.  **Payment Service:** Recebe a requisição, valida o payload e persiste o pagamento com status `PENDING`.
2.  **Event Publishing:** Publica o evento `payment.created` na Exchange `payment.v1.events`.
3.  **Message Broker:** O RabbitMQ roteia a mensagem para a fila exclusiva da Wallet (`wallet.v1.payment-created.consumer`).
4.  **Wallet Service:** Consome a mensagem, valida a existência da carteira e atualiza o saldo.

## 🚀 Funcionalidades Atuais

### ✅ Payment Service
- Criação de pagamentos via API REST.
- Persistência segura de transações.
- Publicação resiliente de eventos de domínio no RabbitMQ (Topic Exchange).

### ✅ Wallet Service
- Gestão de saldo de carteiras digitais.
- Consumo de eventos de pagamento.
- Tratamento de exceções de negócio (Saldo Insuficiente, Usuário Inexistente).

## 🔮 Roadmap (Próximos Passos)

O MVP atual foca no fluxo básico do **Core Bancário**. As próximas sprints focarão em consistência forte e integrações:

* [ ] **Idempotência & Ledger:** Implementar tabela de transações (`wallet_transactions`) com Unique Constraint no `payment_id` para garantir que o mesmo evento não seja processado duas vezes.
* [ ] **Authorizer Externo (Gateway):** Implementar um Adapter no Payment Service para consultar um mock de autorização antes da publicação do evento.
* [ ] **Notification Service:** Criar um terceiro microsserviço (Consumer) que escuta o mesmo evento para envio de e-mail/SMS (Pattern: Fan-out).
* [ ] **Tratamento de Falhas (DLQ):** Implementar *Dead Letter Queues* para reprocessamento manual de mensagens.
* [ ] **Circuit Breaker:** Implementar Resilience4j para proteger o sistema em caso de instabilidade de serviços externos.
* [ ] **Observabilidade:** Instrumentação com **OpenTelemetry** e **Grafana**.

## 🛠️ Como Executar

### Pré-requisitos
* Java 21
* Docker & Docker Compose

### 1. Configuração de Ambiente (Segurança)
O projeto segue práticas de segurança e não versiona credenciais.
Crie o arquivo `.env` na raiz do projeto baseando-se no exemplo:

```bash
cp .env.example .env
```

### 2. Infraestrutura
Suba os bancos de dados (PostgreSQL) e o Broker (RabbitMQ):

```bash
docker-compose up -d
```

### 3. Inicialização dos Serviços
Abra dois terminais separados para rodar os microsserviços.

**Terminal 1 - Payment Service (Windows):**
```powershell
cd payment-service
.\mvnw spring-boot:run
```

**Terminal 2 - Wallet Service (Windows):**
```powershell
cd wallet-service
.\mvnw spring-boot:run
```

*(Para Linux/Mac, utilize `./mvnw`)*

---
Desenvolvido por **Gustavo Gaiotti**
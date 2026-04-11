# Sistema de Conta Bancária

Projeto desenvolvido para a disciplina de Engenharia de Software com o objetivo de aplicar conceitos de orientação a objetos, testes unitários e integração contínua.

**Repositório:** https://github.com/AntonioFSN2/Projeto-C14-NP1

---

## Descrição

O sistema simula um ambiente bancário simples, permitindo o cadastro de clientes e a realização de operações financeiras básicas como depósito, saque e transferência.

Cada cliente possui uma conta bancária criada automaticamente no momento do cadastro, com número gerado de forma aleatória no formato `0000-00`.

---

## Tecnologias Utilizadas

- Java 17
- Maven
- JUnit 5.10.2
- GitHub Actions

---

## Estrutura do Projeto

```
src
├── main/java/com/banco
│   ├── app
│   ├── model
│   │   ├── Cliente
│   │   ├── ContaBancaria
│   │   └── Transacao
│   ├── service
│   │   └── ContaService
│   └── exception
└── test/java/com/banco/service
    └── ContaServiceTest
```

---

## Funcionalidades

### Cadastro de Cliente

- CPF, nome e data de nascimento
- Conta criada automaticamente no momento do cadastro
- Número da conta gerado no formato `0000-00`

### Validações

- CPF padronizado (com ou sem máscara)
- CPF não pode ser duplicado
- Nome não pode ser vazio
- Data deve estar no formato `dd/MM/yyyy`
- Não permite data futura

### Operações Bancárias

- Consultar saldo
- Depositar
- Sacar
- Transferir entre contas

### Regras de Negócio

- Não permite valores negativos
- Não permite saque sem saldo suficiente
- Não permite transferência inválida
- Não permite operações em conta inexistente

---

## Histórico de Transações

Cada operação realizada gera um registro contendo:

- Tipo da operação
- Valor
- Descrição
- Data e hora

---

## Testes Unitários

Os testes foram implementados com JUnit 5 na classe `ContaServiceTest`, cobrindo 20 cenários divididos em dois grupos:

- **Fluxo Normal (10 casos):** validam que o sistema executa corretamente as operações esperadas com entradas válidas — cadastro, busca, depósito, saque, transferência e histórico.
- **Fluxo de Extensão (10 casos):** validam o comportamento do sistema diante de entradas inválidas, dados duplicados, saldos insuficientes e formatos incorretos.

---

## CI/CD — GitHub Actions

O projeto possui uma pipeline configurada no GitHub Actions que é executada automaticamente a cada `push` ou `pull request` para a branch `main` ou branches `feat/**`.

### Etapas da pipeline

| Etapa | Descrição |
|---|---|
| Checkout | Clona o repositório |
| Setup Java 17 | Configura JDK Temurin 17 com cache Maven |
| Compilar | Executa `mvn compile` |
| Executar Testes | Executa `mvn test` e reporta os resultados |

O merge só deve ser realizado com o badge verde, ou seja, com todos os testes passando.

---

## Como Executar

### Pré-requisitos

- Java 17+
- Maven 3.8+

### Compilar o projeto

```bash
mvn compile
```

### Executar o projeto

```bash
mvn exec:java
```

### Executar os testes

```bash
mvn test
```

---

## Integrantes do Grupo

| Nome | Matrícula |
|---|---|
| Antonio Feliciano da Silveira Neto | 2122 |
| Daniele Letícia Pereira Sousa | 2095 |
| Danilo Henrique Maia da Silva | 2092 |
| Matheus Vieira Honório de Souza | 525 |

# Sistema de Conta Bancária

Projeto desenvolvido para a disciplina de Engenharia de Software com o objetivo de aplicar conceitos de orientação a objetos, testes unitários e integração contínua.

---

## Descrição

O sistema simula um ambiente bancário simples, permitindo o cadastro de clientes e a realização de operações financeiras básicas como depósito, saque e transferência.

Cada cliente possui uma conta bancária criada automaticamente no momento do cadastro, com número gerado de forma aleatória no formato `0000-00`.

---

## Tecnologias Utilizadas

- Java 17
- Maven
- JUnit 5
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
    └── ContaServiceCadastroClienteTest
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

Os testes foram implementados com JUnit 5, focando na classe `ContaService`.

### Cenários implementados — Cadastro de Cliente

| Cenário | Descrição |
|---|---|
| Cadastro válido | Dados corretos, conta criada com sucesso |
| CPF formatado | Normalização de CPF com e sem máscara |
| CPF duplicado | Rejeita cadastro com CPF já existente |
| Nome inválido | Rejeita nome vazio ou nulo |
| CPF inválido | Rejeita CPF com formato incorreto |

Os testes estão organizados por responsabilidade, garantindo melhor manutenção e legibilidade do código.

---

## CI/CD — GitHub Actions

O projeto possui pipelines configuradas no GitHub Actions para execução automática dos testes JUnit.

### Pipelines disponíveis

| Arquivo | Função |
|---|---|
| `.github/workflows/tests.yml` | Executa os testes com `mvn test` |
| `.github/workflows/deploy.yml` | Realiza o deploy da aplicação |
| `.github/workflows/version-bump.yml` | Gerencia o versionamento do projeto |

Os relatórios de teste são gerados em `target/surefire-reports` e disponibilizados como artifacts no GitHub Actions.

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
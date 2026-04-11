#  Sistema de Conta Bancária

Projeto desenvolvido para a disciplina de Engenharia de Software com o objetivo de aplicar conceitos de orientação a objetos, testes unitários e integração contínua.

---

##  Descrição

O sistema simula um ambiente bancário simples, permitindo o cadastro de clientes e a realização de operações financeiras básicas como depósito, saque e transferência.

Cada cliente possui uma conta bancária criada automaticamente no momento do cadastro, com número gerado de forma aleatória no formato `0000-00`.

---

##  Objetivo

Desenvolver um sistema estruturado que permita:

- Aplicação de conceitos de orientação a objetos  
- Implementação de regras de negócio  
- Criação de testes unitários  
- Uso de pipeline CI/CD  

---

##  Tecnologias Utilizadas

- Java 17  
- Maven  
- JUnit 5  
- GitHub Actions  

---

##  Estrutura do Projeto

src
├─ main/java/com/banco
│ ├─ app
│ ├─ model
│ │ ├─ Cliente
│ │ ├─ ContaBancaria
│ │ └─ Transacao
│ ├─ service
│ │ └─ ContaService
│ └─ exception
│
└─ test/java/com/banco/service
└─ ContaServiceCadastroClienteTest


---

##  Funcionalidades

### Cadastro de Cliente
- CPF, nome e data de nascimento  
- Conta criada automaticamente  
- Número da conta no formato `0000-00`  

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

### Regras do Sistema
- Não permite valores negativos  
- Não permite saque sem saldo suficiente  
- Não permite transferência inválida  
- Não permite conta inexistente  

---

##  Histórico de Transações

Cada operação realizada gera um registro contendo:

- Tipo da operação  
- Valor  
- Descrição  
- Data e hora  

---

##  Testes Unitários

Os testes foram implementados utilizando JUnit 5, focando na classe `ContaService`.

### Cenários implementados (Cadastro de Cliente)

- Cadastro com dados válidos  
- CPF formatado (normalização)  
- CPF duplicado  
- Nome inválido  
- CPF inválido  

Os testes estão organizados por responsabilidade, garantindo melhor manutenção e legibilidade do código.

---

##  CI/CD — GitHub Actions

O projeto possui pipelines configuradas no GitHub Actions para execução automática dos testes JUnit.

### As pipelines realizam:

- Execução dos testes com `mvn test`  
- Validação automática do sistema  
- Geração de relatórios JUnit  

 Arquivos:
.github/workflows/tests.yml
.github/workflows/deploy.yml
.github/workflows/version-bump.yml


Os relatórios de teste são gerados em:

target/surefire-reports


e disponibilizados como artifacts no GitHub Actions.

### Integrantes do Grupo
- Antonio Feliciano da Silveira Neto - 2122
- Daniele Letícia Pereira Sousa - 2095
- Danilo Henrique Maia da Silva - 2092
- Matheus Vieira Honório de Souza - 525

---

##  Como Executar

### Executar o projeto

```bash
mvn compile
mvn exec:java





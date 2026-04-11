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

## Prompts Utilizados

### Antonio Feliciano

#### Criação do Código Base

```text
Quero que você gere o código base de um projeto Java simples de sistema de conta bancária.

Objetivo:
Criar uma base inicial limpa, organizada e fácil de evoluir futuramente com Maven, JUnit e pipeline de CI/CD. Neste momento, não quero nada muito elaborado, apenas a estrutura inicial bem feita e as funcionalidades básicas que um sistema de conta bancária deve ter.

Requisitos gerais:
- Usar Java
- Código simples, didático e bem organizado
- Pensar na organização do projeto para facilitar futuras implementações de testes unitários com JUnit
- Pensar na separação de responsabilidades para facilitar manutenção
- Não usar nada avançado ou desnecessário neste primeiro momento
- Não precisa implementar interface gráfica
- Pode ser executado via console
- Comentar o código apenas quando realmente ajudar no entendimento
- Usar nomes claros e padronizados

Quero que o projeto seja estruturado de forma parecida com algo que depois possa ser adaptado para Maven, respeitando uma organização como:
- src/main/java
- pacotes separados por responsabilidade

Sugestão de organização:
- model -> classes de domínio
- service -> regras de negócio
- app ou main -> classe principal para executar o sistema

Funcionalidades básicas esperadas:
1. Criar uma conta bancária
2. Consultar saldo
3. Depositar valor
4. Sacar valor
5. Transferir valor entre contas
6. Exibir dados básicos da conta

Regras básicas:
- Não permitir depósito com valor menor ou igual a zero
- Não permitir saque com valor menor ou igual a zero
- Não permitir saque com saldo insuficiente
- Não permitir transferência com valor menor ou igual a zero
- Não permitir transferência se não houver saldo suficiente
- O saldo inicial pode começar em zero

Quero classes simples e coerentes, por exemplo:
- ContaBancaria
- ContaService
- Main

Peço que você:
1. Crie a estrutura inicial do projeto
2. Implemente as classes principais
3. Organize o código para facilitar futuros testes unitários
4. Evite colocar toda a lógica dentro da classe Main
5. Mostre a estrutura de pastas
6. Depois mostre o código completo de cada arquivo

Importante:
- Não usar frameworks neste momento
- Não implementar banco de dados
- Não implementar autenticação
- Não implementar interface gráfica
- Não exagerar na quantidade de classes
- Quero um projeto simples, mas com boa base de engenharia de software

Se achar adequado, você também pode incluir:
- uma classe de menu simples no console
- tratamento básico de entradas inválidas
- métodos bem separados para cada operação

No final, explique de forma breve por que essa estrutura é boa para evoluir depois com Maven e JUnit.
```

#### Criação do Job Build

```text
Você é um assistente especialista em CI/CD com GitHub Actions.

Crie automaticamente o arquivo:
.github/workflows/build.yml

para um projeto Java com Maven.

Requisitos do workflow:

- Nome do workflow: Build Pipeline
- Deve ser executado em:
  - push na branch main
  - pull_request na branch main

- Deve conter um job chamado "build"
- O job deve rodar em ubuntu-latest

Passos obrigatórios:

1. Checkout do código
2. Setup do Java:
   - versão: 17
   - distribuição: temurin
   - com cache do Maven
3. Compilar o projeto sem testes:
   mvn clean compile -DskipTests
4. Empacotar o projeto:
   mvn package -DskipTests

5. Após o build, renomear o arquivo .jar gerado para o padrão:
   sistema-bancario-<versao>.jar

   - A versão deve ser obtida automaticamente do pom.xml
   - O arquivo final deve ficar dentro da pasta target/

6. Fazer upload do artifact com:
   - nome: sistema-bancario-jar
   - caminho: target/sistema-bancario-*.jar

Requisitos adicionais:

- Código YAML bem indentado
- Usar nomes claros nos steps
- Não incluir etapas de teste
- Não incluir deploy
- Seguir boas práticas do GitHub Actions
- O workflow deve funcionar sem ajustes manuais

Gere apenas o conteúdo completo do arquivo build.yml.
```

#### Testes Unitários

```text
You are an assistant specialized in Java, JUnit, and clean unit testing.

I have a Java project with the classes Cliente, ContaBancaria and ContaService.

Your task:

1. Create unit tests for the following scenarios inside the test folder:
   - cadastrarCliente_dadosValidos_retornaClienteComContaAssociada
   - buscarClientePorCpf_cpfExistente_retornaClienteCorreto
   - buscarContaPorNumero_numeroExistente_retornaContaCorreta
   - depositar_valorPositivo_aumentaSaldoERegistraHistorico
   - sacar_valorValido_diminuiSaldoERegistraHistorico

2. Use JUnit (standard annotations like @Test).

3. Focus on:
   - validating returned objects
   - validating saldo changes
   - validating historico (transactions list)
   - validating that the client is stored in the service

4. Keep tests simple, clear, and readable.
5. Do not add comments inside the test code.
6. Do not modify any files outside the /test directory.

7. Save this prompt in the repository:
   - File: PROMPTS.md
   - Add a new section for unit tests
   - Store this exact prompt in Markdown format

Return:
- The test class code
- The content to be added to PROMPTS.md
```

### Danilo Henrique

#### Contexto Geral do Projeto

```text
Estou desenvolvendo um projeto acadêmico de Engenharia de Software chamado Sistema de Conta Bancária utilizando Java, Maven e JUnit. O sistema possui uma estrutura em camadas com model, service e app. Nele é possível cadastrar clientes informando CPF, nome e data de nascimento, sendo que ao cadastrar um cliente uma conta bancária é criada automaticamente com número gerado no formato 0000-00. O CPF é padronizado, aceitando entrada com ou sem máscara, mas sendo armazenado apenas com números e exibido formatado. A data de nascimento deve estar no formato dd/MM/yyyy e não pode ser uma data futura. A conta bancária possui saldo e histórico de transações, sendo que o histórico é representado por uma classe própria contendo tipo, valor, descrição e data/hora. O sistema permite consultar saldo, depositar, sacar e transferir entre contas, garantindo regras como não permitir CPF duplicado, valores negativos, saldo insuficiente ou operações com contas inexistentes. As entradas são tratadas com Scanner usando nextLine e conversões para evitar erros, e há tratamento de exceções no menu. Quero soluções simples, claras e compatíveis com Java padrão.
``` 
#### Teste Unitários

```text
Com base no sistema descrito, preciso criar testes unitários utilizando JUnit para a classe ContaService, focando principalmente na funcionalidade de cadastro de cliente. Os testes devem validar tanto o fluxo normal quanto o fluxo de extensão. No fluxo normal, deve ser testado o cadastro com dados válidos e o cadastro com CPF formatado que deve ser normalizado corretamente. No fluxo de extensão, deve ser testado o comportamento quando o CPF é duplicado, quando o nome é vazio ou inválido e quando o CPF é inválido. Os testes devem garantir que o método cadastrarCliente retorne um objeto Cliente válido quando apropriado e null quando as validações falharem. Além disso, os testes devem seguir um padrão de nomenclatura claro e estar organizados em uma classe específica para cadastro de cliente, mantendo o código limpo e fácil de entender.
```
#### Organização dos Testes

```text
Preciso organizar os testes unitários de forma eficiente em um projeto colaborativo com múltiplos integrantes. Em vez de colocar todos os testes em um único arquivo, quero separar os testes por responsabilidade, criando classes específicas para cada grupo de funcionalidades. No meu caso, os testes relacionados ao cadastro e validação de cliente devem ficar em uma classe própria chamada ContaServiceCadastroClienteTest, localizada no diretório padrão do Maven src/test/java/com/banco/service. Essa organização deve facilitar a manutenção, reduzir conflitos no Git e tornar o código mais legível, evitando tanto a fragmentação excessiva quanto a centralização desorganizada.
```

#### Pipeline CI/CD com JUnit

```text
Preciso configurar uma pipeline de CI/CD utilizando GitHub Actions que execute automaticamente os testes JUnit do projeto. A pipeline deve ser acionada em eventos de push e pull request na branch main ou em branches de feature. O objetivo é rodar o comando mvn test para executar todos os testes e, em seguida, salvar os relatórios gerados pelo Maven na pasta target/surefire-reports como artifacts no GitHub. Esses relatórios devem estar disponíveis para download mesmo em caso de falha nos testes, utilizando a configuração adequada para garantir isso. Não é necessário incluir etapas de build ou deploy, apenas a execução dos testes e o armazenamento dos relatórios.
```

#### Análise e Melhoria

```text
Com base no sistema descrito, gostaria de sugestões de melhorias simples no código, focando em boas práticas de programação, organização e legibilidade. Não quero soluções complexas ou arquiteturas avançadas, apenas melhorias que façam sentido dentro do escopo acadêmico do projeto, como melhor separação de responsabilidades, validações mais claras, organização de métodos e possíveis ajustes que aumentem a qualidade do código sem torná-lo difícil de entender ou manter.
``` 

### Matheus Vieira

Os prompts abaixo foram utilizados principalmente através do Claude CLI, sendo aplicados diretamente no fluxo de desenvolvimento do projeto.

Diferentemente dos demais, esses prompts possuem caráter mais operacional, auxiliando em tarefas como organização do repositório, validação de código, ajustes de pipeline e manutenção geral do sistema.

#### Prompts Utilizados

```text
/init

salve todos os prompts em um arquivo .MD

melhore esse projeto e deixe ele mais robusto

por enquanto não faça testes, isso será feito depois por cada integrante

crie um branch separada e faça as modificações

o que é a pasta out, é boa prática colocá-la no gitignore?

adicione arquivos do claude code no gitignore

adicione o git ignore faça commit e push

não seja coautor dos commits

lembre-se de salvar os prompts e atualizar o claude.md regularmente

realizei o merge de conflitos nessa branch, verifique o funcionamento, se não ficou alguma inconsistencia ou bug

verifique se há bugs

reinplemente o historico de transações

e deixe o numero da conta randomico, como no código do colega

coloque uma opção no menu para o historico de transferencias

sim

verifique se a pipeline já tem depends on, para seguir o fluxo: build -> tests (ainda em construção) -> bump - deploy/notify

sim

a pipe de test foi subida, continue a orquestração

crie uma branch e commit e push

confirmei deu bump na tag, mas mesmo assim o deploy nn foi triggerado

analise novamente, vc estava numa versao antiga da main

sim, tudo certo

não vai, mude o trigger do deploy ent

altere o deploy para publicar o jar no repo

atualize o arquivo de prompts
```

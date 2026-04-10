# Relatório de Testes — Sistema Bancário

**Disciplina:** Engenharia de Software
**Repositório:** https://github.com/AntonioFSN2/Projeto-C14-NP1

## Integrantes

| Nome | Matrícula |
|---|---|
| Antonio Feliciano da Silveira Neto | 2122 |
| Daniele Letícia Pereira Sousa | — |
| Danilo Henrique Maia da Silva | — |
| Matheus Vieira Honório de Souza | — |

---

## Estratégia de Testes

Os testes cobrem a classe `ContaService`, que concentra toda a lógica de negócio do sistema. Foram definidos 20 cenários divididos em dois grupos:

- **Fluxo Normal (10 casos):** validam que o sistema executa corretamente as operações esperadas com entradas válidas.
- **Fluxo de Extensão (10 casos):** validam o comportamento do sistema diante de entradas inválidas, dados duplicados, saldos insuficientes e formatos incorretos.

**Framework:** JUnit 5.10.2
**Cobertura:** JaCoCo 0.8.11 (relatório gerado em `target/site/jacoco/`)
**Arquivo de testes:** `src/test/java/com/banco/service/ContaServiceTest.java`

---

## Cenários de Teste

### Fluxo Normal

| Nº | Método de Teste | Descrição | Resultado Esperado | Responsável |
|---|---|---|---|---|
| 1 | `cadastrarCliente_dadosValidos_retornaCliente` | Cadastra cliente com CPF, nome e data válidos | Retorna objeto Cliente não nulo com conta associada | Antonio |
| 2 | `buscarClientePorCpf_cpfExistente_retornaCliente` | Busca cliente por CPF após cadastro | Retorna o mesmo cliente cadastrado | Antonio |
| 3 | `buscarContaPorNumero_numeroExistente_retornaConta` | Busca conta pelo número gerado no cadastro | Retorna ContaBancaria com número correto | Antonio |
| 4 | `depositar_valorPositivo_aumentaSaldo` | Deposita R$ 100,00 em conta com saldo zero | `getSaldo()` retorna 100.0 | Antonio |
| 5 | `sacar_valorValido_diminuiSaldo` | Deposita R$ 200,00 e saca R$ 80,00 | `getSaldo()` retorna 120.0 | Antonio |
| 6 | `transferir_valorValido_atualizaAmbosOsSaldos` | Transfere R$ 50,00 entre duas contas | Saldo origem diminui R$ 50,00; saldo destino aumenta R$ 50,00 | Daniele |
| 7 | `consultarSaldo_aposDeposito_retornaValorCorreto` | Chama `consultarSaldo()` após depositar | Retorna o valor exato depositado | Daniele |
| 8 | `cadastrarCliente_cpfFormatado_normalizaECadastra` | Cadastra cliente com CPF no formato "111.222.333-44" | Cadastro aceito; `getCpf()` retorna apenas dígitos | Daniele |
| 9 | `historico_aposMultiplasOperacoes_registraTodasTransacoes` | Realiza depósito, saque e transferência | `getHistorico().size()` reflete todas as operações | Daniele |
| 10 | `duasContas_numerosDistintos` | Cadastra dois clientes distintos | Números de conta são diferentes entre si | Daniele |

### Fluxo de Extensão

| Nº | Método de Teste | Descrição | Resultado Esperado | Responsável |
|---|---|---|---|---|
| 11 | `cadastrarCliente_cpfDuplicado_retornaNull` | Tenta cadastrar dois clientes com o mesmo CPF | Segundo cadastro retorna `null` | Danilo |
| 12 | `cadastrarCliente_nomeVazio_retornaNull` | Cadastra cliente com nome `""` ou `"   "` | Retorna `null` | Danilo |
| 13 | `cadastrarCliente_cpfInvalido_retornaNull` | CPF com menos de 11 dígitos (ex: "1234567") | Retorna `null` | Danilo |
| 14 | `cadastrarCliente_dataNascimentoFutura_retornaNull` | Data de nascimento "31/12/2099" | Retorna `null` | Danilo |
| 15 | `cadastrarCliente_dataFormatoInvalido_retornaNull` | Data no formato ISO "1990-01-15" | Retorna `null` | Danilo |
| 16 | `depositar_valorZeroOuNegativo_retornaFalse` | Tenta depositar 0 ou valor negativo | Retorna `false`; saldo não se altera | Matheus |
| 17 | `sacar_saldoInsuficiente_retornaFalse` | Deposita R$ 50,00 e tenta sacar R$ 100,00 | Retorna `false`; saldo permanece R$ 50,00 | Matheus |
| 18 | `sacar_valorNegativo_retornaFalse` | Tenta sacar valor negativo | Retorna `false` | Matheus |
| 19 | `transferir_saldoInsuficienteNaOrigem_retornaFalse` | Origem com R$ 30,00 tenta transferir R$ 100,00 | Retorna `false`; ambos os saldos inalterados | Matheus |
| 20 | `buscarClientePorCpf_cpfNaoCadastrado_retornaNull` | Busca CPF que nunca foi cadastrado | Retorna `null` | Matheus |

---

## Pipeline CI/CD

**Responsável:** Matheus Vieira

Três workflows independentes no GitHub Actions, ativados conforme o evento:

### Build — `.github/workflows/build.yml`

Executa em todo push e pull request. Valida que o projeto compila sem erros.

| Etapa | Descrição |
|---|---|
| Checkout | Clona o repositório |
| Setup Java 17 | Configura JDK Temurin 17 com cache Maven |
| Compilar | Executa `mvn compile` |
| Upload artefato | Salva `target/classes/` como artefato da execução |

### Test — `.github/workflows/test.yml`

Executa em todo push e pull request. Roda os 20 testes e gera relatório de cobertura com JaCoCo.

| Etapa | Descrição |
|---|---|
| Checkout + Java 17 | Configuração padrão |
| Executar testes | `mvn test` — JaCoCo gera relatório em `target/site/jacoco/` |
| Upload relatório | Publica a pasta `jacoco/` como artefato navegável |
| Resumo no PR | Action `madrapps/jacoco-report` exibe cobertura mínima de 70% diretamente no PR |

### Deploy — `.github/workflows/deploy.yml`

Executa apenas em push direto para `main`. Empacota o JAR final da aplicação.

| Etapa | Descrição |
|---|---|
| Checkout + Java 17 | Configuração padrão |
| Empacotar | `mvn package -DskipTests` |
| Publicar JAR | Salva `sistema-bancario-*.jar` como artefato da execução |

### Resultado Esperado

- Pull Requests bloqueados se build ou testes falharem
- Relatório de cobertura JaCoCo visível diretamente em cada PR
- JAR publicado automaticamente a cada merge na `main`

---

## Divisão de Trabalho

| Integrante | Responsabilidade |
|---|---|
| **Antonio Feliciano** | Cenários 1 a 5 — Fluxo normal: cadastro, busca e operações básicas |
| **Daniele Letícia** | Cenários 6 a 10 — Fluxo normal: transferência, histórico e unicidade |
| **Danilo Henrique** | Cenários 11 a 15 — Fluxo de extensão: validações de cadastro |
| **Matheus Vieira** | Cenários 16 a 20 — Fluxo de extensão: operações inválidas + pipelines CI/CD |

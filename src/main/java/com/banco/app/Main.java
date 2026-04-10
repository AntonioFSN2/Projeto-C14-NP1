package com.banco.app;

import com.banco.model.Cliente;
import com.banco.model.ContaBancaria;
import com.banco.model.Transacao;
import com.banco.service.ContaService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContaService service = new ContaService();
        Scanner scanner = new Scanner(System.in);

        Cliente cliente1 = service.cadastrarCliente("11111111111", "Antonio", "01/01/1990");
        Cliente cliente2 = service.cadastrarCliente("22222222222", "Danilo", "02/12/1990");
        Cliente cliente3 = service.cadastrarCliente("33333333333", "Matheus", "22/10/1990");

        if (cliente1 != null) {
            cliente1.getConta().setSaldo(500);
            cliente1.getConta().adicionarTransacao(new Transacao(
                    "SALDO INICIAL",
                    500,
                    "Saldo inicial definido para a conta"
            ));
        }

        if (cliente2 != null) {
            cliente2.getConta().setSaldo(200);
            cliente2.getConta().adicionarTransacao(new Transacao(
                    "SALDO INICIAL",
                    200,
                    "Saldo inicial definido para a conta"
            ));
        }

        if (cliente3 != null) {
            cliente3.getConta().setSaldo(600);
            cliente3.getConta().adicionarTransacao(new Transacao(
                    "SALDO INICIAL",
                    600,
                    "Saldo inicial definido para a conta"
            ));
        }

        while (true) {
            System.out.println("\n=================================");
            System.out.println("         SISTEMA BANCÁRIO");
            System.out.println("=================================");
            System.out.println("1 - Cadastrar cliente");
            System.out.println("2 - Consultar saldo");
            System.out.println("3 - Depositar");
            System.out.println("4 - Sacar");
            System.out.println("5 - Transferir");
            System.out.println("6 - Exibir dados da conta");
            System.out.println("7 - Listar clientes");
            System.out.println("8 - Histórico de transações");
            System.out.println("9 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao;

            try {
                opcao = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Opção inválida.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.print("Nome do cliente: ");
                    String nome = scanner.nextLine();

                    System.out.print("CPF do cliente: ");
                    String cpf = scanner.nextLine();

                    System.out.print("Data de nascimento (dd/mm/aaaa): ");
                    String data = scanner.nextLine();

                    Cliente novoCliente = service.cadastrarCliente(cpf, nome, data);

                    if (novoCliente != null) {
                        System.out.println("Cliente cadastrado com sucesso.");
                        System.out.println("Conta criada automaticamente.");
                        System.out.println("Número da conta: " + novoCliente.getConta().getNumero());
                    } else {
                        System.out.println("Erro: dados inválidos ou CPF já cadastrado.");
                    }
                    break;

                case 2:
                    System.out.println("\n--- Consulta de Saldo ---");
                    System.out.print("Digite o número da conta: ");
                    String numeroSaldo = scanner.nextLine();

                    ContaBancaria contaSaldo = service.buscarContaPorNumero(numeroSaldo);

                    if (contaSaldo != null) {
                        System.out.printf("Saldo atual: R$ %.2f%n", service.consultarSaldo(contaSaldo));
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- Depósito ---");
                    System.out.print("Digite o número da conta: ");
                    String numeroDeposito = scanner.nextLine();

                    ContaBancaria contaDeposito = service.buscarContaPorNumero(numeroDeposito);

                    if (contaDeposito != null) {
                        System.out.print("Digite o valor para depósito: R$ ");
                        double valorDep;

                        try {
                            valorDep = Double.parseDouble(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Valor inválido.");
                            break;
                        }

                        if (service.depositar(contaDeposito, valorDep)) {
                            System.out.println("Depósito realizado com sucesso.");
                            System.out.printf("Novo saldo: R$ %.2f%n", contaDeposito.getSaldo());
                        } else {
                            System.out.println("Erro: valor de depósito inválido.");
                        }
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;

                case 4:
                    System.out.println("\n--- Saque ---");
                    System.out.print("Digite o número da conta: ");
                    String numeroSaque = scanner.nextLine();

                    ContaBancaria contaSaque = service.buscarContaPorNumero(numeroSaque);

                    if (contaSaque != null) {
                        System.out.print("Digite o valor para saque: R$ ");
                        double valorSac;

                        try {
                            valorSac = Double.parseDouble(scanner.nextLine());
                        } catch (NumberFormatException e) {
                            System.out.println("Valor inválido.");
                            break;
                        }

                        if (service.sacar(contaSaque, valorSac)) {
                            System.out.println("Saque realizado com sucesso.");
                            System.out.printf("Novo saldo: R$ %.2f%n", contaSaque.getSaldo());
                        } else {
                            System.out.println("Erro: valor inválido ou saldo insuficiente.");
                        }
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;

                case 5:
                    System.out.println("\n--- Transferência ---");
                    System.out.print("Número da conta de origem: ");
                    String numeroOrigem = scanner.nextLine();

                    System.out.print("Número da conta de destino: ");
                    String numeroDestino = scanner.nextLine();

                    System.out.print("Valor da transferência: R$ ");
                    double valorTransferencia;

                    try {
                        valorTransferencia = Double.parseDouble(scanner.nextLine());
                    } catch (NumberFormatException e) {
                        System.out.println("Valor inválido.");
                        break;
                    }

                    ContaBancaria origem = service.buscarContaPorNumero(numeroOrigem);
                    ContaBancaria destino = service.buscarContaPorNumero(numeroDestino);

                    if (origem == null) {
                        System.out.println("Conta de origem não encontrada.");
                    } else if (destino == null) {
                        System.out.println("Conta de destino não encontrada.");
                    } else if (service.transferir(origem, destino, valorTransferencia)) {
                        System.out.println("Transferência realizada com sucesso.");
                        System.out.printf("Saldo da conta origem: R$ %.2f%n", origem.getSaldo());
                        System.out.printf("Saldo da conta destino: R$ %.2f%n", destino.getSaldo());
                    } else {
                        System.out.println("Erro: valor inválido ou saldo insuficiente.");
                    }
                    break;

                case 6:
                    System.out.println("\n--- Dados da Conta ---");
                    System.out.print("Digite o número da conta: ");
                    String numeroDados = scanner.nextLine();

                    ContaBancaria contaDados = service.buscarContaPorNumero(numeroDados);
                    service.exibirDados(contaDados);
                    break;

                case 7:
                    System.out.println("\n--- Lista de Clientes ---");
                    service.listarClientes();
                    break;

                case 8:
                    System.out.println("\n--- Histórico de Transações ---");
                    System.out.print("Digite o número da conta: ");
                    String numeroHistorico = scanner.nextLine();

                    ContaBancaria contaHistorico = service.buscarContaPorNumero(numeroHistorico);
                    service.exibirHistorico(contaHistorico);
                    break;

                case 9:
                    System.out.println("\nEncerrando o sistema...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }
}
package com.banco.app;

import com.banco.model.ContaBancaria;
import com.banco.service.ContaService;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContaService service = new ContaService();
        Scanner scanner = new Scanner(System.in);

        ContaBancaria conta1 = service.criarConta("Danilo");
        ContaBancaria conta2 = service.criarConta("Antonio");

        if (conta1 != null) conta1.setSaldo(500);
        if (conta2 != null) conta2.setSaldo(200);

        while (true) {
            System.out.println("\n=================================");
            System.out.println("         SISTEMA BANCÁRIO");
            System.out.println("=================================");
            System.out.println("1 - Criar conta");
            System.out.println("2 - Consultar saldo");
            System.out.println("3 - Depositar");
            System.out.println("4 - Sacar");
            System.out.println("5 - Transferir");
            System.out.println("6 - Exibir dados da conta");
            System.out.println("7 - Listar contas");
            System.out.println("8 - Histórico de Transações");
            System.out.println("9 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = Integer.parseInt(scanner.nextLine());

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Criação de Conta ---");
                    System.out.print("Nome do titular: ");
                    String titular = scanner.nextLine();

                    ContaBancaria novaConta = service.criarConta(titular);

                    if (novaConta != null) {
                        System.out.println("Conta criada com sucesso.");
                        System.out.println("Número da conta: " + novaConta.getNumero());
                    } else {
                        System.out.println("Erro ao criar conta.");
                    }
                    break;

                case 2:
                    System.out.println("\n--- Consulta de Saldo ---");
                    System.out.print("Número da conta: ");
                    String numeroSaldo = scanner.nextLine();

                    ContaBancaria contaSaldo = service.buscarContaPorNumero(numeroSaldo);

                    if (contaSaldo != null) {
                        System.out.printf("Saldo: R$ %.2f%n", contaSaldo.getSaldo());
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- Depósito ---");
                    System.out.print("Número da conta: ");
                    String numeroDeposito = scanner.nextLine();

                    ContaBancaria contaDeposito = service.buscarContaPorNumero(numeroDeposito);

                    if (contaDeposito != null) {
                        System.out.print("Valor: ");
                        double valorDep = Double.parseDouble(scanner.nextLine());

                        if (service.depositar(contaDeposito, valorDep)) {
                            System.out.println("Depósito realizado.");
                        } else {
                            System.out.println("Valor inválido.");
                        }
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;

                case 4:
                    System.out.println("\n--- Saque ---");
                    System.out.print("Número da conta: ");
                    String numeroSaque = scanner.nextLine();

                    ContaBancaria contaSaque = service.buscarContaPorNumero(numeroSaque);

                    if (contaSaque != null) {
                        System.out.print("Valor: ");
                        double valorSac = Double.parseDouble(scanner.nextLine());

                        if (service.sacar(contaSaque, valorSac)) {
                            System.out.println("Saque realizado.");
                        } else {
                            System.out.println("Erro: saldo insuficiente ou valor inválido.");
                        }
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;

                case 5:
                    System.out.println("\n--- Transferência ---");
                    System.out.print("Conta origem: ");
                    String numeroOrigem = scanner.nextLine();

                    System.out.print("Conta destino: ");
                    String numeroDestino = scanner.nextLine();

                    System.out.print("Valor: ");
                    double valor = Double.parseDouble(scanner.nextLine());

                    ContaBancaria origem = service.buscarContaPorNumero(numeroOrigem);
                    ContaBancaria destino = service.buscarContaPorNumero(numeroDestino);

                    if (origem == null || destino == null) {
                        System.out.println("Conta inválida.");
                    } else if (service.transferir(origem, destino, valor)) {
                        System.out.println("Transferência realizada.");
                    } else {
                        System.out.println("Erro na transferência.");
                    }
                    break;

                case 6:
                    System.out.println("\n--- Dados da Conta ---");
                    System.out.print("Número da conta: ");
                    String numeroDados = scanner.nextLine();

                    ContaBancaria contaDados = service.buscarContaPorNumero(numeroDados);

                    if (contaDados != null) {
                        service.exibirDados(contaDados);
                    } else {
                        System.out.println("Conta não encontrada.");
                    }
                    break;

                case 7:
                    service.listarContas();
                    break;

                case 8:
                    System.out.println("\n--- Histórico ---");
                    System.out.print("Número da conta: ");
                    String numeroHistorico = scanner.nextLine();

                    ContaBancaria contaHistorico = service.buscarContaPorNumero(numeroHistorico);

                    service.exibirHistorico(contaHistorico);
                    break;

                case 9:
                    System.out.println("Encerrando...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
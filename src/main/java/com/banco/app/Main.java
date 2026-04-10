package com.banco.app;

import com.banco.model.ContaBancaria;
import com.banco.service.ContaService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContaService service = new ContaService();
        List<ContaBancaria> contas = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        ContaBancaria conta = null;

        ContaBancaria conta1 = service.criarConta("Danilo");
        ContaBancaria conta2 = service.criarConta("Antonio");

        conta1.setSaldo(500);
        conta2.setSaldo(200);

        contas.add(conta1);
        contas.add(conta2);

        while (true) {
            System.out.println("\n=================================");
            System.out.println("      SISTEMA BANCÁRIO");
            System.out.println("=================================");
            System.out.println("1 - Criar conta");
            System.out.println("2 - Consultar saldo");
            System.out.println("3 - Depositar");
            System.out.println("4 - Sacar");
            System.out.println("5 - Transferir");
            System.out.println("6 - Exibir dados da conta");
            System.out.println("7 - Listar contas");
            System.out.println("8 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Criação de Conta ---");
                    System.out.print("Nome do titular: ");
                    String titular = scanner.nextLine();

                    conta = service.criarConta(titular);

                    if (conta != null) {
                        System.out.println("Conta criada com sucesso.");
                    } else {
                        System.out.println("Erro: dados inválidos para criação da conta.");
                    }
                    break;

                case 2:
                    System.out.println("\n--- Consulta de Saldo ---");
                    if (conta != null) {
                        System.out.printf("Saldo atual: R$ %.2f%n", service.consultarSaldo(conta));
                    } else {
                        System.out.println("Nenhuma conta foi criada ainda.");
                    }
                    break;

                case 3:
                    System.out.println("\n--- Depósito ---");
                    if (conta != null) {
                        System.out.print("Digite o valor para depósito: R$ ");
                        double valorDep = scanner.nextDouble();

                        if (service.depositar(conta, valorDep)) {
                            System.out.println("Depósito realizado com sucesso.");
                            System.out.printf("Novo saldo: R$ %.2f%n", conta.getSaldo());
                        } else {
                            System.out.println("Erro: valor de depósito inválido.");
                        }
                    } else {
                        System.out.println("Nenhuma conta foi criada ainda.");
                    }
                    break;

                case 4:
                    System.out.println("\n--- Saque ---");
                    if (conta != null) {
                        System.out.print("Digite o valor para saque: R$ ");
                        double valorSac = scanner.nextDouble();

                        if (service.sacar(conta, valorSac)) {
                            System.out.println("Saque realizado com sucesso.");
                            System.out.printf("Novo saldo: R$ %.2f%n", conta.getSaldo());
                        } else {
                            System.out.println("Erro: valor inválido ou saldo insuficiente.");
                        }
                    } else {
                        System.out.println("Nenhuma conta foi criada ainda.");
                    }
                    break;

                case 5:
                    System.out.println("\n--- Transferência ---");
                    System.out.print("Número da conta de origem: ");
                    int numeroOrigem = scanner.nextInt();

                    System.out.print("Número da conta de destino: ");
                    int numeroDestino = scanner.nextInt();

                    System.out.print("Valor da transferência: R$ ");
                    double valor = scanner.nextDouble();
                    scanner.nextLine();

                    ContaBancaria origem = service.buscarContaPorNumero(numeroOrigem);
                    ContaBancaria destino = service.buscarContaPorNumero(numeroDestino);

                    if (origem == null) {
                        System.out.println("Conta de origem não encontrada.");
                    } else if (destino == null) {
                        System.out.println("Conta de destino não encontrada.");
                    } else if (service.transferir(origem, destino, valor)) {
                        System.out.println("Transferência realizada com sucesso.");
                    } else {
                        System.out.println("Valor inválido ou saldo insuficiente.");
                    }

                    break;

                case 6:
                    System.out.println("\n--- Dados da Conta ---");
                    if (conta != null) {
                        service.exibirDados(conta);
                    } else {
                        System.out.println("Nenhuma conta foi criada ainda.");
                    }
                    break;

                case 7:
                    System.out.println("Lista de contas cadastradas:");
                    service.listarContas();
                    break;

                case 8:
                    System.out.println("\nEncerrando o sistema...");
                    scanner.close();
                    return;

                default:
                    System.out.println("\nOpção inválida. Tente novamente.");
            }
        }
    }
}
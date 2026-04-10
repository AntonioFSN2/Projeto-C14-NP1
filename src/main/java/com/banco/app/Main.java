package com.banco.app;

import com.banco.exception.ContaNaoEncontradaException;
import com.banco.exception.SaldoInsuficienteException;
import com.banco.exception.ValorInvalidoException;
import com.banco.model.ContaBancaria;
import com.banco.service.ContaService;

import java.math.BigDecimal;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContaService service = new ContaService();
        Scanner scanner = new Scanner(System.in);

        try {
            ContaBancaria conta1 = service.criarConta("Danilo");
            ContaBancaria conta2 = service.criarConta("Antonio");
            service.depositar(conta1.getNumero(), new BigDecimal("500.00"));
            service.depositar(conta2.getNumero(), new BigDecimal("200.00"));
        } catch (ValorInvalidoException | ContaNaoEncontradaException e) {
            throw new RuntimeException("Erro na inicialização: " + e.getMessage(), e);
        }

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
            System.out.println("8 - Exibir extrato");
            System.out.println("9 - Sair");
            System.out.print("Escolha uma opção: ");

            int opcao;
            try {
                opcao = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                scanner.nextLine();
                System.out.println("\nOpção inválida. Digite um número.");
                continue;
            }

            switch (opcao) {
                case 1:
                    System.out.println("\n--- Criação de Conta ---");
                    System.out.print("Nome do titular: ");
                    String titular = scanner.nextLine();
                    try {
                        ContaBancaria nova = service.criarConta(titular);
                        System.out.println("Conta criada com sucesso. Número: " + nova.getNumero());
                    } catch (ValorInvalidoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 2:
                    System.out.println("\n--- Consulta de Saldo ---");
                    System.out.print("Número da conta: ");
                    try {
                        int num = scanner.nextInt();
                        scanner.nextLine();
                        System.out.printf("Saldo atual: R$ %.2f%n", service.consultarSaldo(num));
                    } catch (InputMismatchException e) {
                        scanner.nextLine();
                        System.out.println("Número inválido.");
                    } catch (ContaNaoEncontradaException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 3:
                    System.out.println("\n--- Depósito ---");
                    System.out.print("Número da conta: ");
                    try {
                        int num = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Valor: R$ ");
                        BigDecimal valor = scanner.nextBigDecimal();
                        scanner.nextLine();
                        service.depositar(num, valor);
                        System.out.printf("Depósito realizado. Novo saldo: R$ %.2f%n", service.consultarSaldo(num));
                    } catch (InputMismatchException e) {
                        scanner.nextLine();
                        System.out.println("Valor inválido.");
                    } catch (ContaNaoEncontradaException | ValorInvalidoException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 4:
                    System.out.println("\n--- Saque ---");
                    System.out.print("Número da conta: ");
                    try {
                        int num = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Valor: R$ ");
                        BigDecimal valor = scanner.nextBigDecimal();
                        scanner.nextLine();
                        service.sacar(num, valor);
                        System.out.printf("Saque realizado. Novo saldo: R$ %.2f%n", service.consultarSaldo(num));
                    } catch (InputMismatchException e) {
                        scanner.nextLine();
                        System.out.println("Valor inválido.");
                    } catch (ContaNaoEncontradaException | ValorInvalidoException | SaldoInsuficienteException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 5:
                    System.out.println("\n--- Transferência ---");
                    System.out.print("Número da conta de origem: ");
                    try {
                        int origem = scanner.nextInt();
                        System.out.print("Número da conta de destino: ");
                        int destino = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Valor: R$ ");
                        BigDecimal valor = scanner.nextBigDecimal();
                        scanner.nextLine();
                        service.transferir(origem, destino, valor);
                        System.out.println("Transferência realizada com sucesso.");
                    } catch (InputMismatchException e) {
                        scanner.nextLine();
                        System.out.println("Valor inválido.");
                    } catch (ContaNaoEncontradaException | ValorInvalidoException | SaldoInsuficienteException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("\n--- Dados da Conta ---");
                    System.out.print("Número da conta: ");
                    try {
                        int num = scanner.nextInt();
                        scanner.nextLine();
                        service.exibirDados(num);
                    } catch (InputMismatchException e) {
                        scanner.nextLine();
                        System.out.println("Número inválido.");
                    } catch (ContaNaoEncontradaException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 7:
                    service.listarContas();
                    break;

                case 8:
                    System.out.println("\n--- Extrato ---");
                    System.out.print("Número da conta: ");
                    try {
                        int num = scanner.nextInt();
                        scanner.nextLine();
                        service.exibirExtrato(num);
                    } catch (InputMismatchException e) {
                        scanner.nextLine();
                        System.out.println("Número inválido.");
                    } catch (ContaNaoEncontradaException e) {
                        System.out.println("Erro: " + e.getMessage());
                    }
                    break;

                case 9:
                    System.out.println("\nEncerrando o sistema...");
                    scanner.close();
                    return;

                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}

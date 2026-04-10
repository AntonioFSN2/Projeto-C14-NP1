package com.banco.app;

import com.banco.model.ContaBancaria;
import com.banco.service.ContaService;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ContaService service = new ContaService();
        Scanner scanner = new Scanner(System.in);
        ContaBancaria conta = null;

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Criar conta");
            System.out.println("2. Consultar saldo");
            System.out.println("3. Depositar");
            System.out.println("4. Sacar");
            System.out.println("5. Transferir");
            System.out.println("6. Exibir dados");
            System.out.println("7. Sair");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Número da conta: ");
                    String numero = scanner.nextLine();
                    System.out.print("Titular: ");
                    String titular = scanner.nextLine();
                    conta = service.criarConta(numero, titular);
                    System.out.println("Conta criada com sucesso.");
                    break;
                case 2:
                    if (conta != null) {
                        System.out.println("Saldo: " + service.consultarSaldo(conta));
                    } else {
                        System.out.println("Conta não criada.");
                    }
                    break;
                case 3:
                    if (conta != null) {
                        System.out.print("Valor a depositar: ");
                        double valorDep = scanner.nextDouble();
                        if (service.depositar(conta, valorDep)) {
                            System.out.println("Depósito realizado.");
                        } else {
                            System.out.println("Valor inválido.");
                        }
                    } else {
                        System.out.println("Conta não criada.");
                    }
                    break;
                case 4:
                    if (conta != null) {
                        System.out.print("Valor a sacar: ");
                        double valorSac = scanner.nextDouble();
                        if (service.sacar(conta, valorSac)) {
                            System.out.println("Saque realizado.");
                        } else {
                            System.out.println("Valor inválido ou saldo insuficiente.");
                        }
                    } else {
                        System.out.println("Conta não criada.");
                    }
                    break;
                case 5:
                    if (conta != null) {
                        System.out.print("Número da conta destino: ");
                        String numDestino = scanner.nextLine();
                        System.out.print("Titular destino: ");
                        String titDestino = scanner.nextLine();
                        ContaBancaria destino = service.criarConta(numDestino, titDestino);
                        System.out.print("Valor a transferir: ");
                        double valorTrans = scanner.nextDouble();
                        if (service.transferir(conta, destino, valorTrans)) {
                            System.out.println("Transferência realizada.");
                        } else {
                            System.out.println("Valor inválido ou saldo insuficiente.");
                        }
                    } else {
                        System.out.println("Conta não criada.");
                    }
                    break;
                case 6:
                    if (conta != null) {
                        service.exibirDados(conta);
                    } else {
                        System.out.println("Conta não criada.");
                    }
                    break;
                case 7:
                    System.out.println("Saindo...");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
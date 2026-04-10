package com.banco.service;

import com.banco.model.ContaBancaria;
import java.util.ArrayList;
import java.util.List;

public class ContaService {
    private List<ContaBancaria> contas;

    public ContaService() {
        this.contas = new ArrayList<>();
    }

    public ContaBancaria criarConta(String titular) {
        if (titular == null || titular.isBlank()) {
            return null;
        }

        ContaBancaria conta = new ContaBancaria(titular);
        contas.add(conta);
        return conta;
    }

    public ContaBancaria buscarContaPorNumero(int numero) {
        for (ContaBancaria conta : contas) {
            if (conta.getNumero() == numero) {
                return conta;
            }
        }
        return null;
    }

    public double consultarSaldo(ContaBancaria conta) {
        if (conta == null) {
            return -1;
        }
        return conta.getSaldo();
    }

    public boolean depositar(ContaBancaria conta, double valor) {
        if (conta == null || valor <= 0) {
            return false;
        }

        conta.setSaldo(conta.getSaldo() + valor);
        return true;
    }

    public boolean sacar(ContaBancaria conta, double valor) {
        if (conta == null || valor <= 0 || valor > conta.getSaldo()) {
            return false;
        }

        conta.setSaldo(conta.getSaldo() - valor);
        return true;
    }

    public boolean transferir(ContaBancaria origem, ContaBancaria destino, double valor) {
        if (origem == null || destino == null || valor <= 0 || valor > origem.getSaldo()) {
            return false;
        }

        origem.setSaldo(origem.getSaldo() - valor);
        destino.setSaldo(destino.getSaldo() + valor);
        return true;
    }

    public void exibirDados(ContaBancaria conta) {
        if (conta != null) {
            System.out.println("\n==============================");
            System.out.println("       DADOS DA CONTA");
            System.out.println("==============================");
            System.out.println("Número : " + conta.getNumero());
            System.out.println("Titular: " + conta.getTitular());
            System.out.printf("Saldo  : R$ %.2f%n", conta.getSaldo());
            System.out.println("==============================");
        }
    }

    public void listarContas() {
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }

        System.out.println("\n==============================");
        System.out.println("     CONTAS CADASTRADAS");
        System.out.println("==============================");

        for (ContaBancaria conta : contas) {
            System.out.println("Número : " + conta.getNumero());
            System.out.println("Titular: " + conta.getTitular());
            System.out.printf("Saldo  : R$ %.2f%n", conta.getSaldo());
            System.out.println("------------------------------");
        }
    }

    public List<ContaBancaria> getContas() {
        return contas;
    }
}
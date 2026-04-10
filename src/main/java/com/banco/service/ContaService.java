
 package com.banco.service;

import com.banco.model.ContaBancaria;
import com.banco.model.Transacao;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContaService {
    private List<ContaBancaria> contas;
    private Random random;

    public ContaService() {
        this.contas = new ArrayList<>();
        this.random = new Random();
    }

    public ContaBancaria criarConta(String titular) {
        if (titular == null || titular.isBlank()) {
            return null;
        }

        String numero = gerarNumeroUnico();
        ContaBancaria conta = new ContaBancaria(numero, titular);
        contas.add(conta);
        return conta;
    }

    private String gerarNumeroConta() {
        int parte1 = random.nextInt(10000); // 0000 até 9999
        int parte2 = random.nextInt(100);   // 00 até 99

        return String.format("%04d-%02d", parte1, parte2);
    }

    private String gerarNumeroUnico() {
        String numeroGerado;

        do {
            numeroGerado = gerarNumeroConta();
        } while (buscarContaPorNumero(numeroGerado) != null);

        return numeroGerado;
    }

    public ContaBancaria buscarContaPorNumero(String numero) {
        for (ContaBancaria conta : contas) {
            if (conta.getNumero().equals(numero)) {
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
    conta.adicionarTransacao(new Transacao("DEPÓSITO", valor, "Depósito realizado na conta " + conta.getNumero()
    ));
    return true;
    }


    public boolean sacar(ContaBancaria conta, double valor) {
    if (conta == null || valor <= 0 || valor > conta.getSaldo()) {
        return false;
    }

    conta.setSaldo(conta.getSaldo() - valor);
    conta.adicionarTransacao(new Transacao(
            "SAQUE",
            valor,
            "Saque realizado na conta " + conta.getNumero()
    ));
    return true;
    }


    public boolean transferir(ContaBancaria origem, ContaBancaria destino, double valor) {
    if (origem == null || destino == null || valor <= 0 || valor > origem.getSaldo()) {
        return false;
    }
    origem.setSaldo(origem.getSaldo() - valor);
    destino.setSaldo(destino.getSaldo() + valor);
    origem.adicionarTransacao(new Transacao("TRANSFERÊNCIA ENVIADA",valor,"Transferência enviada para a conta " + destino.getNumero()));
    destino.adicionarTransacao(new Transacao("TRANSFERÊNCIA RECEBIDA",valor,"Transferência recebida da conta " + origem.getNumero()));
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

    public void exibirHistorico(ContaBancaria conta) {
    if (conta == null) {
        System.out.println("Conta não encontrada.");
        return;
    }

    System.out.println("\n==============================");
    System.out.println("   HISTÓRICO DE TRANSAÇÕES");
    System.out.println("==============================");
    System.out.println("Conta  : " + conta.getNumero());
    System.out.println("Titular: " + conta.getTitular());
    System.out.println("------------------------------");

    if (conta.getHistorico().isEmpty()) {
        System.out.println("Nenhuma transação registrada.");
    } else {
        for (Transacao transacao : conta.getHistorico()) {
            System.out.println(transacao);
        }
    }

    System.out.println("==============================");
}
}


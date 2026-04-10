package com.banco.service;

import com.banco.model.ContaBancaria;

public class ContaService {
    public ContaBancaria criarConta(String numero, String titular) {
        return new ContaBancaria(numero, titular);
    }

    public double consultarSaldo(ContaBancaria conta) {
        return conta.getSaldo();
    }

    public boolean depositar(ContaBancaria conta, double valor) {
        if (valor <= 0) {
            return false;
        }
        conta.setSaldo(conta.getSaldo() + valor);
        return true;
    }

    public boolean sacar(ContaBancaria conta, double valor) {
        if (valor <= 0 || valor > conta.getSaldo()) {
            return false;
        }
        conta.setSaldo(conta.getSaldo() - valor);
        return true;
    }

    public boolean transferir(ContaBancaria origem, ContaBancaria destino, double valor) {
        if (valor <= 0 || valor > origem.getSaldo()) {
            return false;
        }
        origem.setSaldo(origem.getSaldo() - valor);
        destino.setSaldo(destino.getSaldo() + valor);
        return true;
    }

    public void exibirDados(ContaBancaria conta) {
        System.out.println("Número: " + conta.getNumero());
        System.out.println("Titular: " + conta.getTitular());
        System.out.println("Saldo: " + conta.getSaldo());
    }
}
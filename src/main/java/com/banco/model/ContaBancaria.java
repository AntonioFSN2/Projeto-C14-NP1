package com.banco.model;

import java.util.ArrayList;
import java.util.List;

public class ContaBancaria {
    private String numero;
    private String titular;
    private double saldo;
    private List<Transacao> historico;

    public ContaBancaria(String numero, String titular) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = 0.0;
        this.historico = new ArrayList<>();
    }

    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public List<Transacao> getHistorico() {
        return historico;
    }

    public void adicionarTransacao(Transacao transacao) {
        historico.add(transacao);
    }
}
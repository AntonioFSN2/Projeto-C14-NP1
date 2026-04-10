package com.banco.model;

public class ContaBancaria {
    private static int proximoNumero = 1;

    private int numero;
    private String titular;
    private double saldo;

    public ContaBancaria(String titular) {
        this.numero = proximoNumero++;
        this.titular = titular;
        this.saldo = 0.0;
    }

    public int getNumero() {
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
}
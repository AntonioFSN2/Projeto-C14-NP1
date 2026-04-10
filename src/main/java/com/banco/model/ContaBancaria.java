package com.banco.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContaBancaria {
    private final String numero;
    private final String titular;
    private BigDecimal saldo;
    private final List<Transacao> extrato;

    public ContaBancaria(String numero, String titular) {
        this.numero = numero;
        this.titular = titular;
        this.saldo = BigDecimal.ZERO;
        this.extrato = new ArrayList<>();
    }

    public void depositar(BigDecimal valor) {
        this.saldo = this.saldo.add(valor);
        extrato.add(new Transacao("DEPÓSITO", valor, "Depósito realizado na conta " + numero));
    }

    public void sacar(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
        extrato.add(new Transacao("SAQUE", valor, "Saque realizado na conta " + numero));
    }

    public void registrarTransferencia(BigDecimal valor, boolean enviada, String contaContrapartida) {
        if (enviada) {
            this.saldo = this.saldo.subtract(valor);
            extrato.add(new Transacao("TRANSFERÊNCIA ENVIADA", valor,
                    "Transferência enviada para a conta " + contaContrapartida));
        } else {
            this.saldo = this.saldo.add(valor);
            extrato.add(new Transacao("TRANSFERÊNCIA RECEBIDA", valor,
                    "Transferência recebida da conta " + contaContrapartida));
        }
    }

    public String getNumero() { return numero; }
    public String getTitular() { return titular; }
    public BigDecimal getSaldo() { return saldo; }
    public List<Transacao> getExtrato() { return Collections.unmodifiableList(extrato); }
}

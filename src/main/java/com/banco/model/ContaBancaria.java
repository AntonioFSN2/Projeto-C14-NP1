package com.banco.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContaBancaria {
    private static int proximoNumero = 1;

    private final int numero;
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
        extrato.add(new Transacao(Transacao.Tipo.DEPOSITO, valor));
    }

    public void sacar(BigDecimal valor) {
        this.saldo = this.saldo.subtract(valor);
        extrato.add(new Transacao(Transacao.Tipo.SAQUE, valor));
    }

    public void registrarTransferencia(BigDecimal valor, boolean enviada) {
        Transacao.Tipo tipo = enviada
                ? Transacao.Tipo.TRANSFERENCIA_ENVIADA
                : Transacao.Tipo.TRANSFERENCIA_RECEBIDA;
        this.saldo = enviada ? this.saldo.subtract(valor) : this.saldo.add(valor);
        extrato.add(new Transacao(tipo, valor));
    }

    public int getNumero() { return numero; }
    public String getTitular() { return titular; }
    public BigDecimal getSaldo() { return saldo; }
    public List<Transacao> getExtrato() { return Collections.unmodifiableList(extrato); }

    static void resetarContador() {
        proximoNumero = 1;
    }

    public List<Transacao> getHistorico() {
        return historico;
    }

    public void adicionarTransacao(Transacao transacao) {
        historico.add(transacao);
    }
}

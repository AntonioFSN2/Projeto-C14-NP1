package com.banco.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {

    public enum Tipo {
        DEPOSITO, SAQUE, TRANSFERENCIA_ENVIADA, TRANSFERENCIA_RECEBIDA
    }

    private final Tipo tipo;
    private final BigDecimal valor;
    private final LocalDateTime momento;

    public Transacao(Tipo tipo, BigDecimal valor) {
        this.tipo = tipo;
        this.valor = valor;
        this.momento = LocalDateTime.now();
    }

    public Tipo getTipo() { return tipo; }
    public BigDecimal getValor() { return valor; }
    public LocalDateTime getMomento() { return momento; }

    @Override
    public String toString() {
        return String.format("[%s] %s - R$ %s", momento, tipo.name(), valor.toPlainString());
    }
}

package com.banco.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Transacao {
    private final String tipo;
    private final BigDecimal valor;
    private final String descricao;
    private final LocalDateTime dataHora;

    public Transacao(String tipo, BigDecimal valor, String descricao) {
        this.tipo = tipo;
        this.valor = valor;
        this.descricao = descricao;
        this.dataHora = LocalDateTime.now();
    }

    public String getTipo() { return tipo; }
    public BigDecimal getValor() { return valor; }
    public String getDescricao() { return descricao; }
    public LocalDateTime getDataHora() { return dataHora; }

    @Override
    public String toString() {
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return "[" + dataHora.format(formato) + "] "
                + tipo + " - R$ " + String.format("%.2f", valor)
                + " | " + descricao;
    }
}

package com.banco.exception;

import java.math.BigDecimal;

public class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(BigDecimal valorSolicitado, BigDecimal saldoDisponivel) {
        super("Saldo insuficiente. Solicitado: R$ " + valorSolicitado.toPlainString()
              + ", disponível: R$ " + saldoDisponivel.toPlainString());
    }
}

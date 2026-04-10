package com.banco.exception;

public class ContaNaoEncontradaException extends Exception {
    public ContaNaoEncontradaException(String numero) {
        super("Conta não encontrada: " + numero);
    }
}

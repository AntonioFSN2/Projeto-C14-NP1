package com.banco.exception;

public class ContaNaoEncontradaException extends Exception {
    public ContaNaoEncontradaException(int numero) {
        super("Conta não encontrada: " + numero);
    }
}

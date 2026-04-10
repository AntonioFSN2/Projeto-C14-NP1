package com.banco.service;

import com.banco.exception.ContaNaoEncontradaException;
import com.banco.exception.SaldoInsuficienteException;
import com.banco.exception.ValorInvalidoException;
import com.banco.model.ContaBancaria;
import com.banco.model.Transacao;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContaService {
    private final List<ContaBancaria> contas;

    public ContaService() {
        this.contas = new ArrayList<>();
    }

    public ContaBancaria criarConta(String titular) throws ValorInvalidoException {
        if (titular == null || titular.isBlank()) {
            throw new ValorInvalidoException("Nome do titular não pode ser vazio.");
        }
        ContaBancaria conta = new ContaBancaria(titular);
        contas.add(conta);
        return conta;
    }

    public ContaBancaria buscarContaPorNumero(int numero) throws ContaNaoEncontradaException {
        for (ContaBancaria conta : contas) {
            if (conta.getNumero() == numero) {
                return conta;
            }
        }
        throw new ContaNaoEncontradaException(numero);
    }

    public BigDecimal consultarSaldo(int numeroConta) throws ContaNaoEncontradaException {
        return buscarContaPorNumero(numeroConta).getSaldo();
    }

    public void depositar(int numeroConta, BigDecimal valor)
            throws ContaNaoEncontradaException, ValorInvalidoException {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("O valor do depósito deve ser positivo.");
        }
        buscarContaPorNumero(numeroConta).depositar(valor);
    }

    public void sacar(int numeroConta, BigDecimal valor)
            throws ContaNaoEncontradaException, ValorInvalidoException, SaldoInsuficienteException {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("O valor do saque deve ser positivo.");
        }
        ContaBancaria conta = buscarContaPorNumero(numeroConta);
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException(valor, conta.getSaldo());
        }
        conta.sacar(valor);
    }

    public void transferir(int numeroOrigem, int numeroDestino, BigDecimal valor)
            throws ContaNaoEncontradaException, ValorInvalidoException, SaldoInsuficienteException {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("O valor da transferência deve ser positivo.");
        }
        ContaBancaria origem = buscarContaPorNumero(numeroOrigem);
        ContaBancaria destino = buscarContaPorNumero(numeroDestino);
        if (origem.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException(valor, origem.getSaldo());
        }
        origem.registrarTransferencia(valor, true);
        destino.registrarTransferencia(valor, false);
    }

    public void exibirDados(int numeroConta) throws ContaNaoEncontradaException {
        ContaBancaria conta = buscarContaPorNumero(numeroConta);
        System.out.println("\n==============================");
        System.out.println("       DADOS DA CONTA");
        System.out.println("==============================");
        System.out.println("Número : " + conta.getNumero());
        System.out.println("Titular: " + conta.getTitular());
        System.out.printf("Saldo  : R$ %.2f%n", conta.getSaldo());
        System.out.println("==============================");
    }

    public void exibirExtrato(int numeroConta) throws ContaNaoEncontradaException {
        ContaBancaria conta = buscarContaPorNumero(numeroConta);
        System.out.println("\n==============================");
        System.out.println("    EXTRATO DA CONTA " + numeroConta);
        System.out.println("==============================");
        List<Transacao> extrato = conta.getExtrato();
        if (extrato.isEmpty()) {
            System.out.println("Nenhuma transação registrada.");
        } else {
            for (Transacao t : extrato) {
                System.out.println(t);
            }
        }
        System.out.println("==============================");
    }

    public void listarContas() {
        if (contas.isEmpty()) {
            System.out.println("Nenhuma conta cadastrada.");
            return;
        }
        System.out.println("\n==============================");
        System.out.println("     CONTAS CADASTRADAS");
        System.out.println("==============================");
        for (ContaBancaria conta : contas) {
            System.out.println("Número : " + conta.getNumero());
            System.out.println("Titular: " + conta.getTitular());
            System.out.printf("Saldo  : R$ %.2f%n", conta.getSaldo());
            System.out.println("------------------------------");
        }
    }

    public List<ContaBancaria> getContas() {
        return Collections.unmodifiableList(contas);
    }
}

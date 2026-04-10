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
import java.util.Random;

public class ContaService {
    private final List<ContaBancaria> contas;
    private final Random random;

    public ContaService() {
        this.contas = new ArrayList<>();
        this.random = new Random();
    }

    private String gerarNumeroConta() {
        int parte1 = random.nextInt(10000);
        int parte2 = random.nextInt(100);
        return String.format("%04d-%02d", parte1, parte2);
    }

    private String gerarNumeroUnico() {
        String numero;
        do {
            numero = gerarNumeroConta();
        } while (buscarContaPorNumeroInterno(numero) != null);
        return numero;
    }

    private ContaBancaria buscarContaPorNumeroInterno(String numero) {
        for (ContaBancaria conta : contas) {
            if (conta.getNumero().equals(numero)) {
                return conta;
            }
        }
        return null;
    }

    public ContaBancaria criarConta(String titular) throws ValorInvalidoException {
        if (titular == null || titular.isBlank()) {
            throw new ValorInvalidoException("Nome do titular não pode ser vazio.");
        }
        ContaBancaria conta = new ContaBancaria(gerarNumeroUnico(), titular);
        contas.add(conta);
        return conta;
    }

    public ContaBancaria buscarContaPorNumero(String numero) throws ContaNaoEncontradaException {
        ContaBancaria conta = buscarContaPorNumeroInterno(numero);
        if (conta == null) {
            throw new ContaNaoEncontradaException(numero);
        }
        return conta;
    }

    public BigDecimal consultarSaldo(String numeroConta) throws ContaNaoEncontradaException {
        return buscarContaPorNumero(numeroConta).getSaldo();
    }

    public void depositar(String numeroConta, BigDecimal valor)
            throws ContaNaoEncontradaException, ValorInvalidoException {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("O valor do depósito deve ser positivo.");
        }
        buscarContaPorNumero(numeroConta).depositar(valor);
    }

    public void sacar(String numeroConta, BigDecimal valor)
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

    public void transferir(String numeroOrigem, String numeroDestino, BigDecimal valor)
            throws ContaNaoEncontradaException, ValorInvalidoException, SaldoInsuficienteException {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException("O valor da transferência deve ser positivo.");
        }
        ContaBancaria origem = buscarContaPorNumero(numeroOrigem);
        ContaBancaria destino = buscarContaPorNumero(numeroDestino);
        if (origem.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsuficienteException(valor, origem.getSaldo());
        }
        origem.registrarTransferencia(valor, true, destino.getNumero());
        destino.registrarTransferencia(valor, false, origem.getNumero());
    }

    public void exibirDados(String numeroConta) throws ContaNaoEncontradaException {
        ContaBancaria conta = buscarContaPorNumero(numeroConta);
        System.out.println("\n==============================");
        System.out.println("       DADOS DA CONTA");
        System.out.println("==============================");
        System.out.println("Número : " + conta.getNumero());
        System.out.println("Titular: " + conta.getTitular());
        System.out.printf("Saldo  : R$ %.2f%n", conta.getSaldo());
        System.out.println("==============================");
    }

    public void exibirExtrato(String numeroConta) throws ContaNaoEncontradaException {
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

    public void exibirHistoricoTransferencias(String numeroConta) throws ContaNaoEncontradaException {
        ContaBancaria conta = buscarContaPorNumero(numeroConta);
        System.out.println("\n==============================");
        System.out.println(" TRANSFERÊNCIAS DA CONTA " + numeroConta);
        System.out.println("==============================");
        List<Transacao> transferencias = conta.getExtrato().stream()
                .filter(t -> t.getTipo().startsWith("TRANSFERÊNCIA"))
                .toList();
        if (transferencias.isEmpty()) {
            System.out.println("Nenhuma transferência registrada.");
        } else {
            for (Transacao t : transferencias) {
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

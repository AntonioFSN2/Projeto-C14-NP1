package com.banco.service;

import com.banco.model.Cliente;
import com.banco.model.ContaBancaria;
import com.banco.model.Transacao;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ContaService {
    private List<Cliente> clientes;
    private Random random;

    public ContaService() {
        this.clientes = new ArrayList<>();
        this.random = new Random();
    }

    private String padronizarCpf(String cpf) {
        if (cpf == null) {
            return null;
        }

        String cpfLimpo = cpf.replace(".", "")
                             .replace("-", "")
                             .replace(" ", "");

        if (!cpfLimpo.matches("\\d{11}")) {
            return null;
        }

        return cpfLimpo;
    }

    public Cliente cadastrarCliente(String cpf, String nome, String dataNascimentoStr) {
        String cpfPadronizado = padronizarCpf(cpf);

        if (cpfPadronizado == null || nome == null || nome.trim().isEmpty()) {
            return null;
        }

        LocalDate dataNascimento;

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            dataNascimento = LocalDate.parse(dataNascimentoStr, formatter);
        } catch (Exception e) {
            return null;
        }

        if (dataNascimento.isAfter(LocalDate.now())) {
            return null;
        }

        if (buscarClientePorCpf(cpfPadronizado) != null) {
            return null;
        }

        String numeroConta = gerarNumeroUnico();
        ContaBancaria conta = new ContaBancaria(numeroConta, nome);

        Cliente cliente = new Cliente(cpfPadronizado, nome, conta, dataNascimento);
        clientes.add(cliente);

        return cliente;
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

    public Cliente buscarClientePorCpf(String cpf) {
        String cpfPadronizado = padronizarCpf(cpf);

        if (cpfPadronizado == null) {
            return null;
        }

        for (Cliente cliente : clientes) {
            if (cliente.getCpf().equals(cpfPadronizado)) {
                return cliente;
            }
        }
        return null;
    }

    public ContaBancaria buscarContaPorNumero(String numero) {
        for (Cliente cliente : clientes) {
            if (cliente.getConta().getNumero().equals(numero)) {
                return cliente.getConta();
            }
        }
        return null;
    }

    public double consultarSaldo(ContaBancaria conta) {
        if (conta == null) {
            return 0;
        }
        return conta.getSaldo();
    }

    public boolean depositar(ContaBancaria conta, double valor) {
        if (conta == null || valor <= 0) {
            return false;
        }

        conta.setSaldo(conta.getSaldo() + valor);
        conta.adicionarTransacao(new Transacao(
                "DEPÓSITO",
                valor,
                "Depósito realizado na conta " + conta.getNumero()
        ));

        return true;
    }

    public boolean sacar(ContaBancaria conta, double valor) {
        if (conta == null || valor <= 0 || valor > conta.getSaldo()) {
            return false;
        }

        conta.setSaldo(conta.getSaldo() - valor);
        conta.adicionarTransacao(new Transacao(
                "SAQUE",
                valor,
                "Saque realizado na conta " + conta.getNumero()
        ));

        return true;
    }

    public boolean transferir(ContaBancaria origem, ContaBancaria destino, double valor) {
        if (origem == null || destino == null || valor <= 0 || valor > origem.getSaldo()) {
            return false;
        }

        origem.setSaldo(origem.getSaldo() - valor);
        destino.setSaldo(destino.getSaldo() + valor);

        origem.adicionarTransacao(new Transacao(
                "TRANSFERÊNCIA ENVIADA",
                valor,
                "Transferência enviada para a conta " + destino.getNumero()
        ));

        destino.adicionarTransacao(new Transacao(
                "TRANSFERÊNCIA RECEBIDA",
                valor,
                "Transferência recebida da conta " + origem.getNumero()
        ));

        return true;
    }

    public void exibirDados(ContaBancaria conta) {
        if (conta != null) {
            System.out.println("\n==============================");
            System.out.println("       DADOS DA CONTA");
            System.out.println("==============================");
            System.out.println("Número : " + conta.getNumero());
            System.out.println("Titular: " + conta.getTitular());
            System.out.printf("Saldo  : R$ %.2f%n", conta.getSaldo());
            System.out.println("==============================");
        } else {
            System.out.println("Conta não encontrada.");
        }
    }

    public void listarClientes() {
        if (clientes.isEmpty()) {
            System.out.println("Nenhum cliente cadastrado.");
            return;
        }

        System.out.println("\n==============================");
        System.out.println("    CLIENTES CADASTRADOS");
        System.out.println("==============================");

        for (Cliente cliente : clientes) {
            System.out.println("Nome       : " + cliente.getNome());
            System.out.println("CPF        : " + cliente.getCpfFormatado());
            System.out.println("Data Nasc. : " + cliente.getDataNascimentoFormatada());
            System.out.println("Conta      : " + cliente.getConta().getNumero());
            System.out.printf("Saldo      : R$ %.2f%n", cliente.getConta().getSaldo());
            System.out.println("------------------------------");
        }
    }

    public void exibirHistorico(ContaBancaria conta) {
        if (conta == null) {
            System.out.println("Conta não encontrada.");
            return;
        }

        System.out.println("\n==============================");
        System.out.println("   HISTÓRICO DE TRANSAÇÕES");
        System.out.println("==============================");
        System.out.println("Conta  : " + conta.getNumero());
        System.out.println("Titular: " + conta.getTitular());
        System.out.println("------------------------------");

        if (conta.getHistorico().isEmpty()) {
            System.out.println("Nenhuma transação registrada.");
        } else {
            for (Transacao transacao : conta.getHistorico()) {
                System.out.println(transacao);
            }
        }

        System.out.println("==============================");
    }

    public List<Cliente> getClientes() {
        return clientes;
    }
}

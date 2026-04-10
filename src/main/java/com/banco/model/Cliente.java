package com.banco.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Cliente {
    private String cpf;
    private String nome;
    private ContaBancaria conta;
    private LocalDate dataNascimento;

    public Cliente(String cpf, String nome, ContaBancaria conta, LocalDate dataNascimento) {
        this.cpf = cpf;
        this.nome = nome;
        this.conta = conta;
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public String getCpfFormatado() {
        return cpf.substring(0, 3) + "." +
               cpf.substring(3, 6) + "." +
               cpf.substring(6, 9) + "-" +
               cpf.substring(9, 11);
    }

    public String getNome() {
        return nome;
    }

    public ContaBancaria getConta() {
        return conta;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public String getDataNascimentoFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return dataNascimento.format(formatter);
    }
}
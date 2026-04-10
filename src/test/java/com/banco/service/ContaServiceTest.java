package com.banco.service;

import com.banco.model.Cliente;
import com.banco.model.ContaBancaria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContaServiceTest {

    private ContaService contaService;

    @BeforeEach
    void setUp() {
        contaService = new ContaService();
    }

    @Test
    void cadastrarCliente_dadosValidos_retornaClienteComContaAssociada() {
        Cliente cliente = contaService.cadastrarCliente("12345678901", "João Silva", "01/01/1990");

        assertNotNull(cliente);
        assertEquals("12345678901", cliente.getCpf());
        assertEquals("João Silva", cliente.getNome());
        assertNotNull(cliente.getConta());
        assertEquals("João Silva", cliente.getConta().getTitular());
        assertTrue(contaService.getClientes().contains(cliente));
    }

    @Test
    void buscarClientePorCpf_cpfExistente_retornaClienteCorreto() {
        contaService.cadastrarCliente("12345678901", "João Silva", "01/01/1990");

        Cliente cliente = contaService.buscarClientePorCpf("12345678901");

        assertNotNull(cliente);
        assertEquals("12345678901", cliente.getCpf());
        assertEquals("João Silva", cliente.getNome());
    }

    @Test
    void buscarContaPorNumero_numeroExistente_retornaContaCorreta() {
        Cliente cliente = contaService.cadastrarCliente("12345678901", "João Silva", "01/01/1990");
        String numeroConta = cliente.getConta().getNumero();

        ContaBancaria conta = contaService.buscarContaPorNumero(numeroConta);

        assertNotNull(conta);
        assertEquals(numeroConta, conta.getNumero());
        assertEquals("João Silva", conta.getTitular());
    }

    @Test
    void depositar_valorPositivo_aumentaSaldoERegistraHistorico() {
        Cliente cliente = contaService.cadastrarCliente("12345678901", "João Silva", "01/01/1990");
        ContaBancaria conta = cliente.getConta();
        double saldoInicial = conta.getSaldo();
        int historicoInicial = conta.getHistorico().size();

        boolean resultado = contaService.depositar(conta, 100.0);

        assertTrue(resultado);
        assertEquals(saldoInicial + 100.0, conta.getSaldo());
        assertEquals(historicoInicial + 1, conta.getHistorico().size());
        assertEquals("DEPÓSITO", conta.getHistorico().get(historicoInicial).getTipo());
    }

    @Test
    void sacar_valorValido_diminuiSaldoERegistraHistorico() {
        Cliente cliente = contaService.cadastrarCliente("12345678901", "João Silva", "01/01/1990");
        ContaBancaria conta = cliente.getConta();
        contaService.depositar(conta, 200.0);
        double saldoInicial = conta.getSaldo();
        int historicoInicial = conta.getHistorico().size();

        boolean resultado = contaService.sacar(conta, 50.0);

        assertTrue(resultado);
        assertEquals(saldoInicial - 50.0, conta.getSaldo());
        assertEquals(historicoInicial + 1, conta.getHistorico().size());
        assertEquals("SAQUE", conta.getHistorico().get(historicoInicial).getTipo());
    }
}
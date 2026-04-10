package com.banco.service;

import com.banco.model.Cliente;
import com.banco.model.ContaBancaria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContaServiceTest {

    private ContaService service;

    @BeforeEach
    void setUp() {
        service = new ContaService();
    }

    // =====================================================================
    // Fluxo de Extensão — Cenários 16 a 20 (Matheus Vieira)
    // =====================================================================

    @Test
    void depositar_valorZeroOuNegativo_retornaFalse() {
        Cliente cliente = service.cadastrarCliente("19191919191", "Matheus", "01/01/1990");
        ContaBancaria conta = cliente.getConta();

        boolean resultadoZero = service.depositar(conta, 0);
        boolean resultadoNegativo = service.depositar(conta, -50.0);

        assertFalse(resultadoZero);
        assertFalse(resultadoNegativo);
        assertEquals(0.0, conta.getSaldo());
    }

    @Test
    void sacar_saldoInsuficiente_retornaFalse() {
        Cliente cliente = service.cadastrarCliente("20202020202", "Matheus", "01/01/1990");
        ContaBancaria conta = cliente.getConta();
        service.depositar(conta, 50.0);

        boolean resultado = service.sacar(conta, 100.0);

        assertFalse(resultado);
        assertEquals(50.0, conta.getSaldo());
    }

    @Test
    void sacar_valorNegativo_retornaFalse() {
        Cliente cliente = service.cadastrarCliente("21212121212", "Matheus", "01/01/1990");
        ContaBancaria conta = cliente.getConta();

        boolean resultado = service.sacar(conta, -10.0);

        assertFalse(resultado);
    }

    @Test
    void transferir_saldoInsuficienteNaOrigem_retornaFalse() {
        Cliente c1 = service.cadastrarCliente("23232323232", "Matheus", "01/01/1990");
        Cliente c2 = service.cadastrarCliente("24242424242", "Matheus2", "01/01/1990");
        service.depositar(c1.getConta(), 30.0);

        boolean resultado = service.transferir(c1.getConta(), c2.getConta(), 100.0);

        assertFalse(resultado);
        assertEquals(30.0, c1.getConta().getSaldo());
        assertEquals(0.0, c2.getConta().getSaldo());
    }

    @Test
    void buscarClientePorCpf_cpfNaoCadastrado_retornaNull() {
        Cliente cliente = service.buscarClientePorCpf("99999999998");

        assertNull(cliente);
    }
}

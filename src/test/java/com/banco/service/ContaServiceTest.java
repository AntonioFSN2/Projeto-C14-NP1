package com.banco.service;

import com.banco.model.Cliente;
import com.banco.model.ContaBancaria;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContaServiceTest {

    private ContaService service;
    private ContaBancaria contaDaniele;
    private ContaBancaria contaAuxiliar;

    @BeforeEach
    void setUp() {
        service = new ContaService();

        Cliente c1 = service.cadastrarCliente("66666666666", "Daniele", "15/03/1995");
        Cliente c2 = service.cadastrarCliente("77777777777", "Auxiliar", "10/05/1990");

        contaDaniele = c1.getConta();
        contaAuxiliar = c2.getConta();
    }

    // Caso 6 — Transferência válida atualiza os dois saldos
    @Test
    void transferir_valorValido_atualizaAmbosOsSaldos() {
        service.depositar(contaDaniele, 200.0);

        boolean resultado = service.transferir(contaDaniele, contaAuxiliar, 50.0);

        assertTrue(resultado);
        assertEquals(150.0, contaDaniele.getSaldo());
        assertEquals(50.0, contaAuxiliar.getSaldo());
    }

    // Caso 7 — consultarSaldo retorna o valor exato após depósito
    @Test
    void consultarSaldo_aposDeposito_retornaValorCorreto() {
        service.depositar(contaDaniele, 350.0);

        double saldo = service.consultarSaldo(contaDaniele);

        assertEquals(350.0, saldo);
    }

    // Caso 8 — CPF com máscara é aceito e normalizado para só dígitos
    @Test
    void cadastrarCliente_cpfFormatado_normalizaECadastra() {
        Cliente cliente = service.cadastrarCliente("111.222.333-44", "Teste", "01/01/2000");

        assertNotNull(cliente);
        assertEquals("11122233344", cliente.getCpf());
    }

    // Caso 9 — Histórico registra todas as operações realizadas
    @Test
    void historico_aposMultiplasOperacoes_registraTodasTransacoes() {
        service.depositar(contaDaniele, 300.0);   // 1 transação em contaDaniele
        service.sacar(contaDaniele, 100.0);        // 2 transações em contaDaniele
        service.transferir(contaDaniele, contaAuxiliar, 50.0); // 3 em contaDaniele, 1 em contaAuxiliar

        assertEquals(3, contaDaniele.getHistorico().size());
        assertEquals(1, contaAuxiliar.getHistorico().size());
    }

    // Caso 10 — Dois clientes cadastrados recebem números de conta diferentes
    @Test
    void duasContas_numerosDistintos() {
        String numeroDaniele = contaDaniele.getNumero();
        String numeroAuxiliar = contaAuxiliar.getNumero();

        assertNotEquals(numeroDaniele, numeroAuxiliar);
    }
}
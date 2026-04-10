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

    // Caso 16 — Depósito com valor zero ou negativo deve ser rejeitado
    // Garante que apenas valores positivos alteram o saldo da conta
    @Test
    void depositar_valorZeroOuNegativo_retornaFalse() {
        Cliente cliente = service.cadastrarCliente("19191919191", "Matheus", "01/01/1990");
        ContaBancaria conta = cliente.getConta();

        boolean resultadoZero = service.depositar(conta, 0);       // valor zero não é aceito
        boolean resultadoNegativo = service.depositar(conta, -50.0); // valor negativo não é aceito

        assertFalse(resultadoZero);
        assertFalse(resultadoNegativo);
        assertEquals(0.0, conta.getSaldo()); // saldo permanece inalterado
    }

    // Caso 17 — Saque maior que o saldo disponível deve ser recusado
    // O saldo não deve ser alterado quando a operação falha
    @Test
    void sacar_saldoInsuficiente_retornaFalse() {
        Cliente cliente = service.cadastrarCliente("20202020202", "Matheus", "01/01/1990");
        ContaBancaria conta = cliente.getConta();
        service.depositar(conta, 50.0);

        boolean resultado = service.sacar(conta, 100.0); // tenta sacar mais do que há na conta

        assertFalse(resultado);
        assertEquals(50.0, conta.getSaldo()); // saldo permanece R$ 50,00
    }

    // Caso 18 — Saque com valor negativo deve ser recusado
    // Não deve ser possível "sacar" um valor negativo (equivaleria a um depósito)
    @Test
    void sacar_valorNegativo_retornaFalse() {
        Cliente cliente = service.cadastrarCliente("21212121212", "Matheus", "01/01/1990");
        ContaBancaria conta = cliente.getConta();

        boolean resultado = service.sacar(conta, -10.0);

        assertFalse(resultado);
    }

    // Caso 19 — Transferência bloqueada quando a conta de origem não tem saldo suficiente
    // Nenhum dos dois saldos deve ser alterado quando a transferência falha
    @Test
    void transferir_saldoInsuficienteNaOrigem_retornaFalse() {
        Cliente c1 = service.cadastrarCliente("23232323232", "Matheus", "01/01/1990");
        Cliente c2 = service.cadastrarCliente("24242424242", "Matheus2", "01/01/1990");
        service.depositar(c1.getConta(), 30.0);

        boolean resultado = service.transferir(c1.getConta(), c2.getConta(), 100.0); // tenta transferir mais do que tem

        assertFalse(resultado);
        assertEquals(30.0, c1.getConta().getSaldo()); // origem permanece com R$ 30,00
        assertEquals(0.0, c2.getConta().getSaldo());  // destino permanece com R$ 0,00
    }

    // Caso 20 — Busca por CPF não cadastrado deve retornar null
    // O sistema não deve lançar exceção, apenas indicar ausência com null
    @Test
    void buscarClientePorCpf_cpfNaoCadastrado_retornaNull() {
        Cliente cliente = service.buscarClientePorCpf("99999999998");

        assertNull(cliente);
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

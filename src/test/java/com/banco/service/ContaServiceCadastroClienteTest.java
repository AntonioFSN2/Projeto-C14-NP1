package com.banco.service;
import com.banco.model.Cliente;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ContaServiceCadastroClienteTest {

    private ContaService service;

    @BeforeEach
    void setUp() {
        service = new ContaService();
    }

    @Test
    void cadastrarCliente_dadosValidos_retornaCliente() {
        Cliente cliente = service.cadastrarCliente(
                "12345678901",
                "Teste",
                "10/05/2004"
        );

        assertNotNull(cliente);
        assertEquals("12345678901", cliente.getCpf());
        assertEquals("Teste", cliente.getNome());
        assertNotNull(cliente.getConta());
        assertNotNull(cliente.getConta().getNumero());
    }

    @Test
    void cadastrarCliente_cpfFormatado_normalizaECadastra() {
        Cliente cliente = service.cadastrarCliente(
                "111.222.333-44",
                "Teste",
                "15/08/2004"
        );

        assertNotNull(cliente);
        assertEquals("11122233344", cliente.getCpf());
        assertNotNull(cliente.getConta());
        assertNotNull(cliente.getConta().getNumero());
    }

    @Test
    void cadastrarCliente_cpfDuplicado_retornaNull() {
        Cliente primeiroCliente = service.cadastrarCliente(
                "22233344455",
                "Teste",
                "20/01/2003"
        );

        Cliente segundoCliente = service.cadastrarCliente(
                "22233344455",
                "Teste",
                "12/12/2000"
        );

        assertNotNull(primeiroCliente);
        assertNull(segundoCliente);
    }

    @Test
    void cadastrarCliente_nomeVazio_retornaNull() {
        Cliente clienteNomeVazio = service.cadastrarCliente(
                "33344455566",
                "",
                "01/01/2000"
        );

        Cliente clienteNomeEspacos = service.cadastrarCliente(
                "44455566677",
                "   ",
                "01/01/2000"
        );

        assertNull(clienteNomeVazio);
        assertNull(clienteNomeEspacos);
    }

    @Test
    void cadastrarCliente_cpfInvalido_retornaNull() {
        Cliente cliente = service.cadastrarCliente(
                "1234567",
                "Teste",
                "05/09/2004"
        );

        assertNull(cliente);
    }
}
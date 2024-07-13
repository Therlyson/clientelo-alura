import br.com.alura.clientelo.model.Pedido;
import br.com.alura.clientelo.service.RelatorioSintetico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

public class RelatorioSinteticoTest {
    private RelatorioSintetico relatorio;
    private Pedido pedidoMock;

    @BeforeEach
    void setUp() {
        relatorio = new RelatorioSintetico();
        pedidoMock = Mockito.mock(Pedido.class);
    }

    @Test
    void testContabilizarPedidos() {
        relatorio.contabilizarTodosPedidos();
        assertEquals(1, relatorio.getTotalPedidos());
    }

    @Test
    void testContabilizarMontanteDeVendas() {
        when(pedidoMock.getPreco()).thenReturn(new BigDecimal("100"));
        when(pedidoMock.getQuantidade()).thenReturn(2);

        relatorio.contabilizarMontanteDeVendas(pedidoMock);

        assertEquals(new BigDecimal("200"), relatorio.getMontanteDeVendas());
    }

    @Test
    void deveProcessarIdicadoresDePedidosQuandoTiverConteudoNoArquivo() throws IOException {
        var path = Paths.get("src/main/resources/pedidos.csv");
        List<String> lines = Files.readAllLines(path);

        int expectedTotalPedidos = 16;
        int expectedTotalProdutosVendidos = 35;
        int expectedTotalCategorias = 5;
        BigDecimal expectedMontanteDeVendas = new BigDecimal("178374.49");
        BigDecimal expectedPedidoMaisBarato = new BigDecimal("95.17");
        BigDecimal expectedPedidoMaisCaro = new BigDecimal("55056.00");

        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            Pedido pedido = new Pedido();
            pedido.setCategoria(parts[0]);
            pedido.setProduto(parts[1]);
            pedido.setPreco(new BigDecimal(parts[2]));
            pedido.setQuantidade(Integer.parseInt(parts[3]));
            pedido.setData(LocalDate.parse(parts[4], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            pedido.setCliente(parts[5]);

            relatorio.contabilizarTodosPedidos();
            relatorio.contabilizarProdutosVendidos(pedido);
            relatorio.contabilizarTotalCategorias(pedido);
            relatorio.contabilizarMontanteDeVendas(pedido);
            relatorio.calcularPedidoMaisBarato(pedido);
            relatorio.calcularPedidoMaisCaro(pedido);
        }

        assertEquals(expectedTotalPedidos, relatorio.getTotalPedidos());
        assertEquals(expectedTotalProdutosVendidos, relatorio.getTotalProdutosVendidos());
        assertEquals(expectedTotalCategorias, relatorio.getTotalCategorias());
        assertEquals(expectedMontanteDeVendas, relatorio.getMontanteDeVendas());
        assertEquals(expectedPedidoMaisBarato, relatorio.getPedidoMaisBarato().getPreco());
        assertEquals(expectedPedidoMaisCaro, relatorio.getPedidoMaisCaro().getPreco().multiply(new BigDecimal(relatorio.getPedidoMaisCaro().getQuantidade())));
    }

    @Test
    void relatorioDeveEstarZeradoQuandoNaoTiverPedidos() throws IOException {
        var path = Paths.get("src/main/resources/pedidos-vazio.csv");
        List<String> lines = Files.readAllLines(path);

        int expectedTotalPedidos = 0;
        int expectedTotalProdutosVendidos = 0;
        int expectedTotalCategorias = 0;
        BigDecimal expectedMontanteDeVendas = BigDecimal.ZERO;

        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",");
            Pedido pedido = new Pedido();
            pedido.setCategoria(parts[0]);
            pedido.setProduto(parts[1]);
            pedido.setPreco(new BigDecimal(parts[2]));
            pedido.setQuantidade(Integer.parseInt(parts[3]));
            pedido.setData(LocalDate.parse(parts[4], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            pedido.setCliente(parts[5]);

            relatorio.contabilizarTodosPedidos();
            relatorio.contabilizarProdutosVendidos(pedido);
            relatorio.contabilizarTotalCategorias(pedido);
            relatorio.contabilizarMontanteDeVendas(pedido);
            relatorio.calcularPedidoMaisBarato(pedido);
            relatorio.calcularPedidoMaisCaro(pedido);
        }

        assertEquals(expectedTotalPedidos, relatorio.getTotalPedidos());
        assertEquals(expectedTotalProdutosVendidos, relatorio.getTotalProdutosVendidos());
        assertEquals(expectedTotalCategorias, relatorio.getTotalCategorias());
        assertEquals(expectedMontanteDeVendas, relatorio.getMontanteDeVendas());
        assertNull(relatorio.getPedidoMaisBarato());
        assertNull(relatorio.getPedidoMaisCaro());
    }
}

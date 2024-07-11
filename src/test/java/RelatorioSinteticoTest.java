import br.com.alura.clientelo.Pedido;
import br.com.alura.clientelo.RelatorioSintetico;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        relatorio.contabilizarPedidos();
        assertEquals(1, relatorio.getTotalPedidos());
    }

    @Test
    void testContabilizarMontanteDeVendas() {
        when(pedidoMock.getPreco()).thenReturn(new BigDecimal("100"));
        when(pedidoMock.getQuantidade()).thenReturn(2);

        relatorio.contabilizarMontanteDeVendas(pedidoMock);

        assertEquals(new BigDecimal("200"), relatorio.getMontanteDeVendas());
    }
}

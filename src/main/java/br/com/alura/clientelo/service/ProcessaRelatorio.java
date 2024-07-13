package br.com.alura.clientelo.service;

import br.com.alura.clientelo.model.Pedido;

import java.util.List;

public class ProcessaRelatorio {
    private RelatorioSintetico relatorioSintetico;

    public ProcessaRelatorio(RelatorioSintetico relatorioSintetico) {
        this.relatorioSintetico = relatorioSintetico;
    }

    public RelatorioSintetico processarPedidos(List<Pedido> pedidos){
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedidoAtual = pedidos.get(i);

            relatorioSintetico.calcularPedidoMaisBarato(pedidoAtual);
            relatorioSintetico.calcularPedidoMaisCaro(pedidoAtual);
            relatorioSintetico.contabilizarMontanteDeVendas(pedidoAtual);
            relatorioSintetico.contabilizarProdutosVendidos(pedidoAtual);
            relatorioSintetico.contabilizarTodosPedidos();
            relatorioSintetico.contabilizarTotalCategorias(pedidoAtual);
        }
        return relatorioSintetico;
    }
}

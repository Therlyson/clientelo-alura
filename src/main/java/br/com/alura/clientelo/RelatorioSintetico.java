package br.com.alura.clientelo;

import java.math.BigDecimal;

public class RelatorioSintetico {
    private int totalPedidos = 0;
    private int totalProdutosVendidos = 0;
    private int totalCategorias = 0;
    private BigDecimal montanteDeVendas = BigDecimal.ZERO;
    private Pedido pedidoMaisBarato;
    private Pedido pedidoMaisCaro;

    public void contabilizarPedidos(){
        totalPedidos++;
    }

    public int getTotalPedidos() {
        return totalPedidos;
    }

    public void contabilizarProdutosVendidos(Pedido pedido){
        totalProdutosVendidos += pedido.getQuantidade();
    }

    public int getTotalProdutosVendidos() {
        return totalProdutosVendidos;
    }

    public void contabilizarCategorias(){
        totalCategorias++;
    }

    public int getTotalCategorias() {
        return totalCategorias;
    }

    public void contabilizarMontanteDeVendas(Pedido pedido){
        BigDecimal preco = pedido.getPreco();
        BigDecimal quantidade = new BigDecimal(pedido.getQuantidade());
        montanteDeVendas = montanteDeVendas.add(preco.multiply(quantidade));
    }

    public BigDecimal getMontanteDeVendas() {
        return montanteDeVendas;
    }

    public void calcularPedidoMaisBarato(Pedido pedido){
        BigDecimal precoTotalPedido = pedido.getPreco().multiply(new BigDecimal(pedido.getQuantidade()));

        if (pedidoMaisBarato == null || precoTotalPedido.compareTo(pedidoMaisBarato.getPreco()
                .multiply(new BigDecimal(pedidoMaisBarato.getQuantidade()))) < 0) {
            pedidoMaisBarato = pedido;
        }
    }

    public Pedido getPedidoMaisBarato() {
        return pedidoMaisBarato;
    }

    public void calcularPedidoMaisCaro(Pedido pedido){
        BigDecimal precoTotalPedido = pedido.getPreco().multiply(new BigDecimal(pedido.getQuantidade()));

        if (pedidoMaisCaro == null || precoTotalPedido.compareTo(pedidoMaisCaro.getPreco()
                .multiply(new BigDecimal(pedidoMaisCaro.getQuantidade()))) > 0) {
            pedidoMaisCaro = pedido;
        }
    }

    public Pedido getPedidoMaisCaro() {
        return pedidoMaisCaro;
    }
}

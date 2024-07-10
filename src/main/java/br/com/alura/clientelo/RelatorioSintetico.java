package br.com.alura.clientelo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class RelatorioSintetico {
    private int totalPedidos = 0;
    private int totalProdutosVendidos = 0;
    private int totalCategorias = 0;
    private BigDecimal montanteDeVendas = BigDecimal.ZERO;
    private Pedido pedidoMaisBarato = null;

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
}

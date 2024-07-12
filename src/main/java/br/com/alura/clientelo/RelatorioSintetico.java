package br.com.alura.clientelo;

import java.math.BigDecimal;
import java.util.Arrays;

public class RelatorioSintetico {
    private int totalPedidos = 0;
    private int totalProdutosVendidos = 0;
    private int totalCategorias = 0;
    private String[] categoriasProcessadas = new String[10];
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

    public void contabilizarCategorias(Pedido pedido){
        boolean jahProcessouCategoria = false;

        for (int j = 0; j < categoriasProcessadas.length; j++) {
            if (pedido.getCategoria().equalsIgnoreCase(categoriasProcessadas[j])) {
                jahProcessouCategoria = true;
            }
        }

        if (!jahProcessouCategoria) {
            totalCategorias++;

            if (categoriasProcessadas[categoriasProcessadas.length - 1] != null) {
                categoriasProcessadas = Arrays.copyOf(categoriasProcessadas, categoriasProcessadas.length * 2);
            } else {
                for (int k = 0; k < categoriasProcessadas.length; k++) {
                    if (categoriasProcessadas[k] == null) {
                        categoriasProcessadas[k] = pedido.getCategoria();
                        break;
                    }
                }
            }
        }
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

    public BigDecimal valorTotalPedidoMaisBarato() {
        return pedidoMaisBarato.getPreco().multiply(new BigDecimal(pedidoMaisBarato.getQuantidade()));
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

    public BigDecimal valorTotalPedidoMaisCaro() {
        return pedidoMaisCaro.getPreco().multiply(new BigDecimal(pedidoMaisCaro.getQuantidade()));
    }

    public void processarPedidos(Pedido[] pedidos){
        for (int i = 0; i < pedidos.length; i++) {
            Pedido pedidoAtual = pedidos[i];
            if (pedidoAtual == null) {
                break;
            }

            calcularPedidoMaisBarato(pedidoAtual);
            calcularPedidoMaisCaro(pedidoAtual);
            contabilizarMontanteDeVendas(pedidoAtual);
            contabilizarProdutosVendidos(pedidoAtual);
            contabilizarPedidos();
            contabilizarCategorias(pedidoAtual);
        }
    }
}

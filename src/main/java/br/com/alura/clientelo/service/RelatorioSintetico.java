package br.com.alura.clientelo.service;

import br.com.alura.clientelo.model.Pedido;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

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
        montanteDeVendas = montanteDeVendas.add(pedido.getValorTotal());
    }

    public BigDecimal getMontanteDeVendas() {
        return montanteDeVendas;
    }

    public void calcularPedidoMaisBarato(Pedido pedido){
        if (pedidoMaisBarato == null || pedido.isMaisBaratoQue(pedidoMaisBarato)) {
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
        if (pedidoMaisCaro == null || pedido.isMaisCaroQue(pedidoMaisCaro)) {
            pedidoMaisCaro = pedido;
        }
    }

    public Pedido getPedidoMaisCaro() {
        return pedidoMaisCaro;
    }

    public BigDecimal valorTotalPedidoMaisCaro() {
        return pedidoMaisCaro.getPreco().multiply(new BigDecimal(pedidoMaisCaro.getQuantidade()));
    }

    public void processarPedidos(List<Pedido> pedidos){
        for (int i = 0; i < pedidos.size(); i++) {
            Pedido pedidoAtual = pedidos.get(i);

            calcularPedidoMaisBarato(pedidoAtual);
            calcularPedidoMaisCaro(pedidoAtual);
            contabilizarMontanteDeVendas(pedidoAtual);
            contabilizarProdutosVendidos(pedidoAtual);
            contabilizarPedidos();
            contabilizarCategorias(pedidoAtual);
        }
    }
}

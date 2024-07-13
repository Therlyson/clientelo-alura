package br.com.alura.clientelo.service;

import br.com.alura.clientelo.model.Pedido;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class RelatorioSintetico {
    private int totalPedidos = 0;
    private int totalProdutosVendidos = 0;
    private int totalCategorias = 0;
    private List<String> categoriasProcessadas = new ArrayList<>();
    private BigDecimal montanteDeVendas = BigDecimal.ZERO;
    private Pedido pedidoMaisBarato;
    private Pedido pedidoMaisCaro;

    public void contabilizarTodosPedidos(){
        totalPedidos++;
    }

    public void contabilizarProdutosVendidos(Pedido pedido){
        totalProdutosVendidos += pedido.getQuantidade();
    }

    public void contabilizarTotalCategorias(Pedido pedido){
        boolean categoriaJaProcessada = false;

        for (String categoria : categoriasProcessadas) {
            if (categoria.equalsIgnoreCase(pedido.getCategoria())) {
                categoriaJaProcessada = true;
                break;
            }
        }

        if (!categoriaJaProcessada) {
            totalCategorias++;
            categoriasProcessadas.add(pedido.getCategoria());
        }
    }

    public void contabilizarMontanteDeVendas(Pedido pedido){
        montanteDeVendas = montanteDeVendas.add(pedido.getValorTotal());
    }

    public void calcularPedidoMaisBarato(Pedido pedido){
        if (pedidoMaisBarato == null || pedido.isMaisBaratoQue(pedidoMaisBarato)) {
            pedidoMaisBarato = pedido;
        }
    }

    public void calcularPedidoMaisCaro(Pedido pedido){
        if (pedidoMaisCaro == null || pedido.isMaisCaroQue(pedidoMaisCaro)) {
            pedidoMaisCaro = pedido;
        }
    }

    public int getTotalPedidos() {
        return totalPedidos;
    }

    public int getTotalProdutosVendidos() {
        return totalProdutosVendidos;
    }

    public int getTotalCategorias() {
        return totalCategorias;
    }

    public BigDecimal getMontanteDeVendas() {
        return montanteDeVendas;
    }

    public Pedido getPedidoMaisBarato() {
        return pedidoMaisBarato;
    }

    public Pedido getPedidoMaisCaro() {
        return pedidoMaisCaro;
    }
}

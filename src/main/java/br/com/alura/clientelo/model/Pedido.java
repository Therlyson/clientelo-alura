package br.com.alura.clientelo.model;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Pedido {

    private String categoria;
    private String produto;
    private String cliente;
    private BigDecimal preco;
    private int quantidade;

    private LocalDate data;

    public Pedido(String categoria, String produto, String cliente, BigDecimal preco, int quantidade, LocalDate data) {
        this.categoria = categoria;
        this.produto = produto;
        this.cliente = cliente;
        this.preco = preco;
        this.quantidade = quantidade;
        this.data = data;
    }

    public Pedido() {
    }

    public String getCategoria() {
        return categoria;
    }

    public String getProduto() {
        return produto;
    }

    public String getCliente() {
        return cliente;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public LocalDate getData() {
        return data;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setProduto(String produto) {
        this.produto = produto;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public boolean isMaisBaratoQue(Pedido outroPedido) {
        if(this.preco.multiply(new BigDecimal(this.quantidade)).
                compareTo(outroPedido.preco.multiply(new BigDecimal(outroPedido.quantidade))) < 0) {
            return true;
        }
        return false;
    }

    public boolean isMaisCaroQue(Pedido outroPedido) {
        if(this.preco.multiply(new BigDecimal(this.quantidade)).
                compareTo(outroPedido.preco.multiply(new BigDecimal(outroPedido.quantidade))) > 0) {
            return true;
        }
        return false;
    }

    public BigDecimal getValorTotal() {
        return preco.multiply(new BigDecimal(quantidade));
    }

    @Override
    public String toString() {
        return "Pedido{" +
                "categoria='" + categoria + '\'' +
                ", produto='" + produto + '\'' +
                ", cliente='" + cliente + '\'' +
                ", preco=" + preco +
                ", quantidade=" + quantidade +
                ", data=" + data +
                '}';
    }

}

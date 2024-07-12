package br.com.alura.clientelo.utils;

import br.com.alura.clientelo.model.Pedido;

import java.util.List;

public interface ProcessadorDeArquivos {
    List<Pedido> processaArquivo(String nomeDoArquivo);
}

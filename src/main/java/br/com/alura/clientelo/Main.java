package br.com.alura.clientelo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args){
        Pedido[] pedidos = ProcessadorDeCsv.processaArquivo("pedidos.csv");
        RelatorioSintetico relatorio = new RelatorioSintetico();

        relatorio.processarPedidos(pedidos);

        logger.info("##### RELATÓRIO DE VALORES TOTAIS #####");
        logger.info("TOTAL DE PEDIDOS REALIZADOS: {}", relatorio.getTotalPedidos());
        logger.info("TOTAL DE PRODUTOS VENDIDOS: {}", relatorio.getTotalProdutosVendidos());
        logger.info("TOTAL DE CATEGORIAS: {}", relatorio.getTotalCategorias());
        logger.info("MONTANTE DE VENDAS: {}", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).
                format(relatorio.getMontanteDeVendas().setScale(2, RoundingMode.HALF_DOWN)));
        logger.info("PEDIDO MAIS BARATO: {} ({})", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).
                format( relatorio.valorTotalPedidoMaisBarato().setScale(2, RoundingMode.HALF_DOWN)), relatorio.getPedidoMaisBarato().getProduto());
        logger.info("PEDIDO MAIS CARO: {} ({})\n", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(relatorio.valorTotalPedidoMaisCaro()), relatorio.getPedidoMaisCaro().getProduto());
        logger.info("### FIM DO RELATÓRIO ###");
    }
}

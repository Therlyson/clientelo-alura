package br.com.alura.clientelo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws IOException, URISyntaxException {
        Pedido[] pedidos = ProcessadorDeCsv.processaArquivo("pedidos.csv");
        RelatorioSintetico relatorio = new RelatorioSintetico();

        Pedido pedidoMaisCaro = null;

        String[] categoriasProcessadas = new String[10];

        for (int i = 0; i < pedidos.length; i++) {
            Pedido pedidoAtual = pedidos[i];

            if (pedidoAtual == null) {
                break;
            }

            relatorio.calcularPedidoMaisBarato(pedidoAtual);

            if (pedidoMaisCaro == null || pedidoAtual.getPreco().multiply(new BigDecimal(pedidoAtual.getQuantidade())).compareTo(pedidoMaisCaro.getPreco().multiply(new BigDecimal(pedidoMaisCaro.getQuantidade()))) > 0) {
                pedidoMaisCaro = pedidoAtual;
            }

            relatorio.contabilizarMontanteDeVendas(pedidoAtual);
            relatorio.contabilizarProdutosVendidos(pedidoAtual);
            relatorio.contabilizarPedidos();

            boolean jahProcessouCategoria = false;
            for (int j = 0; j < categoriasProcessadas.length; j++) {
                if (pedidoAtual.getCategoria().equalsIgnoreCase(categoriasProcessadas[j])) {
                    jahProcessouCategoria = true;
                }
            }

            if (!jahProcessouCategoria) {
                relatorio.contabilizarCategorias();

                if (categoriasProcessadas[categoriasProcessadas.length - 1] != null) {
                    categoriasProcessadas = Arrays.copyOf(categoriasProcessadas, categoriasProcessadas.length * 2);
                } else {
                    for (int k = 0; k < categoriasProcessadas.length; k++) {
                        if (categoriasProcessadas[k] == null) {
                            categoriasProcessadas[k] = pedidoAtual.getCategoria();
                            break;
                        }
                    }
                }
            }
        }

        logger.info("##### RELATÓRIO DE VALORES TOTAIS #####");
        logger.info("TOTAL DE PEDIDOS REALIZADOS: {}", relatorio.getTotalPedidos());
        logger.info("TOTAL DE PRODUTOS VENDIDOS: {}", relatorio.getTotalProdutosVendidos());
        logger.info("TOTAL DE CATEGORIAS: {}", relatorio.getTotalCategorias());
        logger.info("MONTANTE DE VENDAS: {}", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).
                format(relatorio.getMontanteDeVendas().setScale(2, RoundingMode.HALF_DOWN)));
        logger.info("PEDIDO MAIS BARATO: {} ({})", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).
                format(relatorio.getPedidoMaisBarato().getPreco().multiply(new BigDecimal(relatorio.getPedidoMaisBarato().getQuantidade())).
                        setScale(2, RoundingMode.HALF_DOWN)), relatorio.getPedidoMaisBarato().getProduto());
        logger.info("PEDIDO MAIS CARO: {} ({})\n", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(pedidoMaisCaro.getPreco().multiply(new BigDecimal(pedidoMaisCaro.getQuantidade())).setScale(2, RoundingMode.HALF_DOWN)), pedidoMaisCaro.getProduto());
        logger.info("### FIM DO RELATÓRIO ###");
    }
}

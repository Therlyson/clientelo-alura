package br.com.alura.clientelo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args){
        Pedido[] pedidos = ProcessadorDeCsv.processaArquivo("pedidos.csv");
        RelatorioSintetico relatorio = new RelatorioSintetico();

        String[] categoriasProcessadas = new String[10];

        for (int i = 0; i < pedidos.length; i++) {
            Pedido pedidoAtual = pedidos[i];

            if (pedidoAtual == null) {
                break;
            }

            relatorio.calcularPedidoMaisBarato(pedidoAtual);

            relatorio.calcularPedidoMaisCaro(pedidoAtual);

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
        logger.info("PEDIDO MAIS CARO: {} ({})\n", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).format(relatorio.getPedidoMaisCaro().getPreco().multiply(new BigDecimal(relatorio.getPedidoMaisCaro().getQuantidade())).setScale(2, RoundingMode.HALF_DOWN)), relatorio.getPedidoMaisCaro().getProduto());
        logger.info("### FIM DO RELATÓRIO ###");
    }
}

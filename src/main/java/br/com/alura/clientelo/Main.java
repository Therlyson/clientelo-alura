package br.com.alura.clientelo;

import br.com.alura.clientelo.model.Pedido;
import br.com.alura.clientelo.service.ProcessaRelatorio;
import br.com.alura.clientelo.service.RelatorioSintetico;
import br.com.alura.clientelo.utils.ProcessadorDeArquivos;
import br.com.alura.clientelo.utils.ProcessadorDeCsv;
import br.com.alura.clientelo.utils.ProcessadorDeJson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter file type (json or csv):");
        String fileType = scanner.nextLine();

        System.out.println("Enter file name:");
        String fileName = scanner.nextLine();

        ProcessadorDeArquivos processadorDeArquivos = null;
        switch (fileType.toLowerCase()) {
            case "json" -> processadorDeArquivos = new ProcessadorDeJson();
            case "csv" -> processadorDeArquivos = new ProcessadorDeCsv();
            default -> System.out.println("Unsupported file type. Please use 'json' or 'csv'.");
        }

        List<Pedido> pedidos = processadorDeArquivos.processarArquivo(fileName);
        ProcessaRelatorio processaRelatorio = new ProcessaRelatorio(new RelatorioSintetico());
        RelatorioSintetico relatorio = processaRelatorio.processarPedidos(pedidos);

        gerarTextoFormatado(relatorio);
    }

    private static void gerarTextoFormatado(RelatorioSintetico relatorio){
        Logger logger = LoggerFactory.getLogger(Main.class);

        logger.info("##### RELATÓRIO DE VALORES TOTAIS #####");
        logger.info("TOTAL DE PEDIDOS REALIZADOS: {}", relatorio.getTotalPedidos());
        logger.info("TOTAL DE PRODUTOS VENDIDOS: {}", relatorio.getTotalProdutosVendidos());
        logger.info("TOTAL DE CATEGORIAS: {}", relatorio.getTotalCategorias());
        logger.info("MONTANTE DE VENDAS: {}", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).
                format(relatorio.getMontanteDeVendas().setScale(2, RoundingMode.HALF_DOWN)));
        logger.info("PEDIDO MAIS BARATO: {} ({})", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).
                format(relatorio.getPedidoMaisBarato().getValorTotal().setScale(2, RoundingMode.HALF_DOWN)), relatorio.getPedidoMaisBarato().getProduto());
        logger.info("PEDIDO MAIS CARO: {} ({})\n", NumberFormat.getCurrencyInstance(new Locale("pt", "BR")).
                format(relatorio.getPedidoMaisCaro().getValorTotal()), relatorio.getPedidoMaisCaro().getProduto());
        logger.info("### FIM DO RELATÓRIO ###");
    }
}

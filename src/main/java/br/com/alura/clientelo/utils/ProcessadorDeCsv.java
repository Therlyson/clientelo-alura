package br.com.alura.clientelo.utils;

import br.com.alura.clientelo.model.Pedido;
import com.opencsv.CSVReader;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ProcessadorDeCsv implements ProcessadorDeArquivos{

    @Override
    public List<Pedido> processaArquivo(String nomeDoArquivo) {
        try {
            Path caminhoDoArquivo = procurarCaminho(nomeDoArquivo);

            List<String[]> linhas = readLineByLine(caminhoDoArquivo);

            List<Pedido> pedidos = new ArrayList<>();

            for(String[] registro : linhas) {
                String categoria = registro[0];
                String produto = registro[1];
                BigDecimal preco = new BigDecimal(registro[2]); // This line throws the exception
                int quantidade = Integer.parseInt(registro[3]);
                LocalDate data = LocalDate.parse(registro[4], DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                String cliente = registro[5];

                Pedido pedido = new Pedido(categoria, produto, cliente, preco, quantidade, data);
                pedidos.add(pedido);
            }

            return pedidos;
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Arquivo %s não localizado!", nomeDoArquivo));
        } catch (IOException e) {
            throw new RuntimeException("Erro ao abrir Scanner para processar arquivo!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Path procurarCaminho(String nomeDoArquivo) throws URISyntaxException {
        URL recursoCSV = ClassLoader.getSystemResource(nomeDoArquivo);
        return Path.of(recursoCSV.toURI());
    }

    private List<String[]> readLineByLine(Path filePath) throws Exception {
        List<String[]> list = new ArrayList<>();
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                String[] line;
                boolean isFirstLine = true; // Adiciona uma flag para verificar se é a primeira linha(cabeçalho)
                while ((line = csvReader.readNext()) != null) {
                    if (isFirstLine) {
                        isFirstLine = false;
                        continue;
                    }
                    list.add(line);
                }
            }
        }
        return list;
    }

}

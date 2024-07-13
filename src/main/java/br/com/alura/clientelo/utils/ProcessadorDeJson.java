package br.com.alura.clientelo.utils;

import br.com.alura.clientelo.model.Pedido;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;

public class ProcessadorDeJson implements ProcessadorDeArquivos{
    private ObjectMapper objectMapper;

    public ProcessadorDeJson() {
        objectMapper = new ObjectMapper();
    }

    @Override
    public List<Pedido> processarArquivo(String nomeDoArquivo) {
        objectMapper.registerModule(new JavaTimeModule()); // Registra o módulo para suporte a LocalDate

        try{
            Path caminhoDoArquivo = procurarCaminhoDoArquivo(nomeDoArquivo);
            return objectMapper.readValue(new File(String.valueOf(caminhoDoArquivo)), new TypeReference<List<Pedido>>(){});
        } catch (URISyntaxException e) {
            throw new RuntimeException(String.format("Arquivo %s não localizado!", nomeDoArquivo));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Path procurarCaminhoDoArquivo(String nomeDoArquivo) throws URISyntaxException {
        URL recursoCSV = ClassLoader.getSystemResource(nomeDoArquivo);
        return Path.of(recursoCSV.toURI());
    }
}

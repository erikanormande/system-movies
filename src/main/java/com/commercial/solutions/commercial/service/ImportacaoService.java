package com.commercial.solutions.commercial.service;

import com.commercial.solutions.commercial.model.Filme;
import com.commercial.solutions.commercial.repository.FilmeRepository;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ImportacaoService {

    @Autowired
    private FilmeRepository filmeRepository;

    public void importarCsv() {
        try {
            // Limpa a tabela
            filmeRepository.deleteAll();

            InputStream is = getClass().getResourceAsStream("/data/listagem-de-filmes-brasileiros-e-estrangeiros-exibidos-2009-a-2023.csv");

            if (Objects.isNull(is)) {
                throw new RuntimeException("Arquivo CSV não encontrado.");
            }

            Reader reader = new InputStreamReader(is);
            CSVReader csvReader = new CSVReaderBuilder(reader)
                    .withCSVParser(new CSVParserBuilder().withSeparator(';').build())
                    .build();


            String[] linha;
            int linhaAtual = 0;

            List<Filme> filmes = new ArrayList<>();

            while ((linha = csvReader.readNext()) != null) {
                linhaAtual++;

                if (linhaAtual <= 2) continue;
                if (linha.length == 0 || linha[0].isBlank()) continue;
                if (!isNumeric(linha[0])) continue;

                if (linha.length >= 11) {
                    Filme filme = new Filme();

                    filme.setAnoExibicao(parseIntSafe(linha[0]));
                    filme.setTitulo(linha[1].trim());
                    filme.setGenero(linha[3].trim());
                    filme.setPaisProdutores(linha[4].trim());
                    filme.setNacionalidade(linha[5].trim());
                    filme.setDistribuidora(linha[7].trim());
                    filme.setOrigemDistribuidora(linha[8].trim());
                    filme.setPublicoExibicao(parseLongSafe(linha[9]));
                    filme.setRendaExibicao(parseDoubleSafe(linha[10]));

                    filmes.add(filme);
                }
            }

            filmeRepository.saveAll(filmes);


            csvReader.close();
        } catch (Exception e) {
            throw new RuntimeException("Erro ao importar CSV: " + e.getMessage(), e);
        }
    }

    private Integer parseIntSafe(String value) {
        try {
            return value == null || value.equalsIgnoreCase("ND") ? 0 : Integer.parseInt(value.trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private Long parseLongSafe(String value) {
        try {
            String sanitized = value.replace(".", "").replace(",", "").trim();
            return sanitized.isEmpty() || sanitized.equalsIgnoreCase("ND") ? 0L : Long.parseLong(sanitized);
        } catch (Exception e) {
            return 0L;
        }
    }

    private Double parseDoubleSafe(String value) {
        try {
            String sanitized = value.replace(".", "").replace(",", ".").trim();
            return sanitized.isEmpty() || sanitized.equalsIgnoreCase("ND") ? 0.0 : Double.parseDouble(sanitized);
        } catch (Exception e) {
            return 0.0;
        }
    }

    private boolean isNumeric(String str) {
        if (str == null) return false;
        return str.trim().matches("\\d+");
    }
}


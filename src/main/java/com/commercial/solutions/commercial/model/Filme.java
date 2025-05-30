package com.commercial.solutions.commercial.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
public class Filme {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Ano de exibição é obrigatório")
    private Integer anoExibicao;

    @NotBlank(message = "Título é obrigatório")
    private String titulo;

    private String cpbRoe;

    @NotBlank(message = "Gênero é obrigatório")
    private String genero;

    private String paisProdutores;

    @NotBlank(message = "Nacionalidade é obrigatória")
    private String nacionalidade;

    private LocalDate dataLancamento;

    private String distribuidora;

    private String origemDistribuidora;

    @Column(name = "publico_exibicao")
    private Long publicoExibicao;

    private double rendaExibicao;


}
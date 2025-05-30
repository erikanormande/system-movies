package com.commercial.solutions.commercial.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username is required\n")
    private String username;

    @NotBlank(message = "Password is mandatory\n")
    private String senha;

    // Getters and Setters
}
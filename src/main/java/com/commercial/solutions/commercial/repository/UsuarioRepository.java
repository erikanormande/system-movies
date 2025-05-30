package com.commercial.solutions.commercial.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import com.commercial.solutions.commercial.model.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsernameAndSenha(String username, String senha);
}


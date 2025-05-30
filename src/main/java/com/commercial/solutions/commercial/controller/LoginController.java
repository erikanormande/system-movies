package com.commercial.solutions.commercial.controller;

import com.commercial.solutions.commercial.model.Usuario;
import com.commercial.solutions.commercial.repository.UsuarioRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class LoginController {

    @Autowired
    private UsuarioRepository usuarioRepo;

    @GetMapping("/login")
    public String loginForm() {
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String senha,
                        HttpSession session,
                        Model model) {
        Optional<Usuario> usuario = usuarioRepo.findByUsernameAndSenha(username, senha);
        if (usuario.isPresent()) {
            session.setAttribute("usuario", usuario.get());
            return "redirect:/filmes";
        }
        model.addAttribute("erro", "Invalid username or password\n");
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
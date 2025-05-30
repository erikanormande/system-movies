package com.commercial.solutions.commercial.controller;

import com.commercial.solutions.commercial.model.Filme;
import com.commercial.solutions.commercial.repository.FilmeRepository;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/filmes")
public class FilmeController {

    @Autowired
    private FilmeRepository filmeRepo;

    @GetMapping
    public String listar(Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("filmes", filmeRepo.findAll());
        return "filmes/lista";
    }

    @GetMapping("/novo")
    public String novo(Filme filme, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        return "filmes/form";
    }

    @PostMapping
    public String salvar(@Valid Filme filme, BindingResult result, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        if (result.hasErrors()) {
            return "filmes/form";
        }
        filmeRepo.save(filme);
        return "redirect:/filmes";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        model.addAttribute("filme", filmeRepo.findById(id).orElseThrow());
        return "filmes/form";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id, HttpSession session) {
        if (session.getAttribute("usuario") == null) return "redirect:/login";
        filmeRepo.deleteById(id);
        return "redirect:/filmes";
    }
}
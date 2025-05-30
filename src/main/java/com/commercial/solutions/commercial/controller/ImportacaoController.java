package com.commercial.solutions.commercial.controller;

import com.commercial.solutions.commercial.service.ImportacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ImportacaoController {

    @Autowired
    private ImportacaoService importacaoService;

    @PostMapping("/importar-csv")
    public ResponseEntity<String> importarCsv() {
        importacaoService.importarCsv();
        return ResponseEntity.ok("Import successful");
    }
}

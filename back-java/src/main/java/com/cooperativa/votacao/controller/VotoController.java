package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.VotoRequestDTO;
import com.cooperativa.votacao.service.VotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pautas/{pautaId}/votos")
@RequiredArgsConstructor
public class VotoController {

    private final VotoService votoService;

    @PostMapping
    public ResponseEntity<String> registrarVoto(@PathVariable Long pautaId, @RequestBody @Valid VotoRequestDTO dto) {
        votoService.registrarVoto(pautaId, dto);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Voto recebido e está sendo processado.");
    }
}

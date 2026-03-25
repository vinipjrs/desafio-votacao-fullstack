package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.SessaoRequestDTO;
import com.cooperativa.votacao.dto.SessaoResponseDTO;
import com.cooperativa.votacao.service.SessaoVotacaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/pautas/{pautaId}/sessoes")
@RequiredArgsConstructor
public class SessaoVotacaoController {

    private final SessaoVotacaoService sessaoService;

    @PostMapping
    public ResponseEntity<SessaoResponseDTO> abrirSessao(@PathVariable Long pautaId, @RequestBody(required = false) @Valid SessaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoService.abrirSessao(pautaId, dto));
    }
}

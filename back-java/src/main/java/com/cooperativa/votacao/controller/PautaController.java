package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.PautaRequestDTO;
import com.cooperativa.votacao.dto.PautaResponseDTO;
import com.cooperativa.votacao.dto.ResultadoVotacaoDTO;
import com.cooperativa.votacao.service.PautaService;
import com.cooperativa.votacao.service.VotoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/pautas") // Versão 1 da nossa API de votação
@RequiredArgsConstructor
public class PautaController {

    private final PautaService pautaService;
    private final VotoService votoService;

    @PostMapping
    public ResponseEntity<PautaResponseDTO> criarPauta(@RequestBody @Valid PautaRequestDTO dto) {
        // Cria uma pauta nova. A sessão vai ser aberta depois pelo front no primeiro voto.
        return ResponseEntity.status(HttpStatus.CREATED).body(pautaService.criarPauta(dto));
    }

    @GetMapping
    public ResponseEntity<List<PautaResponseDTO>> listarPautas() {
        return ResponseEntity.ok(pautaService.listarPautas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PautaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(pautaService.buscarPorId(id));
    }

    @GetMapping("/{id}/resultado")
    public ResponseEntity<ResultadoVotacaoDTO> obterResultado(@PathVariable Long id) {
        // Pega o placar atual (SIM/NÃO) da pauta informada
        return ResponseEntity.ok(votoService.obterResultado(id));
    }
}

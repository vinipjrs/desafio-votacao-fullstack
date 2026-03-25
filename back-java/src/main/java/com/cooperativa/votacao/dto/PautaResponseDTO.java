package com.cooperativa.votacao.dto;

import java.time.LocalDateTime;

public record PautaResponseDTO(
    Long id,
    String titulo,
    String descricao,
    LocalDateTime criadoEm,
    boolean sessaoAtiva,
    LocalDateTime dataFechamentoSessao
) {}

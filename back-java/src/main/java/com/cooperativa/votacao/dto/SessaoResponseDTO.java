package com.cooperativa.votacao.dto;

import java.time.LocalDateTime;

public record SessaoResponseDTO(
    Long id,
    Long pautaId,
    LocalDateTime dataAbertura,
    LocalDateTime dataFechamento,
    boolean aberta
) {}

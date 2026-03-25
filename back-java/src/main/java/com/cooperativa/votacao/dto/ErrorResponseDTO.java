package com.cooperativa.votacao.dto;

import java.time.LocalDateTime;

public record ErrorResponseDTO(
    String mensagem,
    int status,
    LocalDateTime timestamp
) {
    public ErrorResponseDTO(String mensagem, int status) {
        this(mensagem, status, LocalDateTime.now());
    }
}

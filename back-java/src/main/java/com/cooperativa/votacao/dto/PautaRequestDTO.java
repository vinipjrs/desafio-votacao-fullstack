package com.cooperativa.votacao.dto;

import jakarta.validation.constraints.NotBlank;

public record PautaRequestDTO(
    @NotBlank(message = "Título é obrigatório")
    String titulo,
    
    @NotBlank(message = "Descrição é obrigatória")
    String descricao
) {}

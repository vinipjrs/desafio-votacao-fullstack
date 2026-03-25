package com.cooperativa.votacao.dto;

import com.cooperativa.votacao.domain.VotoOpcao;

public record VotoMessageDTO(
    Long pautaId,
    String cpf,
    VotoOpcao opcao
) {}

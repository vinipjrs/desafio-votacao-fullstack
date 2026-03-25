package com.cooperativa.votacao.dto;

public record ResultadoVotacaoDTO(
    Long pautaId,
    String titulo,
    long votosSim,
    long votosNao,
    long totalVotos
) {}

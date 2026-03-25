package com.cooperativa.votacao.service;

import com.cooperativa.votacao.dto.CpfValidationResponseDTO;

public interface CpfValidationService {
    CpfValidationResponseDTO validarCpf(String cpf);
}

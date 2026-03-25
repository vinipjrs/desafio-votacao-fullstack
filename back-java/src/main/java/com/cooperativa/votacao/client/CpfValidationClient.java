package com.cooperativa.votacao.client;

import com.cooperativa.votacao.dto.CpfValidationResponseDTO;
import com.cooperativa.votacao.dto.CpfValidationStatus;
import com.cooperativa.votacao.service.CpfValidationService;
import org.springframework.stereotype.Component;

@Component
public class CpfValidationClient implements CpfValidationService {
    @Override
    public CpfValidationResponseDTO validarCpf(String cpf) {
        return new CpfValidationResponseDTO(CpfValidationStatus.ABLE_TO_VOTE);
    }
}

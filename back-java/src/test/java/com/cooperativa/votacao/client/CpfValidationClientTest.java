package com.cooperativa.votacao.client;

import com.cooperativa.votacao.dto.CpfValidationResponseDTO;
import com.cooperativa.votacao.dto.CpfValidationStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class CpfValidationClientTest {

    private final CpfValidationClient client = new CpfValidationClient();

    @Test
    void validarCpf_deveRetornarAbleToVote() {
        String cpf = "12345678901";
        CpfValidationResponseDTO response = client.validarCpf(cpf);

        assertNotNull(response);
        assertEquals(CpfValidationStatus.ABLE_TO_VOTE, response.status());
    }
}

package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.SessaoRequestDTO;
import com.cooperativa.votacao.dto.SessaoResponseDTO;
import com.cooperativa.votacao.service.SessaoVotacaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SessaoVotacaoController.class)
class SessaoVotacaoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private SessaoVotacaoService sessaoVotacaoService;

    @Test
    void abrirSessao_deveRetornarStatusCreated() throws Exception {
        Long pautaId = 1L;
        SessaoResponseDTO responseDTO = new SessaoResponseDTO(1L, pautaId, LocalDateTime.now(), LocalDateTime.now().plusMinutes(5), true);
        when(sessaoVotacaoService.abrirSessao(eq(pautaId), any(SessaoRequestDTO.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(post("/api/v1/pautas/" + pautaId + "/sessoes")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"minutosDuracao\": 5}"))
                .andExpect(status().isCreated());
    }
}

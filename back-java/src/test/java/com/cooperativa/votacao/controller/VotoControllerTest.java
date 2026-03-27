package com.cooperativa.votacao.controller;


import com.cooperativa.votacao.dto.VotoRequestDTO;
import com.cooperativa.votacao.service.VotoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VotoController.class)
class VotoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VotoService votoService;

    @Test
    void registrarVoto_deveRetornarStatusAccepted() throws Exception {
        Long pautaId = 1L;
        doNothing().when(votoService).registrarVoto(eq(pautaId), any(VotoRequestDTO.class));

        mockMvc.perform(post("/api/v1/pautas/" + pautaId + "/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cpf\": \"12345678901\", \"opcao\": \"SIM\"}"))
                .andExpect(status().isAccepted())
                .andExpect(content().string("Voto recebido e está sendo processado."));
    }

    @Test
    void registrarVoto_deveRetornarBadRequestQuandoCpfInvalido() throws Exception {
        Long pautaId = 1L;

        mockMvc.perform(post("/api/v1/pautas/" + pautaId + "/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cpf\": \"\", \"opcao\": \"SIM\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void registrarVoto_deveRetornarBadRequestQuandoOpcaoInvalida() throws Exception {
        Long pautaId = 1L;

        mockMvc.perform(post("/api/v1/pautas/" + pautaId + "/votos")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"cpf\": \"12345678901\", \"opcao\": null}"))
                .andExpect(status().isBadRequest());
    }
}

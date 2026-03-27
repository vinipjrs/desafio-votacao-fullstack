package com.cooperativa.votacao.controller;

import com.cooperativa.votacao.dto.PautaRequestDTO;
import com.cooperativa.votacao.dto.PautaResponseDTO;
import com.cooperativa.votacao.service.PautaService;
import com.cooperativa.votacao.service.VotoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PautaController.class)
class PautaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PautaService pautaService;

    @MockitoBean
    private VotoService votoService;

    @Test
    void listarPautas_deveRetornarStatusOk() throws Exception {
        when(pautaService.listarPautas()).thenReturn(Collections.emptyList());

        mockMvc.perform(get("/api/v1/pautas"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
    }

    @Test
    void criarPauta_deveRetornarStatusCreated() throws Exception {
        PautaResponseDTO pauta = new PautaResponseDTO(1L, "Teste", "Desc", LocalDateTime.now(), false, null);
        when(pautaService.criarPauta(any(PautaRequestDTO.class))).thenReturn(pauta);

        mockMvc.perform(post("/api/v1/pautas")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\": \"Teste\", \"descricao\": \"Desc\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.titulo").value("Teste"));
    }
}

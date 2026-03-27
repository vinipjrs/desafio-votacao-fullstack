package com.cooperativa.votacao;

import com.cooperativa.votacao.domain.Pauta;
import com.cooperativa.votacao.domain.Voto;
import com.cooperativa.votacao.domain.VotoOpcao;
import com.cooperativa.votacao.dto.*;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DomainAndDtoTest {

    @Test
    void testEntities() {
        LocalDateTime now = LocalDateTime.now();
        Pauta pauta = Pauta.builder()
                .id(1L)
                .titulo("Título")
                .descricao("Descrição")
                .criadoEm(now)
                .build();

        assertEquals(1L, pauta.getId());
        assertEquals("Título", pauta.getTitulo());
        assertEquals("Descrição", pauta.getDescricao());
        assertEquals(now, pauta.getCriadoEm());

        Voto voto = Voto.builder()
                .id(2L)
                .pauta(pauta)
                .cpf("12345678901")
                .opcao(VotoOpcao.SIM)
                .build();

        assertEquals(2L, voto.getId());
        assertEquals(pauta, voto.getPauta());
        assertEquals("12345678901", voto.getCpf());
        assertEquals(VotoOpcao.SIM, voto.getOpcao());
    }

    @Test
    void testDTOs() {
        LocalDateTime now = LocalDateTime.now();
        PautaResponseDTO pautaResp = new PautaResponseDTO(1L, "T", "D", now, true, now);
        assertEquals(1L, pautaResp.id());
        assertEquals("T", pautaResp.titulo());
        assertEquals(now, pautaResp.criadoEm());
        assertEquals(true, pautaResp.sessaoAtiva());

        SessaoResponseDTO sessaoResp = new SessaoResponseDTO(1L, 1L, now, now.plusMinutes(1), true);
        assertEquals(1L, sessaoResp.id());
        assertEquals(true, sessaoResp.aberta());

        VotoMessageDTO votoMsg = new VotoMessageDTO(1L, "123", VotoOpcao.NAO);
        assertEquals("123", votoMsg.cpf());
        assertEquals(VotoOpcao.NAO, votoMsg.opcao());

        ErrorResponseDTO error = new ErrorResponseDTO("Erro", 400, now);
        assertEquals(400, error.status());
        assertEquals("Erro", error.mensagem());
    }
}

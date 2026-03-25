package com.cooperativa.votacao.service;

import com.cooperativa.votacao.domain.Pauta;
import com.cooperativa.votacao.domain.SessaoVotacao;
import com.cooperativa.votacao.dto.SessaoRequestDTO;
import com.cooperativa.votacao.dto.SessaoResponseDTO;
import com.cooperativa.votacao.repository.PautaRepository;
import com.cooperativa.votacao.repository.SessaoVotacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SessaoVotacaoServiceTest {

    @Mock
    private SessaoVotacaoRepository sessaoRepository;

    @Mock
    private PautaRepository pautaRepository;

    @InjectMocks
    private SessaoVotacaoService sessaoVotacaoService;

    @Test
    void abrirSessao_deveAbrirComSucesso() {
        Long pautaId = 1L;
        Pauta pauta = Pauta.builder().id(pautaId).titulo("Pauta Teste").build();
        SessaoRequestDTO dto = new SessaoRequestDTO(5);

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(sessaoRepository.findFirstByPautaIdAndDataAberturaBeforeAndDataFechamentoAfter(any(), any(), any())).thenReturn(Optional.empty());
        when(sessaoRepository.save(any(SessaoVotacao.class))).thenAnswer(invocation -> invocation.getArgument(0));

        SessaoResponseDTO sessao = sessaoVotacaoService.abrirSessao(pautaId, dto);

        assertNotNull(sessao);
        assertEquals(pautaId, sessao.pautaId());
        assertTrue(sessao.dataFechamento().isAfter(sessao.dataAbertura()));
        verify(sessaoRepository, times(1)).save(any(SessaoVotacao.class));
    }
}

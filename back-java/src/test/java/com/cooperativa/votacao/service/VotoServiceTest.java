package com.cooperativa.votacao.service;

import com.cooperativa.votacao.client.CpfValidationClient;
import com.cooperativa.votacao.domain.Pauta;
import com.cooperativa.votacao.domain.VotoOpcao;
import com.cooperativa.votacao.dto.ResultadoVotacaoDTO;
import com.cooperativa.votacao.messaging.VotoProducer;
import com.cooperativa.votacao.repository.PautaRepository;
import com.cooperativa.votacao.repository.SessaoVotacaoRepository;
import com.cooperativa.votacao.repository.VotoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotoServiceTest {

    @Mock
    private VotoRepository votoRepository;
    @Mock
    private PautaRepository pautaRepository;
    @Mock
    private SessaoVotacaoRepository sessaoRepository;
    @Mock
    private CpfValidationService cpfValidationService;
    @Mock
    private VotoProducer votoProducer;
    @Mock
    private StringRedisTemplate redisTemplate;

    @InjectMocks
    private VotoService votoService;

    @Test
    void obterResultado_deveRetornarContagemCorreta() {
        Long pautaId = 1L;
        Pauta pauta = Pauta.builder().id(pautaId).titulo("Pauta Teste").build();

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(pauta));
        when(votoRepository.countByPautaIdAndOpcao(pautaId, VotoOpcao.SIM)).thenReturn(10L);
        when(votoRepository.countByPautaIdAndOpcao(pautaId, VotoOpcao.NAO)).thenReturn(5L);
        when(votoRepository.countByPautaId(pautaId)).thenReturn(15L);

        ResultadoVotacaoDTO resultado = votoService.obterResultado(pautaId);

        assertEquals(10, resultado.votosSim());
        assertEquals(5, resultado.votosNao());
        assertEquals(15, resultado.totalVotos());
        assertEquals("Pauta Teste", resultado.titulo());
    }
}

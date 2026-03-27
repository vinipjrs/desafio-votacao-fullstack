package com.cooperativa.votacao.service;


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

import com.cooperativa.votacao.dto.VotoRequestDTO;
import com.cooperativa.votacao.exception.BusinessException;
import com.cooperativa.votacao.exception.ObjectNotFoundException;
import com.cooperativa.votacao.dto.CpfValidationResponseDTO;
import com.cooperativa.votacao.dto.CpfValidationStatus;
import com.cooperativa.votacao.domain.SessaoVotacao;
import org.springframework.data.redis.core.ValueOperations;


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
    @Mock
    private ValueOperations<String, String> valueOperations;

    @InjectMocks
    private VotoService votoService;

    @Test
    void registrarVoto_deveRegistrarComSucesso() {
        Long pautaId = 1L;
        VotoRequestDTO dto = new VotoRequestDTO("12345678901", VotoOpcao.SIM);
        Pauta pauta = Pauta.builder().id(pautaId).build();
        SessaoVotacao sessao = SessaoVotacao.builder().id(1L).pauta(pauta).build();

        when(redisTemplate.hasKey(anyString())).thenReturn(false);
        when(votoRepository.existsByPautaIdAndCpf(pautaId, "12345678901")).thenReturn(false);
        when(pautaRepository.existsById(pautaId)).thenReturn(true);
        when(sessaoRepository.findFirstByPautaIdAndDataAberturaBeforeAndDataFechamentoAfter(any(), any(), any()))
                .thenReturn(Optional.of(sessao));
        when(cpfValidationService.validarCpf("12345678901"))
                .thenReturn(new CpfValidationResponseDTO(CpfValidationStatus.ABLE_TO_VOTE));
        when(redisTemplate.opsForValue()).thenReturn(valueOperations);

        assertDoesNotThrow(() -> votoService.registrarVoto(pautaId, dto));
        verify(votoProducer, times(1)).enviarVotoParaFila(any());
    }

    @Test
    void registrarVoto_deveLancarErroSeCpfJaVotouNoCache() {
        Long pautaId = 1L;
        VotoRequestDTO dto = new VotoRequestDTO("12345678901", VotoOpcao.SIM);

        when(redisTemplate.hasKey(anyString())).thenReturn(true);

        assertThrows(BusinessException.class, () -> votoService.registrarVoto(pautaId, dto));
    }

    @Test
    void registrarVoto_deveLancarErroSePautaNaoExiste() {
        Long pautaId = 1L;
        VotoRequestDTO dto = new VotoRequestDTO("12345678901", VotoOpcao.SIM);

        when(redisTemplate.hasKey(anyString())).thenReturn(false);
        when(votoRepository.existsByPautaIdAndCpf(pautaId, "12345678901")).thenReturn(false);
        when(pautaRepository.existsById(pautaId)).thenReturn(false);

        assertThrows(ObjectNotFoundException.class, () -> votoService.registrarVoto(pautaId, dto));
    }

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

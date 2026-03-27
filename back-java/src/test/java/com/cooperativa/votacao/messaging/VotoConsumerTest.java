package com.cooperativa.votacao.messaging;

import com.cooperativa.votacao.domain.Pauta;
import com.cooperativa.votacao.domain.SessaoVotacao;
import com.cooperativa.votacao.domain.Voto;
import com.cooperativa.votacao.domain.VotoOpcao;
import com.cooperativa.votacao.dto.VotoMessageDTO;
import com.cooperativa.votacao.repository.PautaRepository;
import com.cooperativa.votacao.repository.SessaoVotacaoRepository;
import com.cooperativa.votacao.repository.VotoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VotoConsumerTest {

    @Mock
    private VotoRepository votoRepository;
    @Mock
    private PautaRepository pautaRepository;
    @Mock
    private SessaoVotacaoRepository sessaoRepository;

    @InjectMocks
    private VotoConsumer votoConsumer;

    @Test
    void processarVoto_deveSalvarVotoComSucesso() {
        VotoMessageDTO message = new VotoMessageDTO(1L, "12345678901", VotoOpcao.SIM);
        Pauta pauta = Pauta.builder().id(1L).build();
        SessaoVotacao sessao = SessaoVotacao.builder().id(1L).build();

        when(votoRepository.existsByPautaIdAndCpf(1L, "12345678901")).thenReturn(false);
        when(sessaoRepository.findFirstByPautaIdAndDataAberturaBeforeAndDataFechamentoAfter(any(), any(), any()))
                .thenReturn(Optional.of(sessao));
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        votoConsumer.processarVoto(message);

        verify(votoRepository, times(1)).save(any(Voto.class));
    }

    @Test
    void processarVoto_deveIgnorarSeJaExisteVoto() {
        VotoMessageDTO message = new VotoMessageDTO(1L, "12345678901", VotoOpcao.SIM);

        when(votoRepository.existsByPautaIdAndCpf(1L, "12345678901")).thenReturn(true);

        votoConsumer.processarVoto(message);

        verify(votoRepository, never()).save(any(Voto.class));
    }

    @Test
    void processarVoto_deveIgnorarSeSessaoNaoEncontrada() {
        VotoMessageDTO message = new VotoMessageDTO(1L, "12345678901", VotoOpcao.SIM);

        when(votoRepository.existsByPautaIdAndCpf(1L, "12345678901")).thenReturn(false);
        when(sessaoRepository.findFirstByPautaIdAndDataAberturaBeforeAndDataFechamentoAfter(any(), any(), any()))
                .thenReturn(Optional.empty());

        votoConsumer.processarVoto(message);

        verify(votoRepository, never()).save(any(Voto.class));
    }

    @Test
    void processarVoto_deveIgnorarSePautaNaoEncontrada() {
        VotoMessageDTO message = new VotoMessageDTO(1L, "12345678901", VotoOpcao.SIM);
        SessaoVotacao sessao = SessaoVotacao.builder().id(1L).build();

        when(votoRepository.existsByPautaIdAndCpf(1L, "12345678901")).thenReturn(false);
        when(sessaoRepository.findFirstByPautaIdAndDataAberturaBeforeAndDataFechamentoAfter(any(), any(), any()))
                .thenReturn(Optional.of(sessao));
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());

        votoConsumer.processarVoto(message);

        verify(votoRepository, never()).save(any(Voto.class));
    }
}

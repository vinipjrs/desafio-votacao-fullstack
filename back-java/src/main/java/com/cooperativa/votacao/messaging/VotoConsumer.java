package com.cooperativa.votacao.messaging;

import com.cooperativa.votacao.config.RabbitMQConfig;
import com.cooperativa.votacao.domain.Pauta;
import com.cooperativa.votacao.domain.SessaoVotacao;
import com.cooperativa.votacao.domain.Voto;
import com.cooperativa.votacao.dto.VotoMessageDTO;
import com.cooperativa.votacao.repository.PautaRepository;
import com.cooperativa.votacao.repository.SessaoVotacaoRepository;
import com.cooperativa.votacao.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class VotoConsumer {

    private final VotoRepository votoRepository;
    private final PautaRepository pautaRepository;
    private final SessaoVotacaoRepository sessaoRepository;

    @RabbitListener(queues = RabbitMQConfig.FILA_VOTOS)
    @Transactional
    public void processarVoto(VotoMessageDTO message) {
        if (votoRepository.existsByPautaIdAndCpf(message.pautaId(), message.cpf())) {
            return;
        }

        SessaoVotacao sessao = sessaoRepository.findFirstByPautaIdAndDataAberturaBeforeAndDataFechamentoAfter(
                message.pautaId(), LocalDateTime.now(), LocalDateTime.now()
        ).orElse(null);

        if (sessao == null) return;

        Pauta pauta = pautaRepository.findById(message.pautaId()).orElse(null);
        if (pauta == null) return;

        Voto voto = Voto.builder()
                .pauta(pauta)
                .sessaoVotacao(sessao)
                .cpf(message.cpf())
                .opcao(message.opcao())
                .build();

        votoRepository.save(voto);
    }
}

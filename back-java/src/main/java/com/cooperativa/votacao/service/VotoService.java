package com.cooperativa.votacao.service;

import com.cooperativa.votacao.domain.Pauta;
import com.cooperativa.votacao.domain.SessaoVotacao;
import com.cooperativa.votacao.domain.VotoOpcao;
import com.cooperativa.votacao.dto.CpfValidationStatus;
import com.cooperativa.votacao.dto.ResultadoVotacaoDTO;
import com.cooperativa.votacao.dto.VotoMessageDTO;
import com.cooperativa.votacao.dto.VotoRequestDTO;
import com.cooperativa.votacao.messaging.VotoProducer;
import com.cooperativa.votacao.repository.PautaRepository;
import com.cooperativa.votacao.repository.SessaoVotacaoRepository;
import com.cooperativa.votacao.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class VotoService {

    private final VotoRepository votoRepository;
    private final PautaRepository pautaRepository;
    private final SessaoVotacaoRepository sessaoRepository;
    private final CpfValidationService cpfValidationService;
    private final VotoProducer votoProducer;
    private final StringRedisTemplate redisTemplate;

    public void registrarVoto(Long pautaId, VotoRequestDTO dto) {
        String cpf = dto.cpf().replaceAll("\\D", "");

        String cacheKey = "voto:pauta:" + pautaId + ":cpf:" + cpf;
        if (Boolean.TRUE.equals(redisTemplate.hasKey(cacheKey))) {
            throw new RuntimeException("O CPF informado já registrou um voto para esta pauta.");
        }

        if (votoRepository.existsByPautaIdAndCpf(pautaId, cpf)) {
            redisTemplate.opsForValue().set(cacheKey, "true", Duration.ofHours(24));
            throw new RuntimeException("O CPF informado já registrou um voto para esta pauta.");
        }

        if (!pautaRepository.existsById(pautaId)) {
            throw new RuntimeException("Pauta não encontrada.");
        }

        SessaoVotacao sessao = sessaoRepository.findFirstByPautaIdAndDataAberturaBeforeAndDataFechamentoAfter(
                pautaId, LocalDateTime.now(), LocalDateTime.now()
        ).orElseThrow(() -> new RuntimeException("Não há sessão de votação ativa para esta pauta."));

        if (CpfValidationStatus.UNABLE_TO_VOTE.equals(cpfValidationService.validarCpf(cpf).status())) {
            throw new RuntimeException("O CPF informado não está autorizado a votar.");
        }

        redisTemplate.opsForValue().set(cacheKey, "true", Duration.ofHours(24));
        votoProducer.enviarVotoParaFila(new VotoMessageDTO(pautaId, cpf, dto.opcao()));
    }

    public ResultadoVotacaoDTO obterResultado(Long pautaId) {
        Pauta pauta = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada."));

        long sim = votoRepository.countByPautaIdAndOpcao(pautaId, VotoOpcao.SIM);
        long nao = votoRepository.countByPautaIdAndOpcao(pautaId, VotoOpcao.NAO);
        long total = votoRepository.countByPautaId(pautaId);

        return new ResultadoVotacaoDTO(
                pauta.getId(),
                pauta.getTitulo(),
                sim,
                nao,
                total
        );
    }
}

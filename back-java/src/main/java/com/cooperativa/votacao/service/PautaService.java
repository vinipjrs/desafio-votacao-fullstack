package com.cooperativa.votacao.service;

import com.cooperativa.votacao.domain.Pauta;
import com.cooperativa.votacao.domain.SessaoVotacao;
import com.cooperativa.votacao.dto.PautaRequestDTO;
import com.cooperativa.votacao.dto.PautaResponseDTO;
import com.cooperativa.votacao.repository.PautaRepository;
import com.cooperativa.votacao.repository.SessaoVotacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PautaService {

    private final PautaRepository pautaRepository;
    private final SessaoVotacaoRepository sessaoRepository;

    @Transactional
    public PautaResponseDTO criarPauta(PautaRequestDTO dto) {
        Pauta novaPauta = Pauta.builder()
                .titulo(dto.titulo())
                .descricao(dto.descricao())
                .criadoEm(LocalDateTime.now())
                .build();
        Pauta pautaSalva = pautaRepository.save(novaPauta);
        return mapToResponse(pautaSalva);
    }

    public List<PautaResponseDTO> listarPautas() {
        return pautaRepository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    public PautaResponseDTO buscarPorId(Long id) {
        return pautaRepository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada."));
    }

    // Mapper manual simples pra não precisar de bibliotecas pesadas de Bean Copy
    private PautaResponseDTO mapToResponse(Pauta entity) {
        // Busca a sessão ativa pro timer do front saber quando fechar
        SessaoVotacao sessaoAtiva = sessaoRepository.findFirstByPautaIdAndDataAberturaBeforeAndDataFechamentoAfter(
            entity.getId(), LocalDateTime.now(), LocalDateTime.now()
        ).orElse(null);

        return new PautaResponseDTO(
            entity.getId(), 
            entity.getTitulo(), 
            entity.getDescricao(), 
            entity.getCriadoEm(),
            sessaoAtiva != null,
            sessaoAtiva != null ? sessaoAtiva.getDataFechamento() : null
        );
    }
}

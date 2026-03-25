package com.cooperativa.votacao.service;

import com.cooperativa.votacao.domain.Pauta;
import com.cooperativa.votacao.domain.SessaoVotacao;
import com.cooperativa.votacao.dto.SessaoRequestDTO;
import com.cooperativa.votacao.dto.SessaoResponseDTO;
import com.cooperativa.votacao.repository.PautaRepository;
import com.cooperativa.votacao.repository.SessaoVotacaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SessaoVotacaoService {
    
    private final SessaoVotacaoRepository sessaoRepository;
    private final PautaRepository pautaRepository;
    
    @Transactional
    public SessaoResponseDTO abrirSessao(Long pautaId, SessaoRequestDTO dto) {
        Pauta pautaFound = pautaRepository.findById(pautaId)
                .orElseThrow(() -> new RuntimeException("Pauta não encontrada."));
        
        sessaoRepository.findFirstByPautaIdAndDataAberturaBeforeAndDataFechamentoAfter(
            pautaId, LocalDateTime.now(), LocalDateTime.now()
        ).ifPresent(s -> {
            throw new RuntimeException("Já existe uma sessão de votação ativa para esta pauta.");
        });

        int minDuracao = (dto != null && dto.minutosDuracao() != null && dto.minutosDuracao() > 0) 
            ? dto.minutosDuracao() : 1;
        
        LocalDateTime agora = LocalDateTime.now();
        SessaoVotacao novaSessao = SessaoVotacao.builder()
                .pauta(pautaFound)
                .dataAbertura(agora)
                .dataFechamento(agora.plusMinutes(minDuracao))
                .build();
                
        SessaoVotacao sessaoSalva = sessaoRepository.save(novaSessao);
        return mapToResponse(sessaoSalva);
    }

    private SessaoResponseDTO mapToResponse(SessaoVotacao entity) {
        return new SessaoResponseDTO(
            entity.getId(), 
            entity.getPauta().getId(), 
            entity.getDataAbertura(), 
            entity.getDataFechamento(), 
            entity.isAberta()
        );
    }
}

package com.cooperativa.votacao.repository;

import com.cooperativa.votacao.domain.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface SessaoVotacaoRepository extends JpaRepository<SessaoVotacao, Long> {
    Optional<SessaoVotacao> findFirstByPautaIdAndDataAberturaBeforeAndDataFechamentoAfter(
            Long pautaId, LocalDateTime opener, LocalDateTime closer
    );
}

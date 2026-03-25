package com.cooperativa.votacao.repository;

import com.cooperativa.votacao.domain.Voto;
import com.cooperativa.votacao.domain.VotoOpcao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsByPautaIdAndCpf(Long pautaId, String cpf);
    long countByPautaIdAndOpcao(Long pautaId, VotoOpcao opcao);
    long countByPautaId(Long pautaId);
}

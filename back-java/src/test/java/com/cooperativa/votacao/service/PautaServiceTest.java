package com.cooperativa.votacao.service;

import com.cooperativa.votacao.domain.Pauta;
import com.cooperativa.votacao.dto.PautaRequestDTO;
import com.cooperativa.votacao.dto.PautaResponseDTO;
import com.cooperativa.votacao.repository.PautaRepository;
import com.cooperativa.votacao.repository.SessaoVotacaoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository;

    @Mock
    private SessaoVotacaoRepository sessaoRepository;

    @InjectMocks
    private PautaService pautaService;

    @Test
    void criarPauta_deveSalvarComSucesso() {
        PautaRequestDTO dto = new PautaRequestDTO("Teste", "Descricao");
        Pauta pauta = Pauta.builder().id(1L).titulo("Teste").descricao("Descricao").criadoEm(LocalDateTime.now()).build();

        when(pautaRepository.save(any(Pauta.class))).thenReturn(pauta);

        PautaResponseDTO resultado = pautaService.criarPauta(dto);

        assertNotNull(resultado);
        assertEquals("Teste", resultado.titulo());
        verify(pautaRepository, times(1)).save(any(Pauta.class));
    }
}

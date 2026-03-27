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
import java.util.List;

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

    @Test
    void listarPautas_deveRetornarListaDeResponseDTO() {
        Pauta pauta = Pauta.builder().id(1L).titulo("Teste").descricao("Desc").criadoEm(LocalDateTime.now()).build();
        when(pautaRepository.findAll()).thenReturn(List.of(pauta));

        List<PautaResponseDTO> resultado = pautaService.listarPautas();

        assertNotNull(resultado);
        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("Teste", resultado.get(0).titulo());
    }

    @Test
    void buscarPorId_deveRetornarPautaQuandoEncontrada() {
        Pauta pauta = Pauta.builder().id(1L).titulo("Encontrada").build();
        when(pautaRepository.findById(1L)).thenReturn(java.util.Optional.of(pauta));

        PautaResponseDTO resultado = pautaService.buscarPorId(1L);

        assertNotNull(resultado);
        assertEquals("Encontrada", resultado.titulo());
    }

    @Test
    void buscarPorId_deveLancarExcecaoQuandoNaoEncontrada() {
        when(pautaRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(RuntimeException.class, () -> pautaService.buscarPorId(1L));
    }
}

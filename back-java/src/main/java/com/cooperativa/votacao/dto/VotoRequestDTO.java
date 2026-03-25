package com.cooperativa.votacao.dto;

import com.cooperativa.votacao.domain.VotoOpcao;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record VotoRequestDTO(
    @NotBlank(message = "CPF é obrigatório")
    @Pattern(regexp = "\\d{11}", message = "CPF deve conter 11 dígitos numéricos")
    String cpf,
    
    @NotNull(message = "A opção de voto é obrigatória")
    VotoOpcao opcao
) {}

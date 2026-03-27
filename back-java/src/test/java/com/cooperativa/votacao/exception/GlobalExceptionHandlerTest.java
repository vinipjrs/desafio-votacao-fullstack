package com.cooperativa.votacao.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

    @Test
    void handleBusinessException_deveRetornarBadRequest() {
        BusinessException ex = new BusinessException("Erro de negócio");
        ResponseEntity<Object> response = handler.handleBusinessException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals("Erro de negócio", body.get("mensagem"));
        assertEquals(400, body.get("status"));
    }

    @Test
    void handleObjectNotFoundException_deveRetornarNotFound() {
        ObjectNotFoundException ex = new ObjectNotFoundException("Não encontrado");
        ResponseEntity<Object> response = handler.handleObjectNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals("Não encontrado", body.get("mensagem"));
        assertEquals(404, body.get("status"));
    }

    @Test
    void handleRuntimeException_deveRetornarBadRequest() {
        RuntimeException ex = new RuntimeException("Erro genérico");
        ResponseEntity<Object> response = handler.handleRuntimeException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals("Erro genérico", body.get("mensagem"));
    }

    @Test
    void handleValidationExceptions_deveRetornarBadRequestComDetalhes() {
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        FieldError fieldError = new FieldError("obj", "campo", "mensagem erro");
        
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(fieldError));

        ResponseEntity<Object> response = handler.handleValidationExceptions(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals("Erro de validação: campo: mensagem erro", body.get("mensagem"));
    }

    @Test
    void handleGeneralException_deveRetornarInternalServerError() {
        Exception ex = new Exception("Erro fatal");
        ResponseEntity<Object> response = handler.handleGeneralException(ex);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        Map<String, Object> body = (Map<String, Object>) response.getBody();
        assertNotNull(body);
        assertEquals("Ocorreu um erro interno inesperado.", body.get("mensagem"));
    }
}

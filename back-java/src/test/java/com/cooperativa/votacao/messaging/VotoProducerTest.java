package com.cooperativa.votacao.messaging;

import com.cooperativa.votacao.config.RabbitMQConfig;
import com.cooperativa.votacao.domain.VotoOpcao;
import com.cooperativa.votacao.dto.VotoMessageDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;


import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class VotoProducerTest {

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private VotoProducer votoProducer;

    @Test
    void enviarVotoParaFila_deveEnviarMensagemCorretamente() {
        VotoMessageDTO message = new VotoMessageDTO(1L, "12345678901", VotoOpcao.SIM);

        votoProducer.enviarVotoParaFila(message);

        verify(rabbitTemplate, times(1)).convertAndSend(
                RabbitMQConfig.EXCHANGE,
                RabbitMQConfig.ROUTING_KEY,
                message
        );
    }
}

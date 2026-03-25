package com.cooperativa.votacao.messaging;

import com.cooperativa.votacao.config.RabbitMQConfig;
import com.cooperativa.votacao.dto.VotoMessageDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VotoProducer {

    private final RabbitTemplate rabbitTemplate;

    public void enviarVotoParaFila(VotoMessageDTO message) {
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTING_KEY, message);
    }
}

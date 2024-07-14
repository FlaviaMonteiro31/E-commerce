package br.com.ms_pagamentos.integration.gateway;

import br.com.ms_pagamentos.model.records.ConsultaCarrinhoRequest;
import br.com.ms_pagamentos.model.records.ConsultaCarrinhoResponse;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@MessagingGateway
public interface ConsultaCarrinhoGateway {
    @Gateway(requestChannel = "consultaCarrinho",
            requestTimeout = 5000,
            headers = {@GatewayHeader(name = MessageHeaders.REPLY_CHANNEL, expression = "@nullChannel")})
    ConsultaCarrinhoResponse consultaCarrinho(Message<ConsultaCarrinhoRequest> consultaCarrinho);
}

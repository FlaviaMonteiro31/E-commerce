package br.com.ms_carrinho_compras.integration.gateway;

import br.com.ms_carrinho_compras.model.records.ConsultaUsuarioRequest;
import br.com.ms_carrinho_compras.model.records.ConsultaUsuarioResponse;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@MessagingGateway
public interface ConsultaUsuarioGateway {
    @Gateway(requestChannel = "consultaUsuario",
            requestTimeout = 5000,
            headers = {@GatewayHeader(name = MessageHeaders.REPLY_CHANNEL, expression = "@nullChannel")})
    ConsultaUsuarioResponse consultaUsuario(Message<ConsultaUsuarioRequest> consultaUsuario);
}

package br.com.ms_carrinho_compras.integration.gateway;

import br.com.ms_carrinho_compras.model.records.ConsultaProdutoRequest;
import br.com.ms_carrinho_compras.model.records.ConsultaProdutoResponse;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.GatewayHeader;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;

@MessagingGateway
public interface ConsultaProdutoGateway {
    @Gateway(requestChannel = "consultaProduto",
            requestTimeout = 5000,
            headers = {@GatewayHeader(name = MessageHeaders.REPLY_CHANNEL, expression = "@nullChannel")})
    ConsultaProdutoResponse consultaProduto(Message<ConsultaProdutoRequest> consultaProduto);
}

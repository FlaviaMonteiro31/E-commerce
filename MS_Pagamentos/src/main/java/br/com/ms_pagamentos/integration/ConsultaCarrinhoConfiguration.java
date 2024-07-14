package br.com.ms_pagamentos.integration;

import br.com.ms_pagamentos.model.records.ConsultaCarrinhoResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ConsultaCarrinhoConfiguration {

    @Bean
    public MessageChannel consultaCarrinho(){
        DirectChannel directChannel = new DirectChannel();
        directChannel.setFailover(false);
        return directChannel;
    }

    @Bean
    public IntegrationFlow consultaCarrinhoFlow(){
        return IntegrationFlow.from("consultaCarrinho")
                .handle(Http.outboundGateway("http://localhost:8083/controle-carrinho/consultaCarrinho")
                        .httpMethod(HttpMethod.POST)
                        .expectedResponseType(ConsultaCarrinhoResponse.class))
                .log()
                .bridge().get();
    }
}

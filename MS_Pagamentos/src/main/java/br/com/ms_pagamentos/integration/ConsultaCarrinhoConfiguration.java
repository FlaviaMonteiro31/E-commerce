package br.com.ms_pagamentos.integration;

import br.com.ms_pagamentos.model.records.ConsultaCarrinhoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ConsultaCarrinhoConfiguration {

    @Value("${ms.carrinho}")
    private String consultaCarrinhoUrl;

    @Bean
    public MessageChannel consultaCarrinho(){
        DirectChannel directChannel = new DirectChannel();
        directChannel.setFailover(false);
        return directChannel;
    }

    @Bean
    public IntegrationFlow consultaCarrinhoFlow(){
        return IntegrationFlow.from("consultaCarrinho")
                .handle(Http.outboundGateway(consultaCarrinhoUrl)
                        .httpMethod(HttpMethod.POST)
                        .expectedResponseType(ConsultaCarrinhoResponse.class))
                .log()
                .bridge().get();
    }
}

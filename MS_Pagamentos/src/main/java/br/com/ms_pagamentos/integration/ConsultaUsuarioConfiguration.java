package br.com.ms_pagamentos.integration;

import br.com.ms_pagamentos.model.records.ConsultaUsuarioResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ConsultaUsuarioConfiguration {

    @Bean
    public MessageChannel consultaUsuario(){
        DirectChannel directChannel = new DirectChannel();
        directChannel.setFailover(false);
        return directChannel;
    }

    @Bean
    public IntegrationFlow consultaUsuarioFlow(){
        return IntegrationFlow.from("consultaUsuario")
                .handle(Http.outboundGateway("http://localhost:8084/controle-usuario/consultaUsuario")
                        .httpMethod(HttpMethod.POST)
                        .expectedResponseType(ConsultaUsuarioResponse.class))
                .log()
                .bridge().get();
    }
}

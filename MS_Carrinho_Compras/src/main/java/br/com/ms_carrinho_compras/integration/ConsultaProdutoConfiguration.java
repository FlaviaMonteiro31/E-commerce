package br.com.ms_carrinho_compras.integration;

import br.com.ms_carrinho_compras.model.records.ConsultaProdutoResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.http.dsl.Http;
import org.springframework.messaging.MessageChannel;

@Configuration
public class ConsultaProdutoConfiguration {

    @Value("${ms.produtos}")
    private String consultaProdutoUrl;

    @Bean
    public MessageChannel consultaProduto(){
        DirectChannel directChannel = new DirectChannel();
        directChannel.setFailover(false);
        return directChannel;
    }

    @Bean
    public IntegrationFlow consultaProdutoFlow(){
        return IntegrationFlow.from("consultaProduto")
                .handle(Http.outboundGateway(consultaProdutoUrl)
                        .httpMethod(HttpMethod.POST)
                        .expectedResponseType(ConsultaProdutoResponse.class))
                .log()
                .bridge().get();
    }
}

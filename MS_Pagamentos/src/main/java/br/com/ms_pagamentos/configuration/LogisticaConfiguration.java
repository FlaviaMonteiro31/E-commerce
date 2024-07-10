//package br.com.ms_pedidos.configuration;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.HttpMethod;
//import org.springframework.integration.channel.DirectChannel;
//import org.springframework.integration.dsl.IntegrationFlow;
//import org.springframework.integration.http.dsl.Http;
//import org.springframework.messaging.MessageChannel;
//
//@Configuration
//public class LogisticaConfiguration {
//
//    @Bean
//    public MessageChannel logistica(){
//        DirectChannel directChannel = new DirectChannel();
//        directChannel.setFailover(false);
//        return directChannel;
//    }
//
//    @Bean
//    public IntegrationFlow logisticaFlow(){
//        return IntegrationFlow.from("logistica")
//                .handle(Http.outboundGateway("http://localhost:8086/logistica/preparaEntrega").httpMethod(HttpMethod.POST))
//                .log()
//                .bridge().get();
//    }
//}

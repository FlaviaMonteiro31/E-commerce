package br.com.ms_carrinho_compras.consumer;

import br.com.ms_carrinho_compras.model.records.ConsultaCarrinhoRequest;
import br.com.ms_carrinho_compras.model.records.ConsultaCarrinhoResponse;
import br.com.ms_carrinho_compras.service.CarrinhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ConsultaCarrinhoConsumer {

    @Autowired
    private CarrinhoService service;

    @Bean(name = "consultaCarrinho")
    Function<ConsultaCarrinhoRequest, ConsultaCarrinhoResponse> consumer(){
        return ler -> {
            return service.consultaCarrinhoPorId(ler);
        };
    }

}

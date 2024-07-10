package br.com.ms_gestao_itens.consumer;

import br.com.ms_gestao_itens.model.records.ConsultaProdutoRequest;
import br.com.ms_gestao_itens.model.records.ConsultaProdutoResponse;
import br.com.ms_gestao_itens.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public class ConsultaProdutoConsumer {

    @Autowired
    private ProdutoService service;

    @Bean(name = "consultaProduto")
    Function<ConsultaProdutoRequest, ConsultaProdutoResponse> consumer(){
        return ler -> {
            return service.consultaProdutoPorId(ler);
        };
    }

}

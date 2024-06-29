package br.com.ms_gestao_itens.configuration;

import br.com.ms_gestao_itens.exception.ProdutoException;
import br.com.ms_gestao_itens.model.records.ProdutoEstoqueRequest;
import br.com.ms_gestao_itens.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
public class ProdutoConsumer {

    @Autowired
    private ProdutoService service;

    @Bean(name = "removerEstoque")
    Consumer<ProdutoEstoqueRequest> consumer(){
        return produtoEstoqueRequest -> {
            try {
                service.removerEstoque(produtoEstoqueRequest.getId(),produtoEstoqueRequest.getQuantidade());
            } catch (ProdutoException e) {
                throw new RuntimeException(e);
            }
        };
    }
}

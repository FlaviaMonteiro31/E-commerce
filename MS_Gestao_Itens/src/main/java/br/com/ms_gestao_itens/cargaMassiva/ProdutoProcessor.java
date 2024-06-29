package br.com.ms_gestao_itens.cargaMassiva;

import br.com.ms_gestao_itens.model.Produto;
import org.springframework.batch.item.ItemProcessor;

import java.util.UUID;

public class ProdutoProcessor implements ItemProcessor<Produto,Produto> {
    @Override
    public Produto process(Produto pe) throws Exception {
        pe.setId(UUID.randomUUID());
        return pe;
    }
}

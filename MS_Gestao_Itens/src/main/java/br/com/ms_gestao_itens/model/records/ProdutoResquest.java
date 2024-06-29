package br.com.ms_gestao_itens.model.records;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoResquest {
    private String nome;
    private String descricao;
    private String tamanho;
    private String cor;
    private BigDecimal preco;
    private BigDecimal quantidade;

}

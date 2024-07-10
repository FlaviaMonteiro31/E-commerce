package br.com.ms_gestao_itens.model.records;

import br.com.ms_gestao_itens.model.Produto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaProdutoResponse {

    private UUID id;
    private String nome;
    private String descricao;
    private String tamanho;
    private String cor;
    private BigDecimal preco;
    private BigDecimal quantidade;

    public ConsultaProdutoResponse(Produto pe) {
        this.id = pe.getId();
        this.nome= pe.getNome();
        this.descricao = pe.getDescricao();
        this.tamanho = pe.getTamanho();
        this.cor = pe.getCor();
        this.preco = pe.getPreco();
        this.quantidade = pe.getQuantidade();
    }

}

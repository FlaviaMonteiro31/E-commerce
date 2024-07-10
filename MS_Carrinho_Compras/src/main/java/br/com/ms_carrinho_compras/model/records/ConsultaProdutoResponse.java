package br.com.ms_carrinho_compras.model.records;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class ConsultaProdutoResponse {

    private UUID id;
    private String nome;
    private String descricao;
    private String tamanho;
    private String cor;
    private BigDecimal preco;
    private BigDecimal quantidade;

    public ConsultaProdutoResponse(UUID id, String nome, String descricao, String tamanho, String cor, BigDecimal preco, BigDecimal quantidade) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.tamanho = tamanho;
        this.cor = cor;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    public ConsultaProdutoResponse() {
    }
}

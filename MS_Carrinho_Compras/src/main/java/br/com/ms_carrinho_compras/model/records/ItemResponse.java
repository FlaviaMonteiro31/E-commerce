package br.com.ms_carrinho_compras.model.records;

import br.com.ms_carrinho_compras.model.Item;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ItemResponse {
    private UUID idproduto;
    private BigDecimal quantidade;

    public ItemResponse(Item item) {
        this.idproduto = item.getIdproduto();
        this.quantidade = item.getQuantidade();
    }
}

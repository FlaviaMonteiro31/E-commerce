package br.com.ms_carrinho_compras.model.records;

import br.com.ms_carrinho_compras.model.Carrinho;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoResponse {
    private UUID id;
    private UUID usuario;
    private List<ItemResponse> itens;

    public CarrinhoResponse(Carrinho carrinho) {
        this.id = carrinho.getIdcarrinho();
        this.usuario = carrinho.getUsuario();
        this.itens = carrinho.getItens().stream()
               .map(ItemResponse::new)
                .collect(Collectors.toList());

    }
}

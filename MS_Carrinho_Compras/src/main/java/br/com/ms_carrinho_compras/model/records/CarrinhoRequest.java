package br.com.ms_carrinho_compras.model.records;

import br.com.ms_carrinho_compras.model.Item;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarrinhoRequest {

    private UUID usuario;
    @NotNull
    private List<Item> itens;
}

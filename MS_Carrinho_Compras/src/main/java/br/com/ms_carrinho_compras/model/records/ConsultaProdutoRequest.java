package br.com.ms_carrinho_compras.model.records;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class ConsultaProdutoRequest {
    private UUID id;

    public ConsultaProdutoRequest(UUID id) {
        this.id = id;
    }

    public ConsultaProdutoRequest() {
    }
}

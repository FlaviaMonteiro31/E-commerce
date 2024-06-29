package br.com.ms_gestao_itens.model.records;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoEstoqueRequest {
    private UUID id;
    private BigDecimal quantidade;
}

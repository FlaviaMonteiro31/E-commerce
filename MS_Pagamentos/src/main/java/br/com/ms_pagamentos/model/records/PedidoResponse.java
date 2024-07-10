package br.com.ms_pagamentos.model.records;

import br.com.ms_pagamentos.model.Endereco;
import br.com.ms_pagamentos.model.Pedido;
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
public class PedidoResponse {
    private UUID id;
    private UUID cliente;
    private List<ItemResponse> itens;
    private String status;
    private Endereco enderecoEntrega;

    public PedidoResponse(Pedido pedido) {
        this.id = pedido.getIdpedido();
        this.cliente = pedido.getCliente();
        this.itens = pedido.getItens().stream()
                .map(ItemResponse::new)
                .collect(Collectors.toList());
        this.status = pedido.getStatus();
        this.enderecoEntrega = pedido.getEnderecoEntrega();
    }
}

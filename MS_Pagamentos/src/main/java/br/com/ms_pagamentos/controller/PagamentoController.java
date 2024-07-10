package br.com.ms_pagamentos.controller;

import br.com.ms_pagamentos.exception.PagamentoException;
import br.com.ms_pagamentos.model.records.PagamentoRequest;
import br.com.ms_pagamentos.model.records.PagamentoResponse;
import br.com.ms_pagamentos.model.records.PedidoRequest;
import br.com.ms_pagamentos.model.records.PedidoResponse;
import br.com.ms_pagamentos.service.PagamentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/pagamento")
public class PagamentoController {

    @Autowired
    private PagamentoService service;

    @Operation(description = "Realiza o pagamento")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna dados pagamento"),
    })
    @PostMapping(value = "/pagar")
    public ResponseEntity<PagamentoResponse> pagar(@RequestBody @Valid PagamentoRequest request) throws PagamentoException {

        PagamentoResponse response = service.efetuaPagamento(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{idpagamento}").buildAndExpand(response.getIdpagamento()).toUri();
        return ResponseEntity.created(uri).body(response);
    }

    @Operation(description = "Gera o pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido criado"),
    })
    @PostMapping(value = "/geraPedido")
    public ResponseEntity<PedidoResponse> gerarPedido(@RequestBody @Valid PedidoRequest request)
            throws PagamentoException {

        PedidoResponse response = service.gerarPedido(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

}

package br.com.ms_carrinho_compras.controller;

import br.com.ms_carrinho_compras.exception.CarrinhoException;
import br.com.ms_carrinho_compras.model.records.*;
import br.com.ms_carrinho_compras.service.CarrinhoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoController {

    @Autowired
    private CarrinhoService service;

    @Operation(description = "Insere itens no carrinho ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto inserido com sucesso"),
    })
    @PostMapping(value = "/criaCarrinho")
    public ResponseEntity<CarrinhoResponse> insereItensCarrinho(@RequestBody @Valid CarrinhoRequest request)
            throws CarrinhoException {

        CarrinhoResponse response = service.criaCarrinho(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Operation(description = "Consulta itens do carrinho por Id do usu[ario.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna dados do carrinho"),
            @ApiResponse(responseCode = "400", description = "Carrinho não localizado.")
    })
    @GetMapping("/lista/{id}")
    public ResponseEntity<CarrinhoResponse> listaPorId(@PathVariable UUID id)
            throws CarrinhoException {
        return ResponseEntity.ok(service.listaCarrinhoPorIdUsuario(id));
    }

    @Operation(description = "Atualiza carrinho novos itens")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna dados do produto"),
            @ApiResponse(responseCode = "400", description = "Carrinho não localizado."),
    })
    @PutMapping(value = "/incluirNovoItem/{id}")
    public ResponseEntity<CarrinhoResponse>
        atualizaCarrinhoComNovosItens(@RequestBody AlteraCarrinhoRequest request, @PathVariable UUID id)
            throws CarrinhoException {
        return ResponseEntity.ok(service.adicionaItenNovoCarrinho(request, id));
    }

//    @Operation(description = "Remove itens do carrinho")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Retorna dados do produto"),
//            @ApiResponse(responseCode = "400", description = "Carrinho não localizado."),
//    })
//    @PutMapping(value = "/removeItem/{id}")
//    public ResponseEntity<CarrinhoResponse> removeItensCarrinho(@RequestBody CarrinhoRequest request, @PathVariable UUID id)
//            throws CarrinhoException {
//        return ResponseEntity.ok(service.removeItemCarrinho(request, id));
//    }

    @Operation(description = "Deleta o carrinho")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados deletados.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deletaCarrinho(@PathVariable UUID id) throws CarrinhoException {
        service.deletaCarrinho(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/consultaProduto", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ConsultaProdutoResponse> consulta(@RequestBody @Valid ConsultaProdutoRequest request){
        return ResponseEntity.ok(service.consulta(request));
    }

}

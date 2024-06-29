package br.com.ms_gestao_itens.controller;
import br.com.ms_gestao_itens.exception.ProdutoException;
import br.com.ms_gestao_itens.model.records.ProdutoResponse;
import br.com.ms_gestao_itens.model.records.ProdutoResquest;
import br.com.ms_gestao_itens.service.ProdutoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/gestaoItensProdutos")
public class ProdutoController {
    @Autowired
    private ProdutoService service;
    @Operation(description = "Realiza do cadastro do produto e estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna dados do produto e estoque cadastrado"),
    })
    @PostMapping(value = "/cadastrar")
    public ResponseEntity<ProdutoResponse> inserirProdutoEstoque(@RequestBody @Valid ProdutoResquest request)
            throws ProdutoException {

        ProdutoResponse response = service.inserirProduto(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(response.getId()).toUri();

        return ResponseEntity.created(uri).body(response);
    }
    @Operation(description = "Consulta dados de produto por Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna dados do produto e estoque"),
            @ApiResponse(responseCode = "400", description = "Produto não localizado.")
    })
    @GetMapping("/lista/{id}")
    public ResponseEntity<ProdutoResponse> listaPorId(@PathVariable UUID id)
            throws ProdutoException {
        var pe = service.listaPorId(id);
        return ResponseEntity.ok(pe);
    }
    @Operation(description = "Consulta todos os produtos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna dados de todos os produtos e estoque"),
    })
    @GetMapping(value = "/listaTodos")
    public Page<ProdutoResponse> listarTodos (
            @RequestParam(value = "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho
    ) {
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        Page<ProdutoResponse> pe = service.listaTodos(pageRequest);
        return pe;
    }
    @Operation(description = "Atualiza dados do produto")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna dados do produto"),
            @ApiResponse(responseCode = "400", description = "Produto não localizado."),
    })
    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<ProdutoResponse> atualizaProduto(@RequestBody @Valid ProdutoResquest request, @PathVariable UUID id)
            throws ProdutoException {
        var pe = service.atualizaProduto(request, id);
        return ResponseEntity.ok(pe);
    }

    @Operation(description = "Deleta produto e estoque")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados deletados.")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deletaProduto(@PathVariable UUID id) throws ProdutoException {
        service.deletaProduto(id);
        return ResponseEntity.noContent().build();
    }

}

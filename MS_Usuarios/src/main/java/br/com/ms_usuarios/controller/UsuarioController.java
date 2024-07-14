package br.com.ms_usuarios.controller;
import br.com.ms_usuarios.exception.UsuarioException;
import br.com.ms_usuarios.model.records.LoginRequest;
import br.com.ms_usuarios.model.records.LoginResponse;
import br.com.ms_usuarios.model.records.UsuarioRequest;
import br.com.ms_usuarios.model.records.UsuarioResponse;
import br.com.ms_usuarios.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Valid LoginRequest request) throws UsuarioException {
        return ResponseEntity.ok(service.login(request));
    }

    @Operation(description = "Realiza o cadastro do usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna dados do usuario cadastrado"),
    })
    @PostMapping(value = "/cadastrar")
    public ResponseEntity<UsuarioResponse> inserirUsuario (@RequestBody @Valid UsuarioRequest request)
            throws UsuarioException {

        UsuarioResponse response = service.inserirUsuario(request);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{cpf}").buildAndExpand(response.getCpf()).toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @Operation(description = "Consulta dados usuario por Id.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna dados do usuario"),
            @ApiResponse(responseCode = "400", description = "Usuario não localizado.")
    })
    @GetMapping("/listaUsuario/{id}")
    public ResponseEntity<UsuarioResponse> listaUsuarioPorId(@PathVariable UUID id)
            throws UsuarioException {
        var usuario = service.listaUsuarioPorId(id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(description = "Consulta dados de usuarios")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna dados de todos os usuarios"),
    })
    @GetMapping(value = "/listaTodos")
    public ResponseEntity<Page<UsuarioResponse>> listarTodosUsuario (
            @RequestParam(value =  "pagina", defaultValue = "0") Integer pagina,
            @RequestParam(value = "tamanho", defaultValue = "10") Integer tamanho
    ){
        PageRequest pageRequest = PageRequest.of(pagina, tamanho);
        var usuarios = service.listaTodosUsuarios(pageRequest);
        return ResponseEntity.ok(usuarios);
    }

    @Operation(description = "Atualiza dados do usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Retorna dados do usuaro"),
            @ApiResponse(responseCode = "400", description = "Usuario não localizado."),
    })
    @PutMapping(value = "/atualizar/{id}")
    public ResponseEntity<UsuarioResponse> atualizarUsuario(@RequestBody @Valid UsuarioRequest request, @PathVariable UUID id)
            throws UsuarioException {
        var usuario = service.atualizaUsuario(request, id);
        return ResponseEntity.ok(usuario);
    }

    @Operation(description = "Deleta dados do usuario")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Dados deletados usuario")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity deletaUsuario(@PathVariable UUID id) throws UsuarioException {
        service.deletaUsuario(id);
        return ResponseEntity.noContent().build();
    }
}

package br.com.ms_usuarios.model.records;

import br.com.ms_usuarios.model.Usuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class UsuarioResponse {
    private UUID clientId;
    private String cpf;
    private String nome;
    private String telefone;
    private String login;

    public UsuarioResponse(Usuario usuario) {
        this.clientId = usuario.getClientId();
        this.cpf = usuario.getCpf();
        this.nome = usuario.getNome();
        this.telefone = usuario.getTelefone();
        this.login = usuario.getLogin();
    }
    public UsuarioResponse toClienteResponse(Usuario usuario) {

        setCpf(usuario.getCpf());
        setNome(usuario.getNome());
        setTelefone(usuario.getTelefone());
        setLogin(usuario.getLogin());
        return this;
    }
}

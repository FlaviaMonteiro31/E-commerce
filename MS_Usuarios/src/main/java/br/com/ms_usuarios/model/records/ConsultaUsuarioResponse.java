package br.com.ms_usuarios.model.records;

import br.com.ms_usuarios.model.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultaUsuarioResponse {
    private UUID clientId;
    private String cpf;
    private String nome;
    private String telefone;
    private String login;

    public ConsultaUsuarioResponse(Usuario usuario) {
        this.clientId = usuario.getClientId();
        this.cpf = usuario.getCpf();
        this.nome = usuario.getNome();
        this.telefone = usuario.getTelefone();
        this.login = usuario.getLogin();
    }
}

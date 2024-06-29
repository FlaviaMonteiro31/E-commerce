package br.com.ms_usuarios.model.records;

import br.com.ms_usuarios.model.Usuario;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequest {

    @CPF
    private String cpf;
    @NotNull
    private String nome;
    private String telefone;
    private String email;
    private String login;
    private String senha;

    public Usuario toCliente() {
        return new Usuario(this.cpf,this.nome, this.telefone, this.email, this.login);
    }

}

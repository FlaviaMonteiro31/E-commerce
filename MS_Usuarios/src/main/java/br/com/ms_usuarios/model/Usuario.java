package br.com.ms_usuarios.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name="tb_usuario")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID clientId;
    private String cpf;
    private String nome;
    private String telefone;
    private String email;
    private String login;
    private String senha;


    public Usuario(String cpf, String nome, String telefone, String email, String login) {
        this.cpf = cpf;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.login = login;
    }

    public Usuario() {
    }
}

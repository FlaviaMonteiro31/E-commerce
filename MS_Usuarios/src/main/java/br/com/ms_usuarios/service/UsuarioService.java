package br.com.ms_usuarios.service;

import br.com.ms_usuarios.exception.UsuarioException;
import br.com.ms_usuarios.model.Usuario;
import br.com.ms_usuarios.model.records.*;
import br.com.ms_usuarios.repository.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public LoginResponse login(LoginRequest request) throws UsuarioException {

        UserDetails possivelUsuario = repository.findByLogin(request.getLogin());
        if(possivelUsuario == null) {
            throw new UsuarioException("Usuario não localizado");
        }

        UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
        Authentication auth = this.authenticationManager.authenticate(usernamePassword);
        return tokenService.generateToken((Usuario) auth.getPrincipal());
    }

    public UsuarioResponse inserirUsuario(UsuarioRequest request) throws UsuarioException {

        Usuario usuario = new Usuario();
        if(!(repository.findByCpf(usuario.getCpf()).isEmpty())){
           throw new UsuarioException("O CPF informado já existe!");
        }
        String encryptedPassword = new BCryptPasswordEncoder().encode(request.getPassword());

        usuario.setCpf(request.getCpf());
        usuario.setNome(request.getNome());
        usuario.setTelefone(request.getTelefone());
        usuario.setLogin(request.getLogin());
        usuario.setPassword(encryptedPassword);
        Usuario sc = repository.save(usuario);
        return new UsuarioResponse(sc);
    }

    public Page<UsuarioResponse> listaTodosUsuarios(PageRequest pagina){
        var usuarios = repository.findAll(pagina);
        return usuarios.map(c -> new UsuarioResponse(c));
    }

    public UsuarioResponse listaUsuarioPorId(UUID usuarioId) throws UsuarioException {
        var usuario = repository.findById(usuarioId).orElseThrow(() -> new UsuarioException("Usuario não localizado."));
    return new UsuarioResponse(usuario);
    }
    public Usuario buscaUsuarioId(UUID usuarioId) throws UsuarioException {
        var usuario = repository.findById(usuarioId).orElseThrow(() -> new UsuarioException("Usuario não localizado."));
        return usuario;
    }

    public UsuarioResponse atualizaUsuario(UsuarioRequest request, UUID id) throws UsuarioException {
        try {
            Usuario usuario = repository.getOne(id);
            usuario.setCpf(request.getCpf());
            usuario.setNome(request.getNome());
            usuario.setTelefone(request.getTelefone());
            Usuario sc = repository.save(usuario);
            return new UsuarioResponse(sc);
        } catch (Exception e) {
            throw  new UsuarioException("Usuario não encontrado, id:" + id);
        }
    }

    public void deletaUsuario(UUID id) throws UsuarioException {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Entity not found with ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new UsuarioException("Violação de integridade da base");
        }

    }
    public ConsultaUsuarioResponse consultaUsuarioPorId(ConsultaUsuarioRequest request){
        Usuario u = repository.findUsuarioByIdNative(request.getClientId());
        return new ConsultaUsuarioResponse(u);
    }

}

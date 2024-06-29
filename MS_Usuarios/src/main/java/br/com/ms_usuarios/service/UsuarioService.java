package br.com.ms_usuarios.service;

import br.com.ms_usuarios.exception.UsuarioException;
import br.com.ms_usuarios.model.Usuario;
import br.com.ms_usuarios.model.records.UsuarioRequest;
import br.com.ms_usuarios.model.records.UsuarioResponse;
import br.com.ms_usuarios.repository.IUsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private IUsuarioRepository repository;

    public UsuarioResponse inserirUsuario(UsuarioRequest request) throws UsuarioException {

        Usuario usuario = new Usuario();
        if(!(repository.findByCpf(usuario.getCpf()).isEmpty())){
           throw new UsuarioException("O CPF informado já existe!");
        }

        usuario.setCpf(request.getCpf());
        usuario.setNome(request.getNome());
        usuario.setTelefone(request.getTelefone());
        usuario.setEmail(request.getEmail());
        usuario.setLogin(request.getLogin());
        usuario.setSenha(request.getSenha());
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
            usuario.setEmail(request.getEmail());
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

}

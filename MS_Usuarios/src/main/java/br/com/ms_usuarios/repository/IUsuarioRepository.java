package br.com.ms_usuarios.repository;

import br.com.ms_usuarios.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface IUsuarioRepository extends JpaRepository<Usuario, UUID> {

    Optional<Usuario> findByCpf(String cpf);

    @Query(value = "SELECT p FROM Usuario p WHERE p.clientId = :clientId")
    Usuario findUsuarioByIdNative(@Param("clientId") UUID clientId);
}

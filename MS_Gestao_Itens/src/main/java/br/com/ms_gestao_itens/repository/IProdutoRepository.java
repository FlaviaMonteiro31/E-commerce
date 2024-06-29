package br.com.ms_gestao_itens.repository;


import br.com.ms_gestao_itens.model.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IProdutoRepository extends JpaRepository<Produto, UUID> {
}

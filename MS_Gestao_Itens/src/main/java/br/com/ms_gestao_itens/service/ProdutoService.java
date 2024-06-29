package br.com.ms_gestao_itens.service;

import br.com.ms_gestao_itens.exception.ProdutoException;
import br.com.ms_gestao_itens.model.Produto;
import br.com.ms_gestao_itens.model.records.ProdutoResponse;
import br.com.ms_gestao_itens.model.records.ProdutoResquest;
import br.com.ms_gestao_itens.repository.IProdutoRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private IProdutoRepository repository;
    public ProdutoResponse inserirProduto(ProdutoResquest request) throws ProdutoException {

        Produto pe = new Produto();
        pe.setNome(request.getNome());
        pe.setDescricao(request.getDescricao());
        pe.setTamanho(request.getTamanho());
        pe.setCor(request.getCor());
        pe.setPreco(request.getPreco());
        pe.setQuantidade(request.getQuantidade());

        Produto produto = repository.save(pe);
        return new ProdutoResponse(produto);
    }

    public Page<ProdutoResponse> listaTodos(PageRequest pagina){
        var pe = repository.findAll(pagina);
        return pe.map(p -> new ProdutoResponse(p));
    }

    public ProdutoResponse listaPorId(UUID produtoId) throws ProdutoException {
        var pe = repository.findById(produtoId).orElseThrow(() -> new ProdutoException("Produto não localizado."));
        return new ProdutoResponse(pe);
    }
    public void deletaProduto(UUID id) throws ProdutoException {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Entity not found with ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new ProdutoException("Violação de integridade da base");
        }
    }

    public ProdutoResponse atualizaProduto(ProdutoResquest request, UUID id) throws ProdutoException {
        try {
            Produto pe = repository.getOne(id);
            pe.setNome(request.getNome());
            pe.setDescricao(request.getDescricao());
            pe.setTamanho(request.getTamanho());
            pe.setCor(request.getCor());
            pe.setPreco(request.getPreco());
            Produto produto = repository.save(pe);
            return new ProdutoResponse(produto);
        } catch (Exception e) {
            throw  new ProdutoException("Produto não encontrado, id:" + id);
        }
    }
    public void removerEstoque(UUID id, BigDecimal quantidade) throws ProdutoException {
        Produto produtoLocalizado = repository.findById(id).orElseThrow(() -> new ProdutoException("Produto não localizado"));
        produtoLocalizado.setQuantidade(getFinalEstoqueQuantidade(produtoLocalizado.getQuantidade(), quantidade));
        repository.save(produtoLocalizado);
    }

    private BigDecimal getFinalEstoqueQuantidade(BigDecimal quantiadadeDisponivel, BigDecimal quantidadeSolicitada) {
        final BigDecimal novaQuantidade = quantiadadeDisponivel.subtract(quantidadeSolicitada);
        if (novaQuantidade.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalStateException("Estoque insuficiente para a compra desejada.");
        } else {
            return novaQuantidade;
        }
    }

}

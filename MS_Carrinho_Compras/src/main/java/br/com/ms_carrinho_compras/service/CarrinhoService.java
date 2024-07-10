package br.com.ms_carrinho_compras.service;

import br.com.ms_carrinho_compras.exception.CarrinhoException;
import br.com.ms_carrinho_compras.integration.gateway.ConsultaProdutoGateway;
import br.com.ms_carrinho_compras.model.Carrinho;
import br.com.ms_carrinho_compras.model.Item;
import br.com.ms_carrinho_compras.model.records.*;
import br.com.ms_carrinho_compras.repository.IItemRepository;
import br.com.ms_carrinho_compras.repository.ICarrinhoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CarrinhoService {

    @Autowired
    private ICarrinhoRepository repository;
    @Autowired
    private IItemRepository itemRepository;
    @Autowired
    private ConsultaProdutoGateway gateway;
    public CarrinhoResponse criaCarrinho(CarrinhoRequest request) throws CarrinhoException {

        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(request.getUsuario());
        Carrinho compras = repository.save(carrinho);

        List<Item> itens = request.getItens().stream().map(itemRequest -> {
            Item item = new Item();
            UUID id = itemRequest.getIdproduto();
            BigDecimal quantidade = itemRequest.getQuantidade();

            if (id!= null) {
                ConsultaProdutoResponse r = gateway.consultaProduto(new GenericMessage<>(new ConsultaProdutoRequest(id)));
                BigDecimal valorItens = r.getPreco().multiply(quantidade);
                item.setIdproduto(id);
                item.setQuantidade(itemRequest.getQuantidade());
                item.setCarrinho(compras);
                item.setValoritem(valorItens);
            } else {
                throw new CarrinhoException("ID do produto deve ser informado.");
            }
            return item;


        }).collect(Collectors.toList());

        itemRepository.saveAll(itens);
        compras.setItens(itens);

        return new CarrinhoResponse(compras);
    }
     public CarrinhoResponse listaCarrinhoPorIdUsuario(UUID usuarioId) throws CarrinhoException {
         return new CarrinhoResponse(repository.findByUsuario(usuarioId));
    }
    public void deletaCarrinho(UUID id) throws CarrinhoException {
        try {
            Carrinho carrinho = repository.getOne(id);
            repository.deleteById(id);
            itemRepository.deleteByCarrinho(carrinho);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Entity not found with ID: " + id);
        } catch (DataIntegrityViolationException e) {
            throw new CarrinhoException("Violação de integridade da base");
        }
    }

    public CarrinhoResponse adicionaItenNovoCarrinho(CarrinhoRequest request, UUID id) throws CarrinhoException {

        Carrinho carrinho = repository.getOne(id);
        List<Item> itensExistentes = repository.findByIdcarrinho(id);
        List<Item> itensNovos = request.getItens();

        for (Item itemNovo : itensNovos) {
            boolean itemExistenteEncontrado = false;

            for (Item itemExistente : itensExistentes) {
                if (itemNovo.getIdproduto().equals(itemExistente.getIdproduto())) {
                    itemExistenteEncontrado = true;
                    if (!itemNovo.getQuantidade().equals(itemExistente.getQuantidade())) {
                        itemExistente.setQuantidade(itemNovo.getQuantidade());
                    }
                    break;
                }
            }

            if (!itemExistenteEncontrado) {
                itemNovo.setCarrinho(carrinho);
                itensExistentes.add(itemNovo);
            }
        }

        itemRepository.saveAll(itensExistentes);
        carrinho.setItens(itensExistentes);
        repository.save(carrinho);
        return new CarrinhoResponse(carrinho);
    }

    public CarrinhoResponse removeItemCarrinho(CarrinhoRequest request, UUID id) throws CarrinhoException {

        Carrinho carrinho = repository.getOne(id);
        List<Item> itensExistentes = repository.findByIdcarrinho(id);

        Set<UUID> idsRemover = request.getItens().stream()
                .map(Item::getIditem)
                .collect(Collectors.toSet());

        idsRemover.forEach(item -> {
          itemRepository.deleteByIditem(item);
        });

        Set<UUID> idsProdutosRemover = request.getItens().stream()
                .map(Item::getIdproduto)
                .collect(Collectors.toSet());

        List<Item> itensAtualizados = new ArrayList<>();
        itensExistentes.forEach(item -> {
            if (!idsProdutosRemover.contains(item.getIdproduto())) {
                itensAtualizados.add(item);
            }
        });

        itemRepository.saveAll(itensAtualizados);
        carrinho.setItens(itensAtualizados);
        repository.save(carrinho);

        return new CarrinhoResponse(carrinho);
    }

    public ConsultaProdutoResponse consulta(@Valid ConsultaProdutoRequest request){
        return gateway.consultaProduto(new GenericMessage<>(request));
    }

}

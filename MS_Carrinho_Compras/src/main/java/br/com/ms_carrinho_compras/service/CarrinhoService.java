package br.com.ms_carrinho_compras.service;

import br.com.ms_carrinho_compras.exception.CarrinhoException;
import br.com.ms_carrinho_compras.integration.gateway.ConsultaProdutoGateway;
import br.com.ms_carrinho_compras.integration.gateway.ConsultaUsuarioGateway;
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
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CarrinhoService {

    @Autowired
    private ICarrinhoRepository repository;
    @Autowired
    private IItemRepository itemRepository;
    @Autowired
    private ConsultaProdutoGateway produtoGateway;
    @Autowired
    private ConsultaUsuarioGateway usuarioGateway;

    public CarrinhoResponse criaCarrinho(CarrinhoRequest request) throws CarrinhoException {

        try {
            ConsultaUsuarioResponse u = usuarioGateway.consultaUsuario(new GenericMessage<>(new ConsultaUsuarioRequest(request.getUsuario())));
            System.out.println("usuario : " + u.getClientId() + " nome: " + u.getNome());
        }catch(Exception e){
            throw new CarrinhoException("O usuario informado não existe.");
        }

        BigDecimal[] total = {BigDecimal.ZERO};
        BigDecimal[] valorItens = {BigDecimal.ZERO};
        Carrinho carrinho = new Carrinho();
        carrinho.setUsuario(request.getUsuario());
        Carrinho compras = repository.save(carrinho);

        List<Item> itens = request.getItens().stream().map(itemRequest -> {
            Item item = new Item();
            UUID id = itemRequest.getIdproduto();
            BigDecimal quantidade = itemRequest.getQuantidade();

            if (id!= null) {
                try {
                    ConsultaProdutoResponse p = produtoGateway.consultaProduto(new GenericMessage<>(new ConsultaProdutoRequest(id)));
                    valorItens[0] = p.getPreco().multiply(quantidade);
                }catch(Exception e){
                    throw new CarrinhoException("O produto informado não foi localizado.");
                }

                item.setIdproduto(id);
                item.setQuantidade(itemRequest.getQuantidade());
                item.setCarrinho(compras);
                item.setValoritem(valorItens[0]);
                total[0] = total[0].add(valorItens[0]);
            } else {
                throw new CarrinhoException("ID do produto deve ser informado.");
            }
            return item;


        }).collect(Collectors.toList());

        itemRepository.saveAll(itens);
        compras.setItens(itens);
        compras.setValorCarrinho(total[0]);
        repository.save(carrinho);
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
    public CarrinhoResponse adicionaItenNovoCarrinho(AlteraCarrinhoRequest request, UUID id) throws CarrinhoException {

        Carrinho carrinho  = repository.getReferenceById(id);
        BigDecimal valorCarrinho = repository.findValorCarrinhoById(id);
        System.out.println("Valor total do carrinho atual: " + valorCarrinho);
        BigDecimal[] total = {valorCarrinho};
        BigDecimal[] valorItens = {BigDecimal.ZERO};

        List<Item> itens = request.getItens().stream().map(itemRequest -> {
            Item item = new Item();
            BigDecimal quantidade = itemRequest.getQuantidade();
            UUID idproduto = itemRequest.getIdproduto();
            try {
                ConsultaProdutoResponse p = produtoGateway.consultaProduto(new GenericMessage<>(new ConsultaProdutoRequest(idproduto)));
                valorItens[0] = p.getPreco().multiply(quantidade);
            }catch(Exception e){
                throw new CarrinhoException("O produto informado não foi localizado.");
            }
          item.setIdproduto(idproduto);
          item.setCarrinho(carrinho);
          item.setValoritem(valorItens[0]);
          item.setQuantidade(quantidade);
          total[0].add(valorItens[0]);
          itemRepository.save(item);

            return item;
        }).collect(Collectors.toList());

        carrinho.setValorCarrinho(total[0]);

        return new CarrinhoResponse(carrinho);
    }








//    public CarrinhoResponse removeItemCarrinho(CarrinhoRequest request, UUID id) throws CarrinhoException {
//
//        Carrinho carrinho = repository.getOne(id);
//        List<Item> itensExistentes = repository.findByIdcarrinho(id);
//
//        Set<UUID> idsRemover = request.getItens().stream()
//                .map(Item::getIditem)
//                .collect(Collectors.toSet());
//
//        idsRemover.forEach(item -> {
//          itemRepository.deleteByIditem(item);
//        });
//
//        Set<UUID> idsProdutosRemover = request.getItens().stream()
//                .map(Item::getIdproduto)
//                .collect(Collectors.toSet());
//
//        List<Item> itensAtualizados = new ArrayList<>();
//        itensExistentes.forEach(item -> {
//            if (!idsProdutosRemover.contains(item.getIdproduto())) {
//                itensAtualizados.add(item);
//            }
//        });
//
//        itemRepository.saveAll(itensAtualizados);
//        carrinho.setItens(itensAtualizados);
//        repository.save(carrinho);
//
//        return new CarrinhoResponse(carrinho);
//    }

    public ConsultaProdutoResponse consulta(@Valid ConsultaProdutoRequest request){
        return produtoGateway.consultaProduto(new GenericMessage<>(request));
    }
    @Transactional
    public ConsultaCarrinhoResponse consultaCarrinhoPorId(ConsultaCarrinhoRequest request) throws CarrinhoException {
        return new ConsultaCarrinhoResponse(repository.findCarrinhoById(request.getIdcarrinho()));
    }
}

package br.com.ms_pagamentos.service;

import br.com.ms_pagamentos.enums.FormaPagamentoEnum;
import br.com.ms_pagamentos.enums.MeioPagamentoEnum;
import br.com.ms_pagamentos.enums.StatusPedidoEnum;
import br.com.ms_pagamentos.exception.PagamentoException;
import br.com.ms_pagamentos.model.*;
import br.com.ms_pagamentos.model.records.PagamentoRequest;
import br.com.ms_pagamentos.model.records.PagamentoResponse;
import br.com.ms_pagamentos.model.records.PedidoRequest;
import br.com.ms_pagamentos.model.records.PedidoResponse;
import br.com.ms_pagamentos.repository.IEnderecoRepository;
import br.com.ms_pagamentos.repository.IItemRepository;
import br.com.ms_pagamentos.repository.IPagamentoRepository;
import br.com.ms_pagamentos.repository.IPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PagamentoService{

    @Autowired
    private IPagamentoRepository repository;
    @Autowired
    private IPedidoRepository pedidoRepository;
    @Autowired
    private IItemRepository itemRepository;
    @Autowired
    private IEnderecoRepository enderecoRepository;
    private PixGenerator pixGenerator;
    private StatusPedidoEnum status;
    public PagamentoResponse efetuaPagamento(PagamentoRequest request) throws PagamentoException {
        Pagamento pagamento = new Pagamento();

        if(request.getFormapagamento().equals(FormaPagamentoEnum.AVISTA)
            && request.getMeiopagamento().equals(MeioPagamentoEnum.PIX)){

            Pagamento pag = new Pagamento();
            pag.setFormapagamento(request.getFormapagamento());
            pag.setMeiopagamento(request.getMeiopagamento());
            pag.setPixCopiaCola(getPix());
            pag.setStatusPagamento(StatusPedidoEnum.AGUARDANDO_PAGAMENTO_PIX.toString());
            pagamento = repository.save(pag);
        } else if(request.getMeiopagamento().equals(MeioPagamentoEnum.CARTAO_CREDITO)
            || request.getMeiopagamento().equals(MeioPagamentoEnum.CARTAO_DEBITO) ){
            Pagamento pag = new Pagamento();
            pag.setFormapagamento(request.getFormapagamento());
            pag.setMeiopagamento(request.getMeiopagamento());
            pag.setPixCopiaCola("");
            pag.setNumeroCartao(request.getNumeroCartao());
            pag.setDataValidadeCartao(request.getDataValidadeCartao());
            pag.setCodigoSeguranca(request.getCodigoSegunca());
            pag.setTitularCartao(request.getTitularCartao());
            pag.setStatusPagamento(StatusPedidoEnum.AGUARDANDO_CONFIRMACAO_DO_PAGAMENTO.toString());
            pagamento = repository.save(pag);
        }
        return new PagamentoResponse(pagamento);
    }

    public PedidoResponse gerarPedido(PedidoRequest request) throws PagamentoException {

        Endereco endereco = new Endereco();
        endereco.setCep(request.getEnderecoEntrega().getCep());
        endereco.setCidade(request.getEnderecoEntrega().getCidade());
        endereco.setEstado(request.getEnderecoEntrega().getEstado());
        endereco.setLogradouro(request.getEnderecoEntrega().getLogradouro());
        endereco.setNumero(request.getEnderecoEntrega().getNumero());
        endereco.setComplemento(request.getEnderecoEntrega().getComplemento());
        endereco = enderecoRepository.save(endereco);

        Pedido pedido = new Pedido();
        pedido.setCliente(request.getCliente());
        pedido.setStatus(status.PROCESSANDO_PEDIDO.toString());
        pedido.setEnderecoEntrega(endereco);

        Pedido p = pedidoRepository.save(pedido);

        List<Item> itens = request.getItens().stream().map(itemRequest -> {
            //gateway.removerEstoque(new GenericMessage<>(new RemoverEstoqueRequest(itemRequest.getIdproduto(), itemRequest.getQuantidade())));

            Item item = new Item();
            item.setIdproduto(itemRequest.getIdproduto());
            item.setQuantidade(itemRequest.getQuantidade());
            item.setPedido(p);
            return item;
        }).collect(Collectors.toList());
        itemRepository.saveAll(itens);
           p.setItens(itens);
        return new PedidoResponse(pedido);
    }

    public String getPix(){
        Pix pix = new Pix();
        pix.setChavePix("56324348000199");
        pix.setNomeRecebedor("ECOMMERCE BRASIL LTDA");
        pix.setCidadeRecebedor("UDIA");
        pix.setIdentificadorTransacao("123456");
        pix.setValor(50.00);

        String pixCode = pixGenerator.generatePixCode(pix);
        return pixCode;
    }

}

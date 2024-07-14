package br.com.ms_pagamentos.service;

import br.com.ms_pagamentos.enums.FormaPagamentoEnum;
import br.com.ms_pagamentos.enums.MeioPagamentoEnum;
import br.com.ms_pagamentos.enums.StatusPedidoEnum;
import br.com.ms_pagamentos.exception.PagamentoException;
import br.com.ms_pagamentos.integration.gateway.ConsultaCarrinhoGateway;
import br.com.ms_pagamentos.model.*;
import br.com.ms_pagamentos.model.records.*;
import br.com.ms_pagamentos.repository.IEnderecoRepository;
import br.com.ms_pagamentos.repository.IItemRepository;
import br.com.ms_pagamentos.repository.IPagamentoRepository;
import br.com.ms_pagamentos.repository.IPedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.GenericMessage;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;
import java.util.UUID;
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
    @Autowired
    private ConsultaCarrinhoGateway carrinhoGateway;
    private PixGenerator pixGenerator;
    private StatusPedidoEnum status;
    public PagamentoResponse efetuaPagamento(PagamentoRequest request) throws PagamentoException {

        BigDecimal[] valorPagamento = {BigDecimal.ZERO};
        BigDecimal[] valorDaParcela = {BigDecimal.ZERO};

        try {
            ConsultaCarrinhoResponse p = carrinhoGateway.consultaCarrinho(new GenericMessage<>(new ConsultaCarrinhoRequest(request.getIdcarrinho())));
            if (p.getValorCarrinho().compareTo(BigDecimal.ZERO) > 0) {
                valorPagamento[0] = p.getValorCarrinho();
            }
        }catch(Exception e){
            throw new PagamentoException("O carrinho informado não existe.");
        }

        Pagamento pagamento = new Pagamento();

        if(request.getFormapagamento().equals(FormaPagamentoEnum.AVISTA)
            && request.getMeiopagamento().equals(MeioPagamentoEnum.PIX)){

            Pagamento pag = new Pagamento();
            pag.setFormapagamento(request.getFormapagamento());
            pag.setMeiopagamento(request.getMeiopagamento());
            pag.setPixCopiaCola(getPix());
            pag.setStatusPagamento(StatusPedidoEnum.AGUARDANDO_PAGAMENTO_PIX.toString());
            pag.setValorCarrinho(valorPagamento[0]);
            pagamento = repository.save(pag);

        } else if(request.getMeiopagamento().equals(MeioPagamentoEnum.CARTAO_CREDITO)
            || request.getMeiopagamento().equals(MeioPagamentoEnum.CARTAO_DEBITO) ){

            if(!request.getNumeroCartao().isEmpty() && !request.getDataValidadeCartao().toString().isEmpty() && !request.getTitularCartao().isEmpty()
            ) {
                if (request.getFormapagamento().equals(FormaPagamentoEnum.PARCELAMENTO_2X)){
                   valorDaParcela[0] = valorPagamento[0].divide(new BigDecimal(2));

                }

                Pagamento pag = new Pagamento();
                pag.setFormapagamento(request.getFormapagamento());
                pag.setMeiopagamento(request.getMeiopagamento());
                pag.setPixCopiaCola("");
                pag.setNumeroCartao(request.getNumeroCartao());
                pag.setDataValidadeCartao(request.getDataValidadeCartao());
                pag.setCodigoSeguranca(request.getCodigoSegunca());
                pag.setTitularCartao(request.getTitularCartao());
                pag.setValorCarrinho(valorPagamento[0]);
                pag.setValorParcelaCartao(valorDaParcela[0]);
                pag.setStatusPagamento(StatusPedidoEnum.AGUARDANDO_CONFIRMACAO_DO_PAGAMENTO.toString());
                pagamento = repository.save(pag);

            } else {
                throw new PagamentoException("Os dados do cartão é obrigatório.");
            }
        }
        //gerarPedido(request);

        return new PagamentoResponse(pagamento);
    }

//    public void gerarPedido(PagamentoRequest request) throws PagamentoException {
//
//        Endereco endereco = new Endereco();
//        endereco.setCep(request.get);
//        endereco.setCidade(request.getEnderecoEntrega().getCidade());
//        endereco.setEstado(request.getEnderecoEntrega().getEstado());
//        endereco.setLogradouro(request.getEnderecoEntrega().getLogradouro());
//        endereco.setNumero(request.getEnderecoEntrega().getNumero());
//        endereco.setComplemento(request.getEnderecoEntrega().getComplemento());
//        endereco = enderecoRepository.save(endereco);
//
//        Pedido pedido = new Pedido();
//        pedido.setCliente(request.getCliente());
//        pedido.setStatus(status.PROCESSANDO_PEDIDO.toString());
//        pedido.setEnderecoEntrega(endereco);
//
//        Pedido p = pedidoRepository.save(pedido);
//
//        List<Item> itens = request.getItens().stream().map(itemRequest -> {
//            //gateway.removerEstoque(new GenericMessage<>(new RemoverEstoqueRequest(itemRequest.getIdproduto(), itemRequest.getQuantidade())));
//
//            Item item = new Item();
//            item.setIdproduto(itemRequest.getIdproduto());
//            item.setQuantidade(itemRequest.getQuantidade());
//            item.setPedido(p);
//            return item;
//        }).collect(Collectors.toList());
//        itemRepository.saveAll(itens);
//           p.setItens(itens);
//        return new PedidoResponse(pedido);
//    }

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

    public static Boolean pagamentoAprovado(){
        Random random = new Random();
        int aprovado = random.nextInt(1,3);
        System.out.println(aprovado);

        if(aprovado == 2){
            return false;
        }
        return true;
    }

    public PagamentoResponse retornaStatusPagamento(UUID id) {

        Pagamento pag = repository.getOne(id);
        if(pagamentoAprovado() == true){
            pag.setStatusPagamento(StatusPedidoEnum.PAGAMENTO_APROVADO.toString());
        } else {
            pag.setStatusPagamento(StatusPedidoEnum.PAGAMENTO_RECUSADO.toString());
        }
        Pagamento pagamento = repository.save(pag);

        return new PagamentoResponse(pagamento);
    }
}

package br.com.henriquedev.codechella.service;

import br.com.henriquedev.codechella.controller.request.CompraRequest;
import br.com.henriquedev.codechella.controller.response.IngressoResponse;
import br.com.henriquedev.codechella.entity.Ingresso;
import br.com.henriquedev.codechella.entity.Venda;
import br.com.henriquedev.codechella.mapper.IngressoMapper;
import br.com.henriquedev.codechella.repository.IngressoRepository;
import br.com.henriquedev.codechella.repository.VendaRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class IngressoService {

    private final IngressoRepository repository;
    private final VendaRepository vendaRepository;

    public Flux<Ingresso> findAll(){
        return repository.findAll();
    }

    public Mono<Ingresso> findById(Long id){
        return repository.findById(id);
    }

    public Mono<Ingresso> save(Ingresso ingresso){
        return repository.save(ingresso);
    }

    public Mono<Ingresso> update(Ingresso ingresso, Long id){
        return repository.findById(id)
                .flatMap(existingIngresso ->{
                    existingIngresso.setEventoId(ingresso.getEventoId());
                    existingIngresso.setTipo(ingresso.getTipo());
                    existingIngresso.setValor(ingresso.getValor());
                    existingIngresso.setTotal(ingresso.getTotal());
                    return repository.save(existingIngresso);
                })
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    public Mono<Void> delete(Long id){
        return repository.findById(id)
                .flatMap(repository::delete);
    }

    @Transactional
    public Mono<IngressoResponse> comprar(CompraRequest compra) {
        return repository.findById(compra.ingressoId())
                .flatMap(ingresso -> {
                    Venda venda = new Venda();
                    venda.setIngressoId(ingresso.getId());
                    venda.setTotal(compra.total());
                    return vendaRepository.save(venda).then(Mono.defer(() -> {
                        ingresso.setTotal(ingresso.getTotal() - compra.total());
                        return repository.save(ingresso);
                    }));
                }).map(IngressoMapper::toIngressoResponse);
    }


}

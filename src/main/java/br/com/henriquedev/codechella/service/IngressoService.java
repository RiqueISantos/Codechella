package br.com.henriquedev.codechella.service;

import br.com.henriquedev.codechella.entity.Ingresso;
import br.com.henriquedev.codechella.repository.IngressoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class IngressoService {

    private final IngressoRepository repository;

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

}

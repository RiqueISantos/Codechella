package br.com.henriquedev.codechella.service;

import br.com.henriquedev.codechella.entity.Evento;
import br.com.henriquedev.codechella.repository.EventoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class EventoService {

    private final EventoRepository repository;

    public Flux<Evento> findAll(){
        return repository.findAll();
    }

    public Mono<Evento> findById(Long id){
        return repository.findById(id);

    }

    public Mono<Evento> save(Evento evento){
        return repository.save(evento);
    }

    public Mono<Void> delete(Long id){
        return repository.findById(id)
                .flatMap(repository::delete);
    }

}

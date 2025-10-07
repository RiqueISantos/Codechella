package br.com.henriquedev.codechella.service;

import br.com.henriquedev.codechella.controller.response.EventoResponse;
import br.com.henriquedev.codechella.entity.Evento;
import br.com.henriquedev.codechella.enums.TipoEvento;
import br.com.henriquedev.codechella.mapper.EventoMapper;
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

    public Flux<EventoResponse> obterPorTipo(String tipo) {
        TipoEvento tipoEvento = TipoEvento.valueOf(tipo.toUpperCase());
        return repository.findByTipo(tipoEvento)
                .map(EventoMapper::toEventoResponse);
    }
}

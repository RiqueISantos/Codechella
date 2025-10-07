package br.com.henriquedev.codechella.repository;

import br.com.henriquedev.codechella.entity.Evento;
import br.com.henriquedev.codechella.enums.TipoEvento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;

public interface EventoRepository extends ReactiveCrudRepository<Evento, Long> {
    Flux<Evento> findByTipo(TipoEvento tipoEvento);
}

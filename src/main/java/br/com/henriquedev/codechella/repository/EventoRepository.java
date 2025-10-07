package br.com.henriquedev.codechella.repository;

import br.com.henriquedev.codechella.entity.Evento;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EventoRepository extends ReactiveCrudRepository<Evento, Long> {
}

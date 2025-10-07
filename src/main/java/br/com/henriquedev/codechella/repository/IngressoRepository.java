package br.com.henriquedev.codechella.repository;

import br.com.henriquedev.codechella.entity.Ingresso;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IngressoRepository extends R2dbcRepository<Ingresso, Long> {
}

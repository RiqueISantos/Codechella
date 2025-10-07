package br.com.henriquedev.codechella.repository;

import br.com.henriquedev.codechella.entity.Venda;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface VendaRepository extends R2dbcRepository<Venda, Long> {
}

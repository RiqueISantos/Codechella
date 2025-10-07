package br.com.henriquedev.codechella.controller.request;

import br.com.henriquedev.codechella.enums.TipoEvento;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EventoRequest(TipoEvento tipo,
                            String nome,
                            LocalDate data,
                            String descricao) {
}

package br.com.henriquedev.codechella.controller.response;

import br.com.henriquedev.codechella.enums.TipoEvento;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record EventoResponse(Long id,
                             TipoEvento tipo,
                             String nome,
                             LocalDate data,
                             String descricao) {
}

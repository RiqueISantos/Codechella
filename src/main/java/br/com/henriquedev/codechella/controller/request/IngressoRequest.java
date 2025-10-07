package br.com.henriquedev.codechella.controller.request;

import br.com.henriquedev.codechella.enums.TipoIngresso;
import lombok.Builder;

@Builder
public record IngressoRequest(Long eventoId,
                              TipoIngresso tipo,
                              Double valor,
                              int total) {
}

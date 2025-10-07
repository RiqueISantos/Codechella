package br.com.henriquedev.codechella.controller.response;

import br.com.henriquedev.codechella.enums.TipoIngresso;
import lombok.Builder;

@Builder
public record IngressoResponse(Long id,
                               Long eventoId,
                               TipoIngresso tipo,
                               Double valor,
                               int total) {
}

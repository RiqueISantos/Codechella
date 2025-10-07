package br.com.henriquedev.codechella.mapper;

import br.com.henriquedev.codechella.controller.request.IngressoRequest;
import br.com.henriquedev.codechella.controller.response.IngressoResponse;
import br.com.henriquedev.codechella.entity.Ingresso;
import lombok.experimental.UtilityClass;

@UtilityClass
public class IngressoMapper {

    public static Ingresso toIngresso(IngressoRequest request){
        return Ingresso
                .builder()
                .eventoId(request.eventoId())
                .tipo(request.tipo())
                .valor(request.valor())
                .total(request.total())
                .build();
    }

    public static IngressoResponse toIngressoResponse(Ingresso ingresso){
        return IngressoResponse
                .builder()
                .id(ingresso.getId())
                .eventoId(ingresso.getEventoId())
                .tipo(ingresso.getTipo())
                .valor(ingresso.getValor())
                .total(ingresso.getTotal())
                .build();
    }
}

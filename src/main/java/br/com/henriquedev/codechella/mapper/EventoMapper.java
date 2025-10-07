package br.com.henriquedev.codechella.mapper;

import br.com.henriquedev.codechella.entity.Evento;
import br.com.henriquedev.codechella.controller.request.EventoRequest;
import br.com.henriquedev.codechella.controller.response.EventoResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class EventoMapper {

    public static Evento toEvento (EventoRequest request){
        return Evento
                .builder()
                .nome(request.nome())
                .tipo(request.tipo())
                .data(request.data())
                .descricao(request.descricao())
                .build();
    }

    public static EventoResponse toEventoResponse(Evento evento){
        return EventoResponse
                .builder()
                .id(evento.getId())
                .nome(evento.getNome())
                .tipo(evento.getTipo())
                .data(evento.getData())
                .descricao(evento.getDescricao())
                .build();
    }
}

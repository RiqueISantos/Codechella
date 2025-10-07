package br.com.henriquedev.codechella.controller;

import br.com.henriquedev.codechella.controller.request.EventoRequest;
import br.com.henriquedev.codechella.controller.response.EventoResponse;
import br.com.henriquedev.codechella.entity.Evento;
import br.com.henriquedev.codechella.mapper.EventoMapper;
import br.com.henriquedev.codechella.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService service;

    @GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoResponse> pegarEventos(){
        return service.findAll()
                .map(EventoMapper::toEventoResponse);
    }

    @GetMapping("/{id}")
    public Mono<EventoResponse> pegarEventoPorId(@PathVariable Long id){
        return service.findById(id)
                .map(EventoMapper::toEventoResponse)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<EventoResponse> enviarEvento(@RequestBody EventoRequest eventoRequest){
        Evento saveEvento = EventoMapper.toEvento(eventoRequest);
        return service.save(saveEvento)
                .map(EventoMapper::toEventoResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deletarEvento(@PathVariable Long id){
            return service.delete(id)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

}

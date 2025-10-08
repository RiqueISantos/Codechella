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
import reactor.core.publisher.Sinks;

import java.time.Duration;

@RestController
@RequestMapping("/eventos")
public class EventoController {

    private final EventoService service;
    private final Sinks.Many<EventoResponse> eventoSynk;

    public EventoController(EventoService service){
        this.service = service;
        this.eventoSynk = Sinks.many().multicast().onBackpressureBuffer();
    }


    @GetMapping()
    public Flux<EventoResponse> pegarEventos(){
        return service.findAll()
                .map(EventoMapper::toEventoResponse);
    }

    @GetMapping(value = "/categoria/{tipo}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<EventoResponse> obterPorTipo(@PathVariable String tipo){
        return Flux.merge(service.obterPorTipo(tipo), eventoSynk.asFlux())
                .delayElements(Duration.ofSeconds(4));
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
                .doOnSuccess(e -> eventoSynk.tryEmitNext(EventoMapper.toEventoResponse(e)))
                .map(EventoMapper::toEventoResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deletarEvento(@PathVariable Long id){
            return service.delete(id)
                    .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

}

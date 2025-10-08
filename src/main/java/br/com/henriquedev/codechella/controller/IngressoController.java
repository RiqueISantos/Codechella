package br.com.henriquedev.codechella.controller;

import br.com.henriquedev.codechella.controller.request.CompraRequest;
import br.com.henriquedev.codechella.controller.request.IngressoRequest;
import br.com.henriquedev.codechella.controller.response.IngressoResponse;
import br.com.henriquedev.codechella.entity.Ingresso;
import br.com.henriquedev.codechella.mapper.IngressoMapper;
import br.com.henriquedev.codechella.service.IngressoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

@RestController
@RequestMapping("/ingressos")
public class IngressoController {

    private final IngressoService service;
    private final Sinks.Many<IngressoResponse> ingressoSink;

    public IngressoController(IngressoService service) {
        this.service = service;
        this.ingressoSink = Sinks.many().multicast().onBackpressureBuffer();
    }

    @GetMapping()
    public Flux<IngressoResponse> listarIngressos(){
        return service.findAll()
                .map(IngressoMapper::toIngressoResponse);
    }

    @GetMapping("/{id}")
    public Mono<IngressoResponse> listarIngressoPorId(@PathVariable Long id){
        return service.findById(id)
                .map(IngressoMapper::toIngressoResponse)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<IngressoResponse> cadastrarIngresso (@RequestBody IngressoRequest request){
        Ingresso saveIngresso = IngressoMapper.toIngresso(request);
        return service.save(saveIngresso)
                .map(IngressoMapper::toIngressoResponse);
    }

    @PutMapping("/{id}")
    public Mono<IngressoResponse> alterarIngresso(@RequestBody IngressoRequest request, @PathVariable Long id){
        Ingresso optIngresso = IngressoMapper.toIngresso(request);
        return service.update(optIngresso, id)
                .map(IngressoMapper::toIngressoResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> deletarIngresso(@PathVariable Long id){
        return service.delete(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND)));
    }

    @PostMapping("/compra")
    public Mono<IngressoResponse> comprar(@RequestBody CompraRequest request) {
        return service.comprar(request).doOnSuccess(ingressoSink::tryEmitNext);
    }

    @GetMapping(value = "/{id}/disponivel", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<IngressoResponse> totalDisponivel(@PathVariable Long id) {
        return Flux.merge(service.findById(id).map(IngressoMapper::toIngressoResponse), ingressoSink.asFlux());
    }
}

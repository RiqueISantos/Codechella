package br.com.henriquedev.codechella.controller;

import br.com.henriquedev.codechella.controller.request.IngressoRequest;
import br.com.henriquedev.codechella.controller.response.IngressoResponse;
import br.com.henriquedev.codechella.entity.Ingresso;
import br.com.henriquedev.codechella.mapper.IngressoMapper;
import br.com.henriquedev.codechella.service.IngressoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/ingressos")
@RequiredArgsConstructor
public class IngressoController {

    private final IngressoService service;

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

}

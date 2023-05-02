package com.ada.tech.controller;

import com.ada.tech.entity.Mercado;
import com.ada.tech.entity.Moeda;
import com.ada.tech.service.MercadoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/mercado")
@Slf4j
@RequiredArgsConstructor
public class MercadoController {

    private final MercadoService service;

    @PostMapping
    public Mono<ResponseEntity<Mercado>> save(@RequestBody Mercado mercado) {
        return service.saveMercado(mercado)
                .map(ResponseEntity::ok)
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @GetMapping
    public Mono<ResponseEntity<Flux<Mercado>>> findAll() {
        return service.findAllMercados()
                .collectList()
                .filter(mercados -> !mercados.isEmpty())
                .map(mercados -> ResponseEntity.ok().body(Flux.fromIterable(mercados)))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Mercado>> findById(@PathVariable String id) {
        return service.findMercadoById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Mercado>> update(@RequestBody Mercado mercado, @PathVariable String id) {
        return service.updateMercado(id, mercado)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable String id) {
        return service.deleteMercado(id)
                .then(Mono.just(ResponseEntity.ok().<Void>build()))
                .defaultIfEmpty(ResponseEntity.notFound().build())
                .onErrorResume(e -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()));
    }

    @GetMapping("/search")
    public Mono<ResponseEntity<Flux<Moeda>>> search(@RequestParam("moeda") String moeda){
        return service.cotacao(moeda)
                .collectList()
                .filter(cotacoes -> !cotacoes.isEmpty())
                .map(cotacoes -> ResponseEntity.ok().body(Flux.fromIterable(cotacoes)))
                .defaultIfEmpty(ResponseEntity.noContent().build());
    }
}

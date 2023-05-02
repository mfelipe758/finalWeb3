package com.ada.tech.service;

import com.ada.tech.entity.Mercado;
import com.ada.tech.entity.Moeda;
import com.ada.tech.repository.MercadoRepository;
import com.ada.tech.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MercadoService {

    private final MercadoRepository repository;

    public Mono<Mercado> saveMercado(Mercado mercado) {
        return repository.save(mercado);
    }

    public Flux<Mercado> findAllMercados() {
        return repository.findAll();
    }

    public Mono<Mercado> updateMercado(String id, Mercado mercado) {
        return repository.findById(id)
                .flatMap(existingMercado -> {
                    existingMercado.setNome(mercado.getNome());
                    existingMercado.setMoeda(mercado.getMoeda());
                    existingMercado.setSaldo(mercado.getSaldo());
                    return repository.save(existingMercado);
                });
    }

    public Mono<ResponseEntity<Object>> deleteMercado(String id) {
        return repository.findById(id)
                .flatMap(existingMercado -> {
                    return repository.deleteById(id)
                            .thenReturn(ResponseEntity.ok().build());
                })
                .switchIfEmpty(Mono.error(new NotFoundException("Mercado não encontrado com id " + id)))
                .onErrorResume(error -> Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Erro ao deletar mercado com id " + id + ": " + error.getMessage())));
    }

    public Mono<Mercado> findMercadoById(String id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(new NotFoundException("Mercado não encontrado com id " + id)));
    }

    public Flux<Moeda> cotacao(String moeda) {
        WebClient webClient = WebClient.create("https://economia.awesomeapi.com.br");
        return Mono.fromCallable(() ->
                        webClient.get()
                                .uri("/" + moeda + "/")
                                .retrieve()
                                .bodyToFlux(Moeda.class)
                                .onErrorMap(throwable -> new NotFoundException("Erro ao chamar API externa")))
                .flatMapMany(Function.identity());
    }
}

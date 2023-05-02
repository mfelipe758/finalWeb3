package com.ada.tech.repository;

import com.ada.tech.entity.Mercado;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
public interface MercadoRepository extends ReactiveMongoRepository<Mercado, String> {
}

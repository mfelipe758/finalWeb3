package tech.ada.pagamento.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import tech.ada.pagamento.model.Transacao;

@EnableMongoRepositories
public interface TransacaoRepository extends ReactiveMongoRepository<Transacao, String> {

}
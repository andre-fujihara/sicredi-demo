package com.example.sicredidemo.repository;

import com.example.sicredidemo.entity.AssociadoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Classe para manipulação das entidades nesse banco de dados
 */
public interface AssociadoRepository extends MongoRepository<AssociadoEntity, String> {

}

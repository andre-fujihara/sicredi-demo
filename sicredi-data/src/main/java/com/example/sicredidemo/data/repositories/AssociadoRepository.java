package com.example.sicredidemo.data.repositories;

import com.example.sicredidemo.data.entities.AssociadoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.UUID;

/**
 * Classe para manipulação das entidades nesse banco de dados
 */
public interface AssociadoRepository extends MongoRepository<AssociadoEntity, UUID> {

}

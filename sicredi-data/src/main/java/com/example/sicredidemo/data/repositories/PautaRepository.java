package com.example.sicredidemo.data.repositories;

import com.example.sicredidemo.data.entities.PautaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

/**
 * Classe para manipulação das entidades nesse banco de dados
 */
public interface PautaRepository extends MongoRepository<PautaEntity, UUID> {

    /**
     * Método que encontra as {@link PautaEntity} pelo seu Id e com datas de início anterior ao parâmetro start e à data end após o parâmetro passado.
     * Ou seja, ele busca as pautas que estão no intervalo passado e por ID
     * @param pautaId
     * @param start
     * @param end
     * @return
     */
    Optional<PautaEntity> findByIdAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(UUID pautaId, Date start, Date end);

    /**
     * Método para encontrar as pautas que ainda não foram finalizadas e possuem data de fim antes do parâmetro now passado
     * Ou seja o método encontra as pautas ainda não finalizadas utilizando a data passada
     * @param now
     * @return
     */
    Optional<PautaEntity> findByDataFimLessThanEqualAndSessaoFinalizadaIsFalse(Date now);

    /**
     * Método para encontrar as pautas ainda não configuradas buscando pelo seu ID
     * @param pautaId
     * @return
     */
    Optional<PautaEntity> findByIdAndDataInicioIsNull(UUID pautaId);

}

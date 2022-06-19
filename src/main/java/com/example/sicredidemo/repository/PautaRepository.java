package com.example.sicredidemo.repository;

import com.example.sicredidemo.entity.PautaEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Classe para manipulação das entidades nesse banco de dados
 */
public interface PautaRepository extends MongoRepository<PautaEntity, String> {

    /**
     * Método que encontra as {@link PautaEntity} pelo seu Id e com datas de início anterior ao parâmetro start e à data end após o parâmetro passado.
     * Ou seja, ele busca as pautas que estão no intervalo passado e por ID
     * @param pautaId
     * @param start
     * @param end
     * @return
     */
    Optional<PautaEntity> findByIdAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(String pautaId, Date start, Date end);

    /**
     * Método para encontrar as pautas que ainda não foram finalizadas e possuem data de fim antes do parâmetro now passado
     * Ou seja o método encontra as pautas ainda não finalizadas utilizando a data passada
     * @param now
     * @return
     */
    List<PautaEntity> findByDataFimLessThanEqualAndSessaoFinalizadaIsFalse(Date now);

    /**
     * Método para encontrar as pautas ainda não configuradas buscando pelo seu ID
     * @param pautaId
     * @return
     */
    Optional<PautaEntity> findByIdAndDataInicioIsNull(String pautaId);

}

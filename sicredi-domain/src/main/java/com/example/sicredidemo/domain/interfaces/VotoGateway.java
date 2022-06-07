package com.example.sicredidemo.domain.interfaces;

import com.example.sicredidemo.domain.models.Voto;
import com.example.sicredidemo.domain.models.enums.ValorVotoEnum;

import java.util.Optional;
import java.util.UUID;

/**
 * Classe adapter para manipulação das entidades nesse banco de dados do {@link Voto}
 *
 */
public interface VotoGateway {

    /**
     * Método que procura os votos existens para uma pauta e um associado passados
     *
     * @param pautaId
     * @param associadoId
     * @return
     */
    public Optional<Voto> findVotoByPautaIdAndAssociadoId(UUID pautaId, UUID associadoId);

    /**
     * Método que cria um voto utilizando o dto {@link Voto} passado
     * @param voto
     */
    public void criarVoto(Voto voto);

    /**
     * Método para contar quantos votos do tipo valorVoto passado existem para determinada pauta
     * @param pautaId
     * @param valorVoto
     * @return
     */
    public Long contarVotos(UUID pautaId, ValorVotoEnum valorVoto);

}

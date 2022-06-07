package com.example.sicredidemo.domain.interfaces;

import com.example.sicredidemo.domain.models.Associado;
import com.example.sicredidemo.domain.models.Pauta;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Classe adapter para manipulação das entidades nesse banco de dados do {@link Pauta}
 *
 */
public interface PautaGateway {

    /**
     * Método para criar a pauta no banco de dados a partir do dto {@link Pauta}
     * @param pauta
     * @return
     */
    public Pauta criarPauta(Pauta pauta);

    /**
     * Método para atualizar uma pauta já existe no banco de dados através dos valores do dto {@link Pauta}
     * @param pauta
     * @return
     */
    public Pauta atualizarPauta(Pauta pauta);

    /**
     * Método para retornar o dto da pauta com as informações presents no banco
     * @param pautaId
     * @return
     */
    public Optional<Pauta> findPautaById(UUID pautaId);

    /**
     * Método para encontrar as pautas que ainda não foram configuradas no sistema e não foram iniciadas, que possuem data de início vazias
     * @param pautaId
     * @return
     */
    public Optional<Pauta> findByIdAndDataInicioIsNull(UUID pautaId);

    /**
     * Método que encontra as pautas que já foram iniciadas e ainda não foram finalizadas através do seu ID e do momento atual. Ou seja as pautas que estão com sessão ativa
     * @param pautaId
     * @param now
     * @return
     */
    public Optional<Pauta> findByPautaIdAndDataInicioGreaterThanEqualAndDataFimLessThanEqual(UUID pautaId, Date now);

    /**
     * Método para encontrar todas as pautas com sessão ativa que não foram finalizadas
     * @param now
     * @return
     */
    public List<Pauta> findPautaNaoFinalizada(Date now);

    /**
     * Método para recuperar todas as pautas existentes no sistema
     * @return
     */
    public List<Pauta> recuperarTodos();

    }

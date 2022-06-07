package com.example.sicredidemo.domain.interfaces;

import com.example.sicredidemo.domain.models.Associado;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Classe adapter para manipulação das entidades nesse banco de dados do {@link Associado}
 *
 */
public interface AssociadoGateway {

    /**
     * Método para criar um associado no banco passando o dto {@link Associado} e se esse associado é válido
     * @param associado
     * @param isValido
     * @return
     */
    public Associado criarAssociado(Associado associado, boolean isValido);

    /**
     * Método par aprocurar um associado no banco pelo seu Id passado
     * @param associadoId
     * @return
     */
    public Optional<Associado> findAssociadoById(UUID associadoId);

    /**
     * Método que retorna todos os associados cadastrados no banco
     * @return
     */
    public List<Associado> recuperarTodos();
}

package com.example.sicredidemo.domain.usecases;

import com.example.sicredidemo.domain.interfaces.PautaGateway;
import com.example.sicredidemo.domain.models.Pauta;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Serviço que realiza as manipulações das Pautas
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PautaService {

    private final PautaGateway pautaGateway;

    /**
     * Método para criar uma pauta nova a partir do dto
     * @param pauta
     * @return
     */
    public Pauta criarPauta(Pauta pauta) {
        return pautaGateway.criarPauta(pauta);
    }

    /**
     * Método para retornar todas as pautas do sistema
     * @return
     */
    public List<Pauta> recuperarTodos() {
        return pautaGateway.recuperarTodos();
    }
}

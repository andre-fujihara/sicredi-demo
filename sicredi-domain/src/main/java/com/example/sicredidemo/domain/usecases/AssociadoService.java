package com.example.sicredidemo.domain.usecases;

import com.example.sicredidemo.domain.exceptions.SicrediErroException;
import com.example.sicredidemo.domain.interfaces.AssociadoGateway;
import com.example.sicredidemo.domain.models.Associado;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Serviço que realiza as manipulações do Associado
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssociadoService {

    private final AssociadoGateway associadoGateway;
    private final CPFManagerService cpfManagerService;

    /**
     * Método que cria um associado utilizando o DTO passado
     * @param associado
     * @return
     */
    public Associado criarAssociado(Associado associado) {
        associado.setCpf(associado.getCpf().replaceAll("\\.|\\-", ""));
        return associadoGateway.criarAssociado(associado, cpfManagerService.isCPF(associado.getCpf()));
    }

    /**
     * Método para recuperar todos os associados cadastrados no sistema
     * @return
     */
    public List<Associado> recuperarTodosAssociados() {
        return associadoGateway.recuperarTodos();
    }

    /**
     * Método para validar se o usuário pode votar, realizando uma validação externa
     * @param associadoId
     * @return
     */
    public String podeVotar(UUID associadoId){
        Associado associado = associadoGateway.findAssociadoById(associadoId).orElseThrow(() ->
                new SicrediErroException("Associado não encontrado para o ID : " + associadoId, NOT_FOUND));
        if(!cpfManagerService.isUsuarioPodeVotar(associado.getCpf())){
            return "Usuário não pode votar no momento";
        }
        return "Usuário pode votar";
    }

}

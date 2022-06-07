package com.example.sicredidemo.domain.usecases;

import com.example.sicredidemo.domain.exceptions.SicrediErroException;
import com.example.sicredidemo.domain.interfaces.AssociadoGateway;
import com.example.sicredidemo.domain.interfaces.PautaGateway;
import com.example.sicredidemo.domain.interfaces.VotoGateway;
import com.example.sicredidemo.domain.models.Associado;
import com.example.sicredidemo.domain.models.Pauta;
import com.example.sicredidemo.domain.models.ResultadoPauta;
import com.example.sicredidemo.domain.models.Voto;
import com.example.sicredidemo.domain.models.enums.ValorVotoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Serviço que realiza as manipulações dos Votos
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VotoService {

    private final PautaGateway pautaGateway;
    private final AssociadoGateway associadoGateway;
    private final VotoGateway votoGateway;
    private final CPFManagerService cpfManagerService;

    public void votarPauta(UUID pautaId, UUID associadoId, ValorVotoEnum voto) {
        pautaGateway.findByPautaIdAndDataInicioGreaterThanEqualAndDataFimLessThanEqual(pautaId, new Date()).orElseThrow(() ->
                new SicrediErroException("Pauta não encontrada para o ID : " + pautaId, NOT_FOUND));

        Associado associado = associadoGateway.findAssociadoById(associadoId).orElseThrow(() ->
                new SicrediErroException("Associado não encontrado para o ID : " + associadoId, NOT_FOUND));
        if(!cpfManagerService.isUsuarioPodeVotar(associado.getCpf())){
            throw new SicrediErroException("Usuário não pode votar no momento", BAD_REQUEST);
        }

        votoGateway.findVotoByPautaIdAndAssociadoId(pautaId, associadoId).ifPresent(v -> {
            throw new SicrediErroException("Voto já realizado", BAD_REQUEST);
        });


        votoGateway.criarVoto(Voto.builder()
                .pautaId(pautaId)
                .associadoId(associadoId)
                .voto(voto)
                .build());
    }

    public ResultadoPauta contarVotosDaPauta(UUID pautaId){
        Pauta pauta = pautaGateway.findPautaById(pautaId).orElseThrow(() ->
                new SicrediErroException("Pauta não encontrada para o ID : " + pautaId, NOT_FOUND));
        return ResultadoPauta.builder()
                .pautaId(pautaId)
                .pautaNome(pauta.getNome())
                .votosNao(votoGateway.contarVotos(pautaId, ValorVotoEnum.NAO))
                .votosSim(votoGateway.contarVotos(pautaId, ValorVotoEnum.SIM))
                .build();
    }

}

package com.example.sicredidemo.service;

import com.example.sicredidemo.domain.response.ResultadoPautaResponse;
import com.example.sicredidemo.entity.AssociadoEntity;
import com.example.sicredidemo.entity.PautaEntity;
import com.example.sicredidemo.entity.VotoEntity;
import com.example.sicredidemo.enums.ValorVotoEnum;
import com.example.sicredidemo.exception.SicrediErroException;
import com.example.sicredidemo.repository.AssociadoRepository;
import com.example.sicredidemo.repository.PautaRepository;
import com.example.sicredidemo.repository.VotoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

/**
 * Serviço que realiza as manipulações dos Votos
 */
@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VotoService {

    private final PautaRepository pautaRepository;
    private final AssociadoRepository associadoRepository;
    private final VotoRepository votoRepository;
    private final CPFManagerService cpfManagerService;

    public void votarPauta(String pautaId, String associadoId, ValorVotoEnum voto) {
        Date now = new Date();
        pautaRepository.findByIdAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(pautaId, now, now).orElseThrow(() ->
                new SicrediErroException("Pauta não encontrada para o ID : " + pautaId, NOT_FOUND));

        AssociadoEntity associado = associadoRepository.findById(associadoId).orElseThrow(() ->
                new SicrediErroException("Associado não encontrado para o ID : " + associadoId, NOT_FOUND));
        if(!cpfManagerService.isUsuarioPodeVotar(associado.getCpf()))
            throw new SicrediErroException("Usuário não pode votar no momento", BAD_REQUEST);


        votoRepository.findByPautaIdAndAssociadoId(pautaId, associadoId).ifPresent(v -> {
            throw new SicrediErroException("Voto já realizado", BAD_REQUEST);
        });

        votoRepository.save(VotoEntity.builder()
                .pautaId(pautaId)
                .associadoId(associadoId)
                .voto(voto)
                .build());
    }

    public ResultadoPautaResponse contarVotosDaPauta(String pautaId){
        PautaEntity pautaEntity = pautaRepository.findById(pautaId).orElseThrow(() ->
                new SicrediErroException("Pauta não encontrada para o ID : " + pautaId, NOT_FOUND));
        return ResultadoPautaResponse.builder()
                .pautaId(pautaId)
                .pautaNome(pautaEntity.getNome())
                .votosNao(votoRepository.countByPautaIdAndVoto(pautaId, ValorVotoEnum.NAO))
                .votosSim(votoRepository.countByPautaIdAndVoto(pautaId, ValorVotoEnum.SIM))
                .build();
    }

}

package com.example.sicredidemo.data.gateways;

import com.example.sicredidemo.data.entities.VotoEntity;
import com.example.sicredidemo.data.mappers.PautaConverterMapper;
import com.example.sicredidemo.data.mappers.VotoConverterMapper;
import com.example.sicredidemo.data.repositories.PautaRepository;
import com.example.sicredidemo.data.repositories.VotoRepository;
import com.example.sicredidemo.domain.interfaces.VotoGateway;
import com.example.sicredidemo.domain.models.Associado;
import com.example.sicredidemo.domain.models.Voto;
import com.example.sicredidemo.domain.models.enums.ValorVotoEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

/**
 * Classe adapter para manipulação das entidades nesse banco de dados do {@link Voto}
 *
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VotoGatewayImpl implements VotoGateway {

    private final PautaRepository pautaRepository;
    private final PautaConverterMapper pautaConverterMapper;
    private final VotoRepository votoRepository;
    private final VotoConverterMapper votoConverterMapper;

    @Override
    public Optional<Voto> findVotoByPautaIdAndAssociadoId(UUID pautaId, UUID associadoId){
        return votoRepository.findByPautaIdAndAssociadoId(pautaId, associadoId).map(votoConverterMapper::toDomain);
    }

    @Override
    public void criarVoto(Voto voto) {
        VotoEntity votoEntity = votoConverterMapper.toEntity(voto);
        votoEntity.setId(UUID.randomUUID());
        votoRepository.save(votoEntity);
    }

    @Override
    public Long contarVotos(UUID pautaId, ValorVotoEnum valorVoto) {
        return votoRepository.countByPautaIdAndVoto(pautaId, valorVoto);
    }

}

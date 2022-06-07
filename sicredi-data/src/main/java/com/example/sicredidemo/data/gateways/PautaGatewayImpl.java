package com.example.sicredidemo.data.gateways;

import com.example.sicredidemo.data.entities.PautaEntity;
import com.example.sicredidemo.data.mappers.PautaConverterMapper;
import com.example.sicredidemo.data.repositories.PautaRepository;
import com.example.sicredidemo.domain.interfaces.PautaGateway;
import com.example.sicredidemo.domain.models.Pauta;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Classe adapter para manipulação das entidades nesse banco de dados da {@link Pauta}
 *
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PautaGatewayImpl implements PautaGateway {

    private final PautaRepository pautaRepository;
    private final PautaConverterMapper pautaConverterMapper;

    @Override
    public Pauta criarPauta(Pauta pauta) {
        PautaEntity pautaEntity = pautaConverterMapper.toEntity(pauta);
        pautaEntity.setId(UUID.randomUUID());
        return pautaConverterMapper.toDomain(pautaRepository.save(pautaEntity));
    }

    @Override
    public Pauta atualizarPauta(Pauta pauta) {
        PautaEntity pautaEntity = pautaConverterMapper.toEntity(pauta);
        return pautaConverterMapper.toDomain(pautaRepository.save(pautaEntity));
    }

    @Override
    public List<Pauta> findPautaNaoFinalizada(Date now) {
        return pautaRepository.findByDataFimLessThanEqualAndSessaoFinalizadaIsFalse(now).stream().map(pautaConverterMapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<Pauta> findByIdAndDataInicioIsNull(UUID pautaId) {
        return pautaRepository.findByIdAndDataInicioIsNull(pautaId).map(pautaConverterMapper::toDomain);
    }

    @Override
    public Optional<Pauta> findPautaById(UUID pautaId){
        return pautaRepository.findById(pautaId).map(pautaConverterMapper::toDomain);
    }

    @Override
    public Optional<Pauta> findByPautaIdAndDataInicioGreaterThanEqualAndDataFimLessThanEqual(UUID pautaId, Date now){
        return pautaRepository.findByIdAndDataInicioLessThanEqualAndDataFimGreaterThanEqual(pautaId, now, now).map(pautaConverterMapper::toDomain);
    }

    @Override
    public List<Pauta> recuperarTodos() {
        return pautaRepository.findAll().stream().map(pautaConverterMapper::toDomain).collect(Collectors.toList());
    }

}

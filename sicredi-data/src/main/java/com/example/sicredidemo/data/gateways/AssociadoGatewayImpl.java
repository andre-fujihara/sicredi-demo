package com.example.sicredidemo.data.gateways;

import com.example.sicredidemo.data.entities.AssociadoEntity;
import com.example.sicredidemo.data.mappers.AssociadoConverterMapper;
import com.example.sicredidemo.data.repositories.AssociadoRepository;
import com.example.sicredidemo.domain.interfaces.AssociadoGateway;
import com.example.sicredidemo.domain.models.Associado;
import com.example.sicredidemo.domain.models.Pauta;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Classe adapter para manipulação das entidades nesse banco de dados do {@link Associado}
 *
 */
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AssociadoGatewayImpl implements AssociadoGateway {

    private final AssociadoRepository associadoRepository;
    private final AssociadoConverterMapper associadoConverterMapper;

    @Override
    public Associado criarAssociado(Associado associado, boolean isValido) {
        AssociadoEntity associadoEntity = associadoConverterMapper.toEntity(associado);
        associadoEntity.setId(UUID.randomUUID());
        associadoEntity.setValido(isValido);
        return associadoConverterMapper.toDomain(associadoRepository.save(associadoEntity));
    }

    @Override
    public Optional<Associado> findAssociadoById(UUID pautaId) {
        return associadoRepository.findById(pautaId).map(associadoConverterMapper::toDomain);
    }

    @Override
    public List<Associado> recuperarTodos() {
        return associadoRepository.findAll().stream().map(associadoConverterMapper::toDomain).collect(Collectors.toList());
    }

}

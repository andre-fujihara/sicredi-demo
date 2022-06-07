package com.example.sicredidemo.data.mappers;

import com.example.sicredidemo.data.entities.PautaEntity;
import com.example.sicredidemo.domain.models.Pauta;
import org.mapstruct.Mapper;

/**
 * Mapper para automaticamente criar um conversor entre as classes de transporte da entidade e seu dto
 */
@Mapper(componentModel = "spring")
public interface PautaConverterMapper {

    PautaEntity toEntity(Pauta pauta);

    Pauta toDomain(PautaEntity pautaEntity);
}

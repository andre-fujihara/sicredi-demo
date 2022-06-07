package com.example.sicredidemo.data.mappers;

import com.example.sicredidemo.data.entities.VotoEntity;
import com.example.sicredidemo.domain.models.Voto;
import org.mapstruct.Mapper;

/**
 * Mapper para automaticamente criar um conversor entre as classes de transporte da entidade e seu dto
 */
@Mapper(componentModel = "spring")
public interface VotoConverterMapper {

    VotoEntity toEntity(Voto voto);

    Voto toDomain(VotoEntity votoEntity);
}

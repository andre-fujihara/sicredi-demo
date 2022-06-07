package com.example.sicredidemo.data.mappers;

import com.example.sicredidemo.data.entities.AssociadoEntity;
import com.example.sicredidemo.domain.models.Associado;
import org.mapstruct.Mapper;

/**
 * Mapper para automaticamente criar um conversor entre as classes de transporte da entidade e seu dto
 */
@Mapper(componentModel = "spring")
public interface AssociadoConverterMapper {

    AssociadoEntity toEntity(Associado associado);

    Associado toDomain(AssociadoEntity associadoEntity);
}

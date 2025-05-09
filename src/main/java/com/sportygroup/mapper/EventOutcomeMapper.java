// EventOutcomeMapper.java
package com.sportygroup.mapper;

import com.sportygroup.domain.dto.EventOutcomeDto;
import com.sportygroup.domain.entity.EventOutcomeEntity;
import com.sportygroup.domain.model.EventOutcome;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventOutcomeMapper {
    // Entity <-> Model
    EventOutcome entityToModel(EventOutcomeEntity entity);
    EventOutcomeEntity modelToEntity(EventOutcome model);
    List<EventOutcome> entitiesToModels(List<EventOutcomeEntity> entities);

    // Model <-> DTO
    EventOutcomeDto modelToDto(EventOutcome model);

    EventOutcome dtoToModel(EventOutcomeDto dto);
    // Entity <-> DTO (convenience methods)
    default EventOutcomeDto entityToDto(EventOutcomeEntity entity) {
        return modelToDto(entityToModel(entity));
    }

    default EventOutcomeEntity dtoToEntity(EventOutcomeDto dto) {
        return modelToEntity(dtoToModel(dto));
    }
}

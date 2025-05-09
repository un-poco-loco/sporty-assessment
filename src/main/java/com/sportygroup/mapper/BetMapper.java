// BetMapper.java
package com.sportygroup.mapper;

import com.sportygroup.domain.dto.BetDto;
import com.sportygroup.domain.entity.BetEntity;
import com.sportygroup.domain.model.Bet;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BetMapper {
    Bet entityToModel(BetEntity entity);
    BetEntity modelToEntity(Bet model);
    List<Bet> entitiesToModels(List<BetEntity> entities);
    List<BetEntity> modelsToEntities(List<Bet> models);

    BetDto modelToDto(Bet model);
    Bet dtoToModel(BetDto dto);
    List<BetDto> modelsToDtos(List<Bet> models);
    default BetDto entityToDto(BetEntity entity) {
        return modelToDto(entityToModel(entity));
    }

    default BetEntity dtoToEntity(BetDto dto) {
        return modelToEntity(dtoToModel(dto));
    }

    default List<BetDto> entitiesToDtos(List<BetEntity> entities) {
        return modelsToDtos(entitiesToModels(entities));
    }
}


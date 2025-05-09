// BetSettlementMapper.java
package com.sportygroup.mapper;

import com.sportygroup.domain.dto.BetSettlementDto;
import com.sportygroup.domain.model.BetSettlement;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BetSettlementMapper {
    BetSettlementDto modelToDto(BetSettlement model);
    BetSettlement dtoToModel(BetSettlementDto dto);
    List<BetSettlementDto> modelsToDtos(List<BetSettlement> models);
}
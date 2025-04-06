package com.albaridbank.edition.mappers;

import com.albaridbank.edition.dto.ParametreRapportDTO;
import com.albaridbank.edition.model.ParametreRapport;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")
public interface ParameterRapportMapper {

    ParametreRapportDTO toDto(ParametreRapport parametre);

    List<ParametreRapportDTO> toDtoList(Set<ParametreRapport> parametres);

    @Mapping(target = "rapport", ignore = true)
    ParametreRapport toEntity(ParametreRapportDTO parametreDTO);

    Set<ParametreRapport> toEntitySet(List<ParametreRapportDTO> parametreDTOs);
}
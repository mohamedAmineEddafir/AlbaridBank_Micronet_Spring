package com.albaridbank.edition.mappers.cen;

import com.albaridbank.edition.dto.base.MouvementCENDTO;
import com.albaridbank.edition.model.cen.OperCompCEN;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OperCompCENMapper {

    @Mapping(source = "compteDebit", target = "idencomp")
    @Mapping(expression = "java(getIntituleCompte(operComp))", target = "inticomp")
    @Mapping(source = "typeOperation", target = "libtypop")
    @Mapping(expression = "java(getSens(operComp))", target = "sensMouvement")
    @Mapping(source = "montantOperation", target = "montoper")
    MouvementCENDTO toMouvementCENDTO(OperCompCEN operComp);

    List<MouvementCENDTO> toMouvementCENDTOList(List<OperCompCEN> operations);

    default String getSens(OperCompCEN operComp) {
        return operComp.getCompteDebit() != null ? "D" : "C";
    }
}
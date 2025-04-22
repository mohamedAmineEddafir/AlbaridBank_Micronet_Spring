package com.albaridbank.edition.mappers.ccp;

import com.albaridbank.edition.dto.base.MouvementFinancierDTO;
import com.albaridbank.edition.model.ccp.MvtFinancierCCP;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MvtFinancierCCPMapper {

    @Mapping(source = "compte.idCompte", target = "idencomp")
    @Mapping(source = "compte.intitule", target = "inticomp")
    @Mapping(source = "bureauPoste.codeBureau", target = "codburpo")
    @Mapping(source = "bureauPoste.designation", target = "desburpo")
    @Mapping(source = "typeOperation.libelle", target = "libtypop")
    @Mapping(source = "sens", target = "sensmouv")
    @Mapping(source = "montant", target = "montmouv")
    MouvementFinancierDTO toMouvementFinancierDTO(MvtFinancierCCP mvt);

    List<MouvementFinancierDTO> toMouvementFinancierDTOList(List<MvtFinancierCCP> mouvements);
}
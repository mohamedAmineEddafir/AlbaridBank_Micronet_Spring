package com.albaridbank.edition.mappers.rapport;

import com.albaridbank.edition.dto.base.CompteCENDetailDTO;
import com.albaridbank.edition.dto.base.MouvementCENDTO;
import com.albaridbank.edition.dto.rapport.CompteCENMouvementVeilleDTO;
import com.albaridbank.edition.dto.rapport.NbrTotalEncoursCENDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleCENDTO;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {LocalDate.class, LocalDateTime.class, BigDecimal.class})
public interface RapportCENMapper {

    @Mapping(target = "titreRapport", constant = "ETAT COMPTES CEN MOUVEMENTÉS VEILLE")
    @Mapping(target = "dateEdition", expression = "java(LocalDateTime.now())")
    @Mapping(target = "numeroPage", constant = "1")
    @Mapping(target = "journeeDu", source = "dateRapport")
    @Mapping(target = "codburpo", source = "codeBureau")
    @Mapping(target = "desburpo", source = "desBureau")
    @Mapping(target = "mouvements", source = "mouvementDTOs")
    @Mapping(target = "nombreTotalComptes", source = "nombreComptes")
    @Mapping(target = "montantTotal", source = "montantTotal")
    CompteCENMouvementVeilleDTO creerRapportMouvementCENVeille(
            Long codeBureau,
            String desBureau,
            LocalDate dateRapport,
            List<MouvementCENDTO> mouvementDTOs,
            Integer nombreComptes,
            BigDecimal montantTotal);

    @Mapping(target = "titreRapport", constant = "ETAT NOMBRE TOTAL & ENCOURS GLOBAL CEN")
    @Mapping(target = "journeeDu", expression = "java(LocalDate.now())")
    @Mapping(target = "codburpo", source = "codeBureau")
    @Mapping(target = "desburpo", source = "desBureau")
    @Mapping(target = "nombreComptes", source = "nombreComptes")
    @Mapping(target = "totalEncours", source = "totalEncours")
    NbrTotalEncoursCENDTO creerRapportEncoursGlobalCEN(
            Long codeBureau,
            String desBureau,
            Long nombreComptes,
            BigDecimal totalEncours);

    @Mapping(target = "titreRapport", constant = "ETAT PORTEFEUILLE CEN")
    @Mapping(target = "dateEdition", expression = "java(LocalDateTime.now())")
    @Mapping(target = "numeroPage", constant = "1")
    @Mapping(target = "codburpo", source = "codeBureau")
    @Mapping(target = "desburpo", source = "desBureau")
    @Mapping(target = "comptes", source = "compteDTOs")
    @Mapping(target = "encoursTotal", source = "encoursTotal")
    PortefeuilleCENDTO creerRapportPortefeuilleCEN(
            Long codeBureau,
            String desBureau,
            List<CompteCENDetailDTO> compteDTOs,
            BigDecimal encoursTotal);
}
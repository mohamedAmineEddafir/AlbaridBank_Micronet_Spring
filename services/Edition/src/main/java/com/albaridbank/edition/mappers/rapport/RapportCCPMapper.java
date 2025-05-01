package com.albaridbank.edition.mappers.rapport;

import com.albaridbank.edition.dto.base.CompteCCPDetailDTO;
import com.albaridbank.edition.dto.base.MouvementFinancierDTO;
import com.albaridbank.edition.dto.rapport.CompteMouvementVeilleDTO;
import com.albaridbank.edition.dto.rapport.NbrTotalEncoursCCPDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCPDTO;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {LocalDate.class, LocalDateTime.class, BigDecimal.class})
public interface RapportCCPMapper {

    @Mapping(target = "titreRapport", expression = "java(\"ETAT COMPTES MOUVEMENTEES \" + (joursAvant == 1 ? \"VEILLE\" : \"AVANT VEILLE\"))")
    @Mapping(target = "dateEdition", expression = "java(LocalDateTime.now())")
    @Mapping(target = "numeroPage", constant = "0")
    @Mapping(target = "journeeDu", source = "dateRapport")
    @Mapping(target = "codeAgence", source = "codeBureau")
    @Mapping(target = "nomAgence", source = "desBureau")
    @Mapping(target = "mouvements", source = "mouvementDTOs")
    @Mapping(target = "nombreTotalComptes", source = "nombreComptes")
    @Mapping(target = "montantTotal", source = "montantTotal")
    @Mapping(target = "joursAvant", source = "joursAvant")
    @Mapping(target = "montantMinimum", source = "montantMinimum")
    CompteMouvementVeilleDTO creerRapportMouvementVeille(
            Long codeBureau,
            String desBureau,
            LocalDate dateRapport,
            List<MouvementFinancierDTO> mouvementDTOs,
            Integer nombreComptes,
            BigDecimal montantTotal,
            Integer joursAvant,
            BigDecimal montantMinimum);

    @Mapping(target = "titreRapport", constant = "ETAT NOMBRE TOTAL & ENCOURS GLOBAL CCP")
    @Mapping(target = "journeeDu", expression = "java(LocalDate.now())")
    @Mapping(target = "codburpo", source = "codeBureau")
    @Mapping(target = "desburpo", source = "desBureau")
    @Mapping(target = "nombreComptes", source = "nombreComptes")
    @Mapping(target = "totalEncours", source = "totalEncours")
    NbrTotalEncoursCCPDTO creerRapportEncoursGlobal(
            Long codeBureau,
            String desBureau,
            Long nombreComptes,
            BigDecimal totalEncours);

    @Mapping(target = "titreRapport", constant = "ETAT PORTEFEUILLE CLIENT CCP")
    @Mapping(target = "dateEdition", expression = "java(java.time.LocalDateTime.parse(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern(\"dd/MM/yyyy HH:mm:ss\")), java.time.format.DateTimeFormatter.ofPattern(\"dd/MM/yyyy HH:mm:ss\")))")
    @Mapping(target = "codburpo", source = "codeBureau")
    @Mapping(target = "desburpo", source = "desBureau")
    @Mapping(target = "comptes", source = "compteDTOs")
    @Mapping(target = "nombreTotalComptes", expression = "java(compteDTOs.size())")
    @Mapping(target = "encoursTotalComptes", source = "encoursTotal")
    PortefeuilleClientCCPDTO creerRapportPortefeuilleClient(
            Long codeBureau,
            String desBureau,
            List<CompteCCPDetailDTO> compteDTOs,
            BigDecimal encoursTotal
    );
}
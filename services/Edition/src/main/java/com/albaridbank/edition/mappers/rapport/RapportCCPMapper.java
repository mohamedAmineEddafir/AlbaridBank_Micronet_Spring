package com.albaridbank.edition.mappers.rapport;

import com.albaridbank.edition.dto.base.CompteCCPDetailDTO;
import com.albaridbank.edition.dto.base.MouvementFinancierDTO;
import com.albaridbank.edition.dto.rapport.CompteMouvementVeilleDTO;
import com.albaridbank.edition.dto.rapport.NbrTotalEncoursCCPDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCPDTO;
import com.albaridbank.edition.repositorys.ccp.projectionCCPRepo.CompteStats;
import org.mapstruct.*;
import org.springframework.data.domain.Page;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {LocalDate.class, LocalDateTime.class, BigDecimal.class})
public interface RapportCCPMapper {

    @Mapping(target = "titreRapport", expression = "java(\"ETAT DES COMPTES MOUVEMENTEES \" + (joursAvant == 1 ? \"LA VEILLE\" : \"L'AVANT VEILLE\"))")
    @Mapping(target = "dateEdition", expression = "java(LocalDateTime.now())")
    @Mapping(target = "numeroPage", constant = "1")
    @Mapping(target = "journeeDu", source = "dateRapport")
    @Mapping(target = "codeAgence", source = "codeBureau")
    @Mapping(target = "nomAgence", source = "desBureau")
    @Mapping(target = "mouvements", source = "mouvementDTOs")
    @Mapping(target = "nombreTotalComptes", source = "nombreComptes")
    @Mapping(target = "montantTotal", source = "montantTotal")
    @Mapping(target = "joursAvant", source = "joursAvant")
    @Mapping(target = "montantMinimum", source = "montantMinimum")
    @Mapping(target = "createdBy", expression = "java(\"SYSTEM\")")
    @Mapping(target = "creationDateTime", expression = "java(LocalDateTime.now())")
    CompteMouvementVeilleDTO creerRapportMouvementVeille(
            Long codeBureau,
            String desBureau,
            LocalDate dateRapport,
            Page<MouvementFinancierDTO> mouvementDTOs,
            Integer nombreComptes,
            BigDecimal montantTotal,
            Integer joursAvant,
            BigDecimal montantMinimum);

    @Mapping(target = "titreRapport", constant = "ETAT PORTEFEUILLE CLIENT CCP")
    @Mapping(target = "dateEdition", expression = "java(java.time.LocalDateTime.parse(java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern(\"dd/MM/yyyy HH:mm:ss\")), java.time.format.DateTimeFormatter.ofPattern(\"dd/MM/yyyy HH:mm:ss\")))")
    @Mapping(target = "codburpo", source = "codeBureau")
    @Mapping(target = "desburpo", source = "designation")
    @Mapping(target = "comptes", source = "compteDTOs")
    @Mapping(target = "nombreTotalComptes", source = "nombreTotalComptes")
    @Mapping(target = "encoursTotalComptes", source = "encoursTotal")
    PortefeuilleClientCCPDTO creerRapportPortefeuilleClient(
            Long codeBureau,
            String designation,
            List<CompteCCPDetailDTO> compteDTOs,
            BigDecimal encoursTotal,
            Long nombreTotalComptes
    );

    /**
     * <h4>ETAT NOMBRE TOTAL & ENCOURS GLOBAL CCP</h4>
     * <p>
     * Cette méthode est utilisée pour mapper les statistiques des comptes
     * à un DTO spécifique pour le rapport.
     * </p>
     * <p>
     * Elle est annotée avec {@Named} pour permettre
     * son utilisation dans d'autres mappers.
     * </p>
     */
    @Mapping(target = "titreRapport", constant = "ETAT NOMBRE TOTAL & ENCOURS GLOBAL CCP")
    @Mapping(target = "codeBureau", source = "codburpo")
    @Mapping(target = "designationBureau", source = "desburpo")
    @Mapping(target = "journeeDu", expression = "java(LocalDate.now())")
    @Mapping(target = "nombreComptes", source = "nombreTotalComptes")
    @Mapping(target = "totalEncours", source = "totalEncours")
    NbrTotalEncoursCCPDTO toDto(CompteStats stats);

    /**
     * Mapping pour un DTO vide
     */
    @Named("toEmptyDto")
    @Mapping(target = "titreRapport", constant = "ETAT NOMBRE TOTAL & ENCOURS GLOBAL CCP")
    @Mapping(target = "codeBureau", source = "codeBureau")
    @Mapping(target = "designationBureau", constant = "")
    @Mapping(target = "journeeDu", expression = "java(LocalDate.now())")
    @Mapping(target = "nombreComptes", constant = "0L")
    @Mapping(target = "totalEncours", expression = "java(BigDecimal.ZERO)")
    NbrTotalEncoursCCPDTO toEmptyDto(Long codeBureau);

    /**
     * Méthode de compatibilité
     */
    @Mapping(target = "titreRapport", constant = "ETAT NOMBRE TOTAL & ENCOURS GLOBAL CCP")
    @Mapping(target = "codeBureau", source = "codeBureau")
    @Mapping(target = "designationBureau", source = "desBureau")
    @Mapping(target = "journeeDu", expression = "java(LocalDate.now())")
    @Mapping(target = "nombreComptes", source = "nombreComptes")
    @Mapping(target = "totalEncours", source = "totalEncours")
    NbrTotalEncoursCCPDTO creerRapportEncoursGlobal(
            Long codeBureau,
            String desBureau,
            Long nombreComptes,
            BigDecimal totalEncours);
}
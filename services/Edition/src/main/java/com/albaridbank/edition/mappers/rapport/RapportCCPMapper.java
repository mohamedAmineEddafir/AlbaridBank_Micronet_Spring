package com.albaridbank.edition.mappers.rapport;

import com.albaridbank.edition.dto.base.CompteCCPDetailDTO;
import com.albaridbank.edition.dto.base.MouvementFinancierDTO;
import com.albaridbank.edition.dto.base.PortefeuilleClientCCPDetailDTO;
import com.albaridbank.edition.dto.excelCCP.CompteMouvementVeilleExcelDTO;
import com.albaridbank.edition.dto.rapport.CompteMouvementVeilleDTO;
import com.albaridbank.edition.dto.rapport.NbrTotalEncoursCCPDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCPDTO;
import com.albaridbank.edition.dto.rapport.PortefeuilleClientCCPRapportDTO;
import com.albaridbank.edition.mappers.ccp.CompteCCPMapper;
import com.albaridbank.edition.mappers.util.MapperUtil;
import com.albaridbank.edition.model.ccp.CompteCCP;
import com.albaridbank.edition.repositorys.ccp.projectionCCPRepo.CompteStats;
import com.albaridbank.edition.repositorys.ccp.projectionCCPRepo.PortefeuilleStats;
import org.mapstruct.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;

/**
 * Mapper pour la gestion des rapports CCP
 *
 * @author Mohamed Amine EDDAFIR
 * @version 1.0
 * @since 2025-05-15
 */
@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {
                LocalDate.class,
                LocalDateTime.class,
                BigDecimal.class,
                MapperUtil.class,
                DateTimeFormatter.class
        },
        uses = {CompteCCPMapper.class}
)
public interface RapportCCPMapper {

    /**
     * Mapping des états de compte
     */
    Map<String, String> ETATS_MAPPING = Map.of(
            "N", "Normal",
            "O", "Oppose",
            "C", "Cloture",
            "B", "Blocage"
    );

    /**
     * Crée un rapport des mouvements de la veille
     */
    @Mapping(target = "titreRapport",
            expression = "java(\"ETAT DES COMPTES MOUVEMENTEES \" + (joursAvant == 1 ? \"LA VEILLE\" : \"L'AVANT VEILLE\"))")
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
    @Mapping(target = "createdBy", constant = "mohamedAmineEddafir")
    @Mapping(target = "creationDateTime", expression = "java(LocalDateTime.parse(\"2025-05-15 11:48:25\", DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")))")
    CompteMouvementVeilleDTO creerRapportMouvementVeille(
            Long codeBureau,
            String desBureau,
            LocalDate dateRapport,
            Page<MouvementFinancierDTO> mouvementDTOs,
            Integer nombreComptes,
            BigDecimal montantTotal,
            Integer joursAvant,
            BigDecimal montantMinimum
    );

    /**
     * Crée un rapport de portefeuille client
     */
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
     * Creates a detailed portfolio report DTO from various sources.
     */
    @Mapping(target = "titreRapport", constant = "ETAT PORTE FEUILLE CLIENT M CCP")
    @Mapping(target = "dateEdition",
            expression = "java(LocalDateTime.parse(\"2025-05-15 12:16:23\", " +
                    "DateTimeFormatter.ofPattern(\"yyyy-MM-dd HH:mm:ss\")))")
    @Mapping(target = "numeroPage", constant = "1")
    @Mapping(target = "codburpo", source = "codeBureau")
    @Mapping(target = "desburpo", source = "designation")
    @Mapping(target = "comptes", source = "comptes")
    @Mapping(target = "nombreTotalComptes", source = "stats.nombreTotalComptes")
    @Mapping(target = "encoursTotalComptes", source = "stats.encoursTotalComptes")
    @Mapping(target = "totalSoldeOpposition", source = "stats.totalSoldeOpposition")
    @Mapping(target = "totalSoldeTaxe", source = "stats.totalSoldeTaxe")
    @Mapping(target = "totalSoldeDebitOperations", source = "stats.totalSoldeDebitOperations")
    @Mapping(target = "totalSoldeCreditOperations", source = "stats.totalSoldeCreditOperations")
    @Mapping(target = "totalSoldeOperationsPeriode", source = "stats.totalSoldeOperationsPeriode")
    @Mapping(target = "totalSoldeCertifie", source = "stats.totalSoldeCertifie")
    PortefeuilleClientCCPRapportDTO creerRapportPortefeuilleDetaillee(
            Long codeBureau,
            String designation,
            List<PortefeuilleClientCCPDetailDTO> comptes,
            PortefeuilleStats stats
    );

    /**
     * Convertit les statistiques de compte en DTO
     */
    @Mapping(target = "titreRapport", constant = "ETAT NOMBRE TOTAL & ENCOURS GLOBAL CCP")
    @Mapping(target = "codeBureau", source = "codburpo")
    @Mapping(target = "designationBureau", source = "desburpo")
    @Mapping(target = "journeeDu", expression = "java(LocalDate.now())")
    @Mapping(target = "nombreComptes", source = "nombreTotalComptes")
    @Mapping(target = "totalEncours", source = "totalEncours")
    NbrTotalEncoursCCPDTO toDto(CompteStats stats);

    /**
     * Crée un DTO vide pour les statistiques
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
     * Crée un rapport d'encours global
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
            BigDecimal totalEncours
    );

    /**
     * Maps a CompteCCP entity to its DTO representation.
     */
    @Named("toPortefeuilleDetailDTO")
    @Mapping(source = "idCompte", target = "idencomp")
    @Mapping(source = "intitule", target = "inticomp")
    @Mapping(source = "adresse", target = "adrecomp")
    @Mapping(source = "codePostal", target = "codepost")
    @Mapping(source = "intituleCondense", target = "inticond")
    @Mapping(source = "client.categorieSocioProfessionnelle.libelle", target = "libsocpr")
    @Mapping(source = "client.numeroPieceIdentite", target = "numpieid")
    @Mapping(source = "client.telephone", target = "numetele")
    @Mapping(source = "codeEtatCompte", target = "etatCompte")
    @Mapping(source = "codeEtatCompte", target = "typeCompteLibelle", qualifiedByName = "formatEtatCompteRapport")
    @Mapping(source = "client.dateNaissance", target = "datenais")
    @Mapping(source = "soldeCourant", target = "soldcour")
    @Mapping(source = "soldeOpposition", target = "soldoppo")
    @Mapping(source = "soldeTaxe", target = "soldtaxe")
    @Mapping(source = "soldeDebitOperations", target = "solddebo")
    @Mapping(source = "soldeCreditOperations", target = "solddeco")
    @Mapping(source = "soldeOperationsPeriode", target = "solopede")
    @Mapping(source = "soldeCertifie", target = "soldcert")
    @Mapping(source = "dateSolde", target = "datesold")
    @Mapping(source = "codeProduit", target = "comptetyp")
    PortefeuilleClientCCPDetailDTO toDetailDTO(CompteCCP compte);

    /**
     * Formats the account state code to its display label.
     */
    @Named("formatEtatCompteRapport")
    default String formatEtatCompteRapport(String codeEtatCompte) {
        if (codeEtatCompte == null) return "Inconnu";
        return ETATS_MAPPING.getOrDefault(codeEtatCompte.toUpperCase(), "Inconnu");
    }

    /**
     * After mapping, ensure all BigDecimal fields are properly initialized.
     */
    @AfterMapping
    default void setDefaultValues(@MappingTarget PortefeuilleClientCCPDetailDTO dto) {
        if (dto.getSoldcour() == null) dto.setSoldcour(BigDecimal.ZERO);
        if (dto.getSoldoppo() == null) dto.setSoldoppo(BigDecimal.ZERO);
        if (dto.getSoldtaxe() == null) dto.setSoldtaxe(BigDecimal.ZERO);
        if (dto.getSolddebo() == null) dto.setSolddebo(BigDecimal.ZERO);
        if (dto.getSolddeco() == null) dto.setSolddeco(BigDecimal.ZERO);
        if (dto.getSolopede() == null) dto.setSolopede(BigDecimal.ZERO);
        if (dto.getSoldcert() == null) dto.setSoldcert(BigDecimal.ZERO);
    }

    /**
     * Convertit un CompteMouvementVeilleDTO en CompteMouvementVeilleExcelDTO pour l'export Excel
     */
    @Mapping(target = "titreRapport", source = "titreRapport")
    @Mapping(target = "dateEdition", source = "dateEdition")
    @Mapping(target = "journeeDu", source = "journeeDu")
    @Mapping(target = "codeAgence", source = "codeAgence")
    @Mapping(target = "nomAgence", source = "nomAgence")
    @Mapping(target = "mouvements", expression = "java(rapportDTO.getMouvements().getContent())")
    @Mapping(target = "nombreTotalComptes", source = "nombreTotalComptes")
    @Mapping(target = "montantTotal", source = "montantTotal")
    @Mapping(target = "joursAvant", source = "joursAvant")
    @Mapping(target = "montantMinimum", source = "montantMinimum")
    CompteMouvementVeilleExcelDTO toExcelDTO(CompteMouvementVeilleDTO rapportDTO);
}
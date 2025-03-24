package com.albaridbank.BankinApp.mappers;

import com.albaridbank.BankinApp.dtos.CompteDTO;
import com.albaridbank.BankinApp.models.Compte;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper optimisé pour l'affichage des comptes et leurs informations
 */
@Mapper(
        componentModel = "spring",
        uses = {AdresseLinkMapper.class}
)
public interface CompteMapper {

    /**
     * Convertit une entité Compte en DTO pour l'affichage
     */
    @Mapping(source = "idencomp", target = "compteId")
    @Mapping(source = "codcatcp", target = "categoryCode")
    @Mapping(source = "codcatcp", target = "subCategoryCode")
    @Mapping(source = "client.idenclie", target = "clientId")
    @Mapping(source = "client.nomrais", target = "clientLastName")
    @Mapping(source = "client.prenclie", target = "clientFirstName")
    @Mapping(source = "inticomp", target = "accountTitle")
    @Mapping(source = "dateouve", target = "openingDate")
    @Mapping(source = "dateclot", target = "closingDate")
    @Mapping(source = "datogmaj", target = "lastUpdateDate")
    @Mapping(source = "bloqadr", target = "addressBlocked")
    @Mapping(source = "datetacp", target = "accountStatusDate")
    @Mapping(source = "NSig", target = "signaturesCount")
    @Mapping(source = "codeinst", target = "institutionCode")
    @Mapping(source = "codclaure", target = "rateClass")
    @Mapping(source = "codetacp", target = "accountStatus")
    @Mapping(source = "cleComp", target = "checkDigit")
    @Mapping(source = "codebpcpt", target = "branchCode")
    @Mapping(source = "dateremb", target = "repaymentDate")
    @Mapping(source = "cmptmand", target = "mandateAccount")
    @Mapping(source = "cptedeco", target = "overdrawnAccount")
    @Mapping(source = "codbpini", target = "initialBranchCode")
    @Mapping(source = "usrgestcp", target = "accountManager")
    @Mapping(source = "comoetco", target = "commissionMode")
    @Mapping(source = "codepinn", target = "productCode")
    @Mapping(source = "comptmon", target = "monitoredAccount")
    @Mapping(source = "nserliv", target = "bookSerialNumber")
    @Mapping(source = "typerela", target = "relationshipType")
    @Mapping(source = "perirele", target = "releaseInterval")
    @Mapping(source = "codedevi", target = "currencyCode")
    //@Mapping(source = "adresses", target = "addresses")
    CompteDTO toDto(Compte compte);

    /**
     * Convertit une liste d'entités Compte en liste de DTOs
     */
    List<CompteDTO> toDtoList(List<Compte> comptes);
}

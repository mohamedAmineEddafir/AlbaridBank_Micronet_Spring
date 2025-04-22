package com.albaridbank.edition.mappers.ccp;

import com.albaridbank.edition.dto.base.CompteCCPDetailDTO;
import com.albaridbank.edition.dto.base.CompteCCP_Top100_DTO;
import com.albaridbank.edition.mappers.util.MapperUtil;
import com.albaridbank.edition.model.ccp.CompteCCP;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompteCCPMapper {

    @Mapping(source = "idCompte", target = "idencomp")
    @Mapping(source = "intitule", target = "inticomp")
    @Mapping(source = "adresse", target = "adrecomp")
    @Mapping(source = "client.categorieSocioProfessionnelle.libelle", target = "libsocpr")
    @Mapping(source = "client.numeroPieceIdentite", target = "numpieid")
    @Mapping(source = "client.telephone", target = "numetele")
    @Mapping(source = "codeEtatCompte", target = "etatCompte", qualifiedByName = "formatEtatCompte")
    @Mapping(source = "client.dateNaissance", target = "datenais")
    @Mapping(source = "soldeCourant", target = "soldcour")
    @Mapping(source = "codeProduit", target = "comptetyp")
    CompteCCPDetailDTO toCompteCCPDetailDTO(CompteCCP compte);

    List<CompteCCPDetailDTO> toCompteCCPDetailDTOList(List<CompteCCP> comptes);

    @Mapping(source = "idCompte", target = "idencomp")
    @Mapping(source = "intitule", target = "inticomp")
    @Mapping(source = "adresse", target = "adrecomp")
    @Mapping(source = "client.categorieSocioProfessionnelle.libelle", target = "libsocpr")
    @Mapping(source = "soldeCourant", target = "soldcour")
    CompteCCP_Top100_DTO toCompteCCPTop100DTO(CompteCCP compte);

    List<CompteCCP_Top100_DTO> toCompteCCPTop100DTOList(List<CompteCCP> comptes);

    @Named("formatEtatCompte")
    default String formatEtatCompte(String codeEtatCompte) {
        return MapperUtil.mapEtatCompteCCP(codeEtatCompte);
    }

    // format dd/MM/yyyy
    @Named("formatDate")
    default String formatDate(LocalDate date) {
        return MapperUtil.formatDate(date);
    }

    // format dd/MM/yyyy HH:mm:ss
    @Named("formatDateTime")
    default String formatDateTime(LocalDateTime dateTime) {
        return MapperUtil.formatDateTime(dateTime);
    }
}

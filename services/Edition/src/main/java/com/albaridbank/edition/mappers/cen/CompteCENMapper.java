package com.albaridbank.edition.mappers.cen;

import com.albaridbank.edition.dto.base.CompteCENDetailDTO;
import com.albaridbank.edition.mappers.util.MapperUtil;
import com.albaridbank.edition.model.cen.CompteCEN;
import org.mapstruct.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CompteCENMapper {

    @Mapping(source = "idCompte", target = "idencomp")
    @Mapping(source = "intitule", target = "inticomp")
    @Mapping(source = "adresse", target = "adrecomp")
    @Mapping(source = "client.categorieSocioProfessionnelle.libelle", target = "licasopr")
    @Mapping(source = "client.dateNaissance", target = "datenais", qualifiedByName = "formatDate")
    @Mapping(source = "client.numeroPieceIdentite", target = "numpieid")
    @Mapping(source = "client.telephone", target = "numetele")
    @Mapping(source = "codeTypeActivite", target = "etatCompte", qualifiedByName = "mapEtatCompte")
    @Mapping(source = "soldeCourant", target = "soldminu")
    @Mapping(source = "dureeCompte", target = "durecomp")
    CompteCENDetailDTO toCompteCENDetailDTO(CompteCEN compte);

    List<CompteCENDetailDTO> toCompteCENDetailDTOList(List<CompteCEN> comptes);

    @Named("mapEtatCompte")
    default String mapEtatCompte(Integer codeTypeActivite) {
        return MapperUtil.mapEtatCompteCEN(codeTypeActivite);
    }

    @Mapping(source = "client.dateNaissance", target = "datenais", qualifiedByName = "formatDate")
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
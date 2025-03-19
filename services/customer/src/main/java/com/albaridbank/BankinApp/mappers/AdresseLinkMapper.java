package com.albaridbank.BankinApp.mappers;

import com.albaridbank.BankinApp.dtos.AdressLinkDTO;
import com.albaridbank.BankinApp.models.AdresseLink;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper optimisé pour l'affichage des adresses
 */
@Mapper(
        componentModel = "spring"
)
public interface AdresseLinkMapper {
    /**
     * Convertit une entité AdresseLink en DTO pour l'affichage
     */
    @Mapping(source = "numeiden", target = "id")
    @Mapping(source = "client.idenclie", target = "clientId")
    @Mapping(source = "compte.idencomp", target = "compteId")
    @Mapping(source = "codtypad", target = "addressType")
    @Mapping(source = "codcatad", target = "addressCategory")
    @Mapping(source = "intitule1", target = "line1")
    @Mapping(source = "intitule2", target = "line2")
    @Mapping(source = "intitule3", target = "line3")
    @Mapping(source = "intitule4", target = "line4")
    @Mapping(source = "codepays", target = "countryCode")
    @Mapping(source = "codevill", target = "cityCode")
    @Mapping(source = "codepost", target = "postalCode")
    @Mapping(source = "libeville", target = "cityName")
    @Mapping(source = "datogmaj", target = "lastUpdateDate")
    AdressLinkDTO toDto(AdresseLink adresseLink);

    /**
     * Convertit une liste d'entités AdresseLink en liste de DTOs
     */
    List<AdressLinkDTO> toDtoList(List<AdresseLink> adresseLinks);

}

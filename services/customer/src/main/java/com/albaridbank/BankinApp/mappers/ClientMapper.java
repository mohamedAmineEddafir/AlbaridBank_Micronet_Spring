package com.albaridbank.BankinApp.mappers;

import com.albaridbank.BankinApp.dtos.ClientDTO;
import com.albaridbank.BankinApp.models.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * Mapper optimisé pour l'affichage des clients et leurs comptes
 */
@Mapper(
        componentModel = "spring",
        uses = {CompteMapper.class, AdresseLinkMapper.class}
)
public interface ClientMapper {

    /**
     * Convertit une entité Client en DTO pour l'affichage
     */
    @Mapping(source = "idenclie", target = "clientId")
    @Mapping(source = "nomclie", target = "lastName")
    @Mapping(source = "prenomcl", target = "firstName")
    @Mapping(source = "adremail", target = "email")
    @Mapping(source = "datnaiss", target = "birthDate")
    @Mapping(source = "lieunais", target = "birthPlace")
    @Mapping(source = "codetati", target = "status")
    // Mappings pour Situation Juridique
    @Mapping(source = "situationJuridiqu.codsitju", target = "legalSituationCode")
    @Mapping(source = "situationJuridiqu.libsitju", target = "legalSituationName")
    // Mappings pour CateSocioProf
    @Mapping(source = "cateSocioProf.codsocpr", target = "socioProfessionalCode")
    @Mapping(source = "cateSocioProf.libcsocpr", target = "socioProfessionalName")
    // Collections
    @Mapping(source = "comptes", target = "accounts")
    @Mapping(source = "adresses", target = "addresses")
    ClientDTO toDto(Client client);

    /**
     * Convertit une liste d'entités Client en liste de DTOs
     */
    List<ClientDTO> toDtoList(List<Client> clients);
    /**
    * Convertit une liste DTOs en liste d'entités Client (but i don't need this method know because i don't need to convert a list of DTOs to a list of entities this only for Save method)
    */
    // List<Client> toEntityList(List<ClientDTO> clientDTOs);
}
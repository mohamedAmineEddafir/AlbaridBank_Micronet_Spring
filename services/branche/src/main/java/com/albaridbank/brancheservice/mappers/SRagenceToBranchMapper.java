package com.albaridbank.brancheservice.mappers;

import com.albaridbank.brancheservice.dto.BranchDTO;
import com.albaridbank.brancheservice.dto.BranchSimpleDTO;
import com.albaridbank.brancheservice.model.SgcRefAgence;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper for converting between {@link SgcRefAgence} entity and {@link BranchDTO}.
 * Uses MapStruct for automatic mapping implementation, with custom mappings specified
 * for fields that have different names in the entity and DTO.
 *
 * @author Mohamed Amine Eddafir
 */
@Mapper(
        componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface SRagenceToBranchMapper {

    /**
     * Converts a {@link SgcRefAgence} entity to a {@link BranchDTO}.
     *
     * @param branch The source entity object
     * @return The mapped DTO
     */
    @Mapping(source = "codburpo", target = "codeAgence")
    @Mapping(source = "libelleBurpo", target = "nomAgence")
    @Mapping(source = "typbur", target = "typeAgence")
    @Mapping(source = "libelleRegion", target = "region")
    @Mapping(source = "codreg", target = "codeRegion")
    @Mapping(source = "libelleGroupe", target = "groupe")
    @Mapping(source = "libelleLocalite", target = "localite")
    @Mapping(source = "catagence", target = "catAgence")
    @Mapping(source = "datedeploiement", target = "dateDeploiement")
    @Mapping(source = "adeployer", target = "aDeployer")
    @Mapping(source = "typeconnexion", target = "typeConnexion")
    @Mapping(source = "codepostale", target = "codePostal")
    @Mapping(source = "telephoneFixe", target = "telephoneFixe")
    @Mapping(source = "telephoneMobile", target = "telephoneMobile")
    BranchDTO toDto(SgcRefAgence branch);

    /**
     * Converts a list of {@link SgcRefAgence} entities to a list of {@link BranchDTO}.
     *
     * @param branches The source entity list
     * @return The mapped DTO list
     */
    List<BranchDTO> toDtoList(List<SgcRefAgence> branches);

    /**
     * Converts a {@link SgcRefAgence} entity to a {@link BranchSimpleDTO} for simplified data transfer.
     *
     * @param entity The source entity object
     * @return The mapped simplified DTO
     */
    @Mapping(source = "codburpo", target = "codeAgence")
    @Mapping(source = "libelleBurpo", target = "nomAgence")
    @Mapping(source = "libelleRegion", target = "region")
    @Mapping(source = "telephoneFixe", target = "telephone")
    BranchSimpleDTO toSimpleDto(SgcRefAgence entity);

    /**
     * Converts a list of {@link SgcRefAgence} entities to a list of {@link BranchSimpleDTO}.
     *
     * @param simpleBranches The source entity list
     * @return The mapped simplified DTO list
     */
    List<BranchSimpleDTO> toSimpleDtoList(List<SgcRefAgence> simpleBranches);

    /*
     * Converts a {@link BranchDTO} to a {@link SgcRefAgence} entity.
     *
     * @param dto The source DTO object
     * @return The mapped entity
     **/
    //@InheritInverseConfiguration(name = "toDto")
    //@Mapping(target = "id", ignore = true) // cette ligne si mon entité a un ID généré automatiquement
    //SgcRefAgence toEntity(BranchDTO dto);
}
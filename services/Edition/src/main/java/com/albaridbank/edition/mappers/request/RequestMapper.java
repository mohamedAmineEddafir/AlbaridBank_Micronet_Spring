package com.albaridbank.edition.mappers.request;

import com.albaridbank.edition.dto.request.EncoursGlobalRequestDTO;
import com.albaridbank.edition.dto.request.MouvementVeilleRequestDTO;
import com.albaridbank.edition.dto.request.PortefeuilleCENRequestDTO;
import com.albaridbank.edition.dto.request.PortefeuilleClientCCPRequestDTO;
import org.mapstruct.*;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RequestMapper {

    @Mapping(target = "codeBureau", source = "codeBureauPoste")
    @Mapping(target = "joursAvant", source = "joursPrecedents")
    @Mapping(target = "montantMinimum", source = "montantMinimum")
    MouvementVeilleRequestDTO toMouvementVeilleRequest(Long codeBureauPoste, Integer joursPrecedents, java.math.BigDecimal montantMinimum);

    @Mapping(target = "codeBureau", source = "codeBureauPoste")
    EncoursGlobalRequestDTO toEncoursGlobalRequest(Long codeBureauPoste);

    @Mapping(target = "codeBureau", source = "codeBureauPoste")
    @Mapping(target = "filtreEtat", source = "etatCompte")
    @Mapping(target = "soldeMinimum", source = "montantMinimum")
    PortefeuilleClientCCPRequestDTO toPortefeuilleClientCCPRequest(Long codeBureauPoste, String etatCompte, java.math.BigDecimal montantMinimum);

    @Mapping(target = "codeBureau", source = "codeBureauPoste")
    @Mapping(target = "filtreEtat", source = "etatCompte")
    PortefeuilleCENRequestDTO toPortefeuilleCENRequest(Long codeBureauPoste, String etatCompte);
}
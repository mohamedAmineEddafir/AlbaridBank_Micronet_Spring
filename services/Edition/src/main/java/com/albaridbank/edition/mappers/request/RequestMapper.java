package com.albaridbank.edition.mappers.request;

import com.albaridbank.edition.dto.request.EncoursGlobalRequestDTO;
import com.albaridbank.edition.dto.request.MouvementVeilleRequestDTO;
import com.albaridbank.edition.dto.request.PortefeuilleClientCCPRequestDTO;
import com.albaridbank.edition.dto.request.PortefeuilleCENRequestDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.math.BigDecimal;

/**
 * Mapper pour convertir les paramètres de requêtes en DTOs de requête.
 * Ce mapper permet de transformer les paramètres reçus par les contrôleurs en objets
 * de transfert de données utilisés par les services.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RequestMapper {

    /**
     * Convertit les paramètres de requête en DTO pour les mouvements de veille.
     *
     * @param codeBureauPoste Identifiant du bureau postal
     * @param joursPrecedents Nombre de jours précédents à considérer
     * @param montantMinimum Montant minimum des mouvements
     * @return Un DTO de requête pour les mouvements de veille
     */
    @Mapping(target = "codeBureau", source = "codeBureauPoste")
    @Mapping(target = "joursAvant", source = "joursPrecedents")
    @Mapping(target = "montantMinimum", source = "montantMinimum")
    MouvementVeilleRequestDTO toMouvementVeilleRequest(Long codeBureauPoste, Integer joursPrecedents, BigDecimal montantMinimum);

    /**
     * Convertit l'identifiant du bureau en DTO pour les encours globaux.
     *
     * @param codeBureauPoste Identifiant du bureau postal
     * @return Un DTO de requête pour les encours globaux
     */
    @Mapping(target = "codeBureau", source = "codeBureauPoste")
    EncoursGlobalRequestDTO toEncoursGlobalRequest(Long codeBureauPoste);

    /**
     * Convertit les paramètres de requête en DTO pour le portefeuille client CCP.
     * Note: Les propriétés ont été adaptées en fonction des champs disponibles dans PortefeuilleClientCCPRequestDTO.
     *
     * @param codeBureauPoste Identifiant du bureau postal
     * @param etatCompte État du compte (tri)
     * @param montantMinimum Montant minimum
     * @return Un DTO de requête pour le portefeuille client CCP
     */
    @Mapping(target = "codeBureau", source = "codeBureauPoste")
    @Mapping(target = "triPar", source = "etatCompte")
    PortefeuilleClientCCPRequestDTO toPortefeuilleClientCCPRequest(Long codeBureauPoste, String etatCompte, BigDecimal montantMinimum);

    /**
     * Convertit les paramètres de requête en DTO pour le portefeuille CEN.
     * Note: La propriété filtreEtat a été supprimée car elle n'existe pas dans PortefeuilleCENRequestDTO.
     *
     * @param codeBureauPoste Identifiant du bureau postal
     * @param etatCompte État du compte (non utilisé)
     * @return Un DTO de requête pour le portefeuille CEN
     */
    @Mapping(target = "codeBureau", source = "codeBureauPoste")
    PortefeuilleCENRequestDTO toPortefeuilleCENRequest(Long codeBureauPoste, String etatCompte);
}
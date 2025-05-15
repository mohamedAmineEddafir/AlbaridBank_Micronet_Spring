package com.albaridbank.edition.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortefeuilleClientCCPDetailDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private Long idencomp;           // Compte
    private String inticomp;         // Nom et Prénom
    private String adrecomp;         // Adresse
    private String codepost;         // Code Postal
    private String inticond;         // Intitulé Condensé
    private String libsocpr;         // Cat. Soc. Prof
    private String numpieid;         // CIN
    private String numetele;         // Tél
    private String etatCompte;       // État Compte (ACTIF/INACTIF)
    private LocalDate datenais;      // Date Naissance
    private BigDecimal soldcour;     // Solde Courant
    private BigDecimal soldoppo;     // Solde Opposition
    private BigDecimal soldtaxe;     // Solde Taxe
    private BigDecimal solddebo;     // Solde Débit Opérations
    private BigDecimal solddeco;     // Solde Crédit Opérations
    private BigDecimal solopede;     // Solde Opérations Période
    private BigDecimal soldcert;     // Solde Certifié
    private LocalDate datesold;      // Date Solde
    private Integer comptetyp;       // Type Compte
    private String typeCompteLibelle; // Libellé Type Compte
}
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
public class CompteCCPDetailDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long idencomp;           // Compte
    private String inticomp;         // Nom et Prénom
    private String adrecomp;         // Adresse
    private String libsocpr;         // Cat. Soc. Prof
    private String numpieid;         // CIN
    private String numetele;         // Tél
    private String etatCompte;       // Etat Compte (ACTIF/INACTIF)
    private LocalDate datenais;      // Date Naissance
    private BigDecimal soldcour;     // Solde
    private Integer comptetyp;       // Type Compte
    private String typeCompteLibelle; // Type Compte libellé
}

package com.albaridbank.edition.dto.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteCENDetailDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long idencomp;           // N° Compte
    private String inticomp;         // Nom et prénom
    private String adrecomp;         // Adresse
    private String licasopr;         // Categ.Socio.Prof
    private String datenais;         // Date.Nais
    private String numpieid;         // CIN
    private String numetele;         // TELE
    private String etatCompte;       // ETAT COMPTE (Actif/Inactif)
    private BigDecimal soldminu;     // SOLDE
    private Integer durecomp;        // Pour déterminer si compte actif/inactif
}

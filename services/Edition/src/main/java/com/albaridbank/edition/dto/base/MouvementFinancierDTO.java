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
public class MouvementFinancierDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long idencomp;         // N° Compte
    private String inticomp;       // Nom et prénom du titulaire
    private Long codburpo;         // Code bureau poste
    private String desburpo;       // Agence de l'opération
    private String libtypop;       // Type d'opération
    private String sensmouv;       // Sens du mouvement (D/C)
    private BigDecimal montmouv;   // Montant du mouvement
}

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
public class MouvementCENDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long idencomp;         // N° Compte
    // private String inticomp; Nom et prénom du titulaire
    private String libtypop;       // Type d'opération
    private String sensMouvement;  // ID_constant_ (D/C)
    private BigDecimal montoper;   // Montant de l'opération
}

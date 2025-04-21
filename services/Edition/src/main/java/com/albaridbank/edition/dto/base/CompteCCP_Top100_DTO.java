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
public class CompteCCP_Top100_DTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long idencomp;           // Compte
    private String inticomp;         // Nom et Prénom
    private String adrecomp;         // Adresse
    private String libsocpr;         // Cat. Soc. Prof
    private BigDecimal soldcour;     // Solde
}
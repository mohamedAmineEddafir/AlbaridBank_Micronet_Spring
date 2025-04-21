package com.albaridbank.edition.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MouvementVeilleRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long codeBureau;       // Code de l'agence (Parameter1)
    private Integer joursAvant;    // 1 pour veille, 2 pour avant-veille
    private BigDecimal montantMinimum; // Montant minimum optionnel (filtrage côté service)
}

package com.albaridbank.edition.model.cen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteCENId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Integer codeProduit;
    private Long idCompte;
}

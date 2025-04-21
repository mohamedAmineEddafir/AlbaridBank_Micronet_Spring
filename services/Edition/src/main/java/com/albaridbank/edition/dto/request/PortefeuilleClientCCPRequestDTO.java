package com.albaridbank.edition.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortefeuilleClientCCPRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long codeBureau;       // Code de l'agence (Parameter1)
    private String triPar;         // Critère de tri (par défaut "soldcour")
    private String ordreTri;       // Ordre de tri (par défaut "DESC")
    private Integer[] codesProduit; // Types de comptes à inclure (par défaut [1,2,null])
}

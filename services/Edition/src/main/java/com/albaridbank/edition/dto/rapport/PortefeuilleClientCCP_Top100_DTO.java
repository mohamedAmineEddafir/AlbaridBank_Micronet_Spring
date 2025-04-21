package com.albaridbank.edition.dto.rapport;

import com.albaridbank.edition.dto.base.CompteCCP_Top100_DTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortefeuilleClientCCP_Top100_DTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    // Informations d'en-tête
    private String titreRapport;
    private LocalDateTime dateEdition;
    private String numeroPage;

    // Données du rapport
    private List<CompteCCP_Top100_DTO> comptes;
    private Integer nombreTotalComptes;
    private BigDecimal encoursTotalComptes;
}

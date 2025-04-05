package com.albaridbank.edition.dto;

import com.albaridbank.edition.model.emuns.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;


/**
 * <h4> Afficher les métadonnées des rapports disponibles </h4>
 *
 * @author Mohamed Amine Eddafir
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RapportDTO {
    private Long id;
    private String titre;
    private String description;
    private ReportType typeRapport;
    private LocalDateTime dateCreation;
    private String nomFichier;
    private String formatSouhaite;  // "PDF", "EXCEL" ou "PDF,EXCEL"
    private List<ParametreRapportDTO> parametres;
}
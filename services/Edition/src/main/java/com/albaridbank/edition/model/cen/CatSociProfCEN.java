package com.albaridbank.edition.model.cen;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"catSociProfCEN\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatSociProfCEN {

    @Id
    @Column(name = "cocasopr")
    private Long codeSocioProf;

    @Column(name = "licasopr")
    private String libelle;

    @Column(name = "codcatcl")
    private Float codeCategorie; // SMALLFLOAT dans Informix correspond à Float en Java
}

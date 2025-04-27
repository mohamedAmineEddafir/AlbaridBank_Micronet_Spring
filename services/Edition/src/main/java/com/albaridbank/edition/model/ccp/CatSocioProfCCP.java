package com.albaridbank.edition.model.ccp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "catSocioProfCCP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CatSocioProfCCP {

    @Id
    @Column(name = "codsocpr")
    private Long codeSocioProf;

    @Column(name = "libsocpr")
    private String libelle;

    @Column(name = "codtyppe")
    private String codeTypePersonne;

    @Column(name = "cosfacas")
    private Integer codeSousFacture;

    @Column(name = "codcatcl")
    private Integer codeCategorie;

    @Column(name = "cofacasp")
    private Integer codeFactureASP;
}
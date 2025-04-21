package com.albaridbank.edition.model.ccp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "\"bureauPosteCCP\"")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BureauPosteCCP {

    @Id
    @Column(name = "codburpo")
    private Long codeBureau;

    @Column(name = "desburpo")
    private String designation;

    @Column(name = "adrburpo")
    private String adresse;

    @Column(name = "codepost")
    private String codePostal;

    @Column(name = "numetele")
    private String telephone;

    @Column(name = "nume_fax")
    private String fax;

    @Column(name = "coderegi")
    private Integer codeRegion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cobupora", referencedColumnName = "codburpo")
    private BureauPosteCCP bureauParent;
}
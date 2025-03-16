package com.albaridbank.BankinApp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "adresse_link")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdresseLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numeiden")
    private Long numeiden;

    @ManyToOne // many addresses can belong to one client
    @JoinColumn(name = "idenclie", nullable = false)
    private Client client;

    @ManyToOne // many addresses can belong to one account
    @JoinColumn(name = "idencomp")
    private Compte compte;

    @Column(name = "codtypad", nullable = false, length = 2)
    private String codtypad;

    @Column(name = "codcatad", nullable = false, length = 2)
    private String codcatad;

    @Column(name = "intitule1", length = 120)
    private String intitule1;

    @Column(name = "intitule2", length = 60)
    private String intitule2;

    @Column(name = "intitule3", length = 60)
    private String intitule3;

    @Column(name = "intitule4", length = 60)
    private String intitule4;

    @Column(name = "codepays", length = 2)
    private String codepays;

    @Column(name = "codevill")
    private Integer codevill;

    @Column(name = "codepost", length = 5)
    private String codepost;

    @Column(name = "codburpo")
    private Long codburpo;

    @Column(name = "datogmaj")
    @Temporal(TemporalType.DATE)
    private Date datogmaj;

    @Column(name = "codburog")
    private Long codburog;

    @Column(name = "numordog")
    private Long numordog;

    @Column(name = "libeville", length = 100)
    private String libeville;
}
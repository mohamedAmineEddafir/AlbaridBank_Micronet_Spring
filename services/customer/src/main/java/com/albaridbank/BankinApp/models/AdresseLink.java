package com.albaridbank.BankinApp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "adresse_link", indexes = {
        @Index(name = "idx_adresselink_idenclie", columnList = "idenclie"),
        @Index(name = "idx_adresselink_idencomp", columnList = "idencomp")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdresseLink implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "numeiden")
    private Long numeiden;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idenclie", nullable = false)
    @JsonIgnoreProperties({"adresses", "hibernateLazyInitializer", "handler"})
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idencomp")
    @JsonIgnoreProperties({"adresseLinks", "hibernateLazyInitializer", "handler"})
    private Compte compte;

    @NotBlank(message = "Address type code cannot be empty")
    @Column(name = "codtypad", nullable = false, length = 2)
    private String codtypad;

    @NotBlank(message = "Address category code cannot be empty")
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
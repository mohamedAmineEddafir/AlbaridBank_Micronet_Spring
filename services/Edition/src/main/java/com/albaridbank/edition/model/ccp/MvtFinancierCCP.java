package com.albaridbank.edition.model.ccp;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "\"mvtFinancierCCP\"") // PostgreSQL requires double quotes for case sensitivity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MvtFinancierCCP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cptemouv")
    private CompteCCP compte;

    @Column(name = "datemouv")
    private LocalDate dateMouvement;

    @Column(name = "numemouv")
    private Integer numeroMouvement;

    @Column(name = "sensmouv")
    private String sens;

    @Column(name = "montmouv")
    private BigDecimal montant;

    @Column(name = "datevale")
    private LocalDate dateValeur;

    @Column(name = "solddepa")
    private BigDecimal soldeDepartCompte;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codburpo")
    private BureauPosteCCP bureauPoste;

    @Column(name = "numeordr")
    private Long numeroOrdre;

    @Column(name = "dateoper")
    private LocalDate dateOperation;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codtypop")
    private TypeOperationCCP typeOperation;

    @Column(name = "refopeor")
    private String referenceOperationOriginale;

    @Column(name = "menmarmo")
    private String memoMouvement;

    @Column(name = "natumouv")
    private String natureMouvement;

    @Column(name = "nom_tire")
    private String nomTireur;

    @Column(name = "datcreatemvt")
    private LocalDateTime dateCreation;
}
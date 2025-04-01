package com.albaridbank.edition.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import jakarta.persistence.*;

@Entity
@Table(name = "parametre_rapport")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ParametreRapport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_rapport", nullable = false)
    @ToString.Exclude
    private Rapport rapport;

    @Column(name = "nom", length = 50, nullable = false)
    private String nom;

    @Column(name = "valeur", length = 200)
    private String valeur;

    @Column(name = "type", length = 20)
    private String type;
}
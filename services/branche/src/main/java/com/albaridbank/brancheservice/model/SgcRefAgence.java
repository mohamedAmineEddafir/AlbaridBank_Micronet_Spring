package com.albaridbank.brancheservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Entity class representing a branch/agency.
 * This class is mapped to the "sgc_ref_agence" table in the database.
 *
 * @author Mohamed Amine Eddafir
 */
@Entity
@Table(name = "sgc_ref_agence", indexes = {
        @Index(name = "idx_sgc_ref_agence_codreg", columnList = "codreg"),
        @Index(name = "idx_sgc_ref_agence_typbur", columnList = "typbur")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SgcRefAgence implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The unique identifier/code for the branch.
     * This field is required and has a maximum length of 6 characters.
     */
    @Id
    @Column(name = "codburpo", length = 6, nullable = false)
    private String codburpo;

    /**
     * The region code of the branch.
     */
    @Column(name = "codreg")
    private Integer codreg;

    /**
     * The region name of the branch.
     */
    @Column(name = "libreg", length = 200)
    private String libelleRegion;

    /**
     * The type of branch.
     */
    @Column(name = "typbur", length = 10)
    private String typbur;

    /**
     * DSN chain information.
     */
    @Column(name = "chaindsn", length = 20)
    private String chaindsn;

    /**
     * IP address of the branch.
     */
    @Column(name = "ip", length = 20, nullable = false)
    private String ip;

    /**
     * Creation source code.
     */
    @Column(name = "src_cre", length = 4)
    private String srcCre;

    /**
     * Pole code.
     */
    @Column(name = "pole", length = 2)
    private String pole;

    /**
     * Zone code of the branch.
     */
    @Column(name = "codezone", length = 3)
    private String codezone;

    /**
     * Zone name of the branch.
     */
    @Column(name = "libelle_zone", length = 50)
    private String libelleZone;

    /**
     * Group code of the branch.
     */
    @Column(name = "codegroupe", length = 6)
    private String codegroupe;

    /**
     * Group name of the branch.
     */
    @Column(name = "libelle_groupe", length = 50)
    private String libelleGroupe;

    /**
     * Locality code of the branch.
     */
    @Column(name = "codelocalite", length = 3)
    private String codelocalite;

    /**
     * Locality name of the branch.
     */
    @Column(name = "libelle_localite", length = 50)
    private String libelleLocalite;

    /**
     * Category of the branch.
     */
    @Column(name = "categorie", length = 3)
    private String categorie;

    /**
     * Responsible person for the branch.
     */
    @Column(name = "responsable", length = 200)
    private String responsable;

    /**
     * Remittance indicator.
     */
    @Column(name = "remontee", length = 1)
    private String remontee;

    /**
     * Status of the branch.
     */
    @Column(name = "statut", length = 1)
    private String statut;

    /**
     * Related branch code.
     */
    @Column(name = "codburpo_ra", length = 6)
    private String codburpoRa;

    /**
     * Observation or notes about the branch.
     */
    @Column(name = "observation", length = 100)
    private String observation;

    /**
     * Connection type.
     */
    @Column(name = "typeconnexion", length = 5)
    private String typeconnexion;

    /**
     * BKAM locality code.
     */
    @Column(name = "codelocbkam", length = 4)
    private String codelocbkam;

    /**
     * BKAM locality name.
     */
    @Column(name = "liblocbkam", length = 50)
    private String liblocbkam;

    /**
     * BKAM region code.
     */
    @Column(name = "codregbkam", length = 2)
    private String codregbkam;

    /**
     * BKAM region name.
     */
    @Column(name = "libregbkam", length = 50)
    private String libregbkam;

    /**
     * Address of the branch.
     */
    @Column(name = "adresse", length = 200)
    private String adresse;

    /**
     * BKAM branch code.
     */
    @Column(name = "codburbkam", length = 5)
    private String codburbkam;

    /**
     * SRBM code.
     */
    @Column(name = "codesrbm", length = 2)
    private String codesrbm;

    /**
     * BKAM code.
     */
    @Column(name = "codebkam", length = 4)
    private String codebkam;

    /**
     * IPS information.
     */
    @Column(name = "ips", length = 10)
    private String ips;

    /**
     * Signature information.
     */
    @Column(name = "sig", length = 10)
    private String sig;

    /**
     * Postal code of the branch.
     */
    @Column(name = "codepostale", length = 10)
    private String codepostale;

    /**
     * Fixed telephone number of the branch.
     */
    @Column(name = "telephone_fixe", length = 15)
    private String telephoneFixe;

    /**
     * Mobile telephone number of the branch.
     */
    @Column(name = "telephone_mobile", length = 15)
    private String telephoneMobile;

    /**
     * Agency category.
     */
    @Column(name = "catagence", length = 10)
    private String catagence;

    /**
     * Western Union branch code.
     */
    @Column(name = "codburpo_wu", length = 10)
    private String codburpoWu;

    /**
     * Branch name.
     */
    @Column(name = "libelle_burpo", length = 200)
    private String libelleBurpo;

    /**
     * Pole code.
     */
    @Column(name = "code_pole")
    private Integer codePole;

    /**
     * Deployment status.
     */
    @Column(name = "deploye", length = 1)
    private String deploye;

    /**
     * Deployment date.
     */
    @Column(name = "datedeploiement")
    @Temporal(TemporalType.DATE)
    private LocalDate datedeploiement;

    /**
     * To be deployed indicator.
     */
    @Column(name = "adeployer", length = 1)
    private String adeployer;

    /**
     * Receiver registration number.
     */
    @Column(name = "matriculereceveur", length = 10)
    private String matriculereceveur;

    /**
     * Agency type.
     */
    @Column(name = "typeagence")
    private Integer typeagence;

    /**
     * Fax number of the branch.
     */
    @Column(name = "faxe", length = 20)
    private String faxe;

    /**
     * Email address of the branch.
     */
    @Column(name = "email", length = 20)
    private String email;
}
package com.albaridbank.BankinApp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "client", indexes = {
        @Index(name = "idx_client_statclie", columnList = "statclie"),
        @Index(name = "idx_client_typeclie", columnList = "typeclie"),
        @Index(name = "idx_client_nomrais", columnList = "nomrais")
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString(exclude = {"comptes", "adresses"}) // Prevent circular references in toString()
public class Client implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "idenclie", nullable = false, precision = 10)
    private BigDecimal idenclie;

    @Column(name = "cateclie", nullable = false)
    private Integer cateclie;

    @Column(name = "typeclie", length = 5)
    private String typeclie;

    @NotBlank(message = "Client name cannot be empty")
    @Column(name = "nomrais", nullable = false, length = 120)
    private String nomrais;

    @Column(name = "prenclie", length = 20)
    private String prenclie;

    @Column(name = "civiclie", length = 5)
    private String civiclie;

    @Column(name = "nume_telb", length = 15)
    private String numeTelb;

    @Column(name = "nume_teld", length = 15)
    private String numeTeld;

    @Column(name = "nume_gsm", length = 15)
    private String numeGsm;

    @Column(name = "nume_fax", length = 15)
    private String numeFax;

    @Column(name = "datenais")
    @Temporal(TemporalType.DATE)
    private Date datenais;

    @Column(name = "lieunais", length = 30)
    private String lieunais;

    @Column(name = "sexeclie", length = 2)
    private String sexeclie;

    @Column(name = "typepiec", length = 1)
    private String typepiec;

    @Column(name = "numpieid", length = 15)
    private String numpieid;

    @Column(name = "datpieid")
    @Temporal(TemporalType.DATE)
    private Date datpieid;

    @Column(name = "revemens", precision = 15, scale = 2)
    private BigDecimal revemens;

    @Column(name = "situfami")
    private Integer situfami;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "clients"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "situjuri", referencedColumnName = "codsitju")
    private SituationJuridiqu situationJuridiqu;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler", "clients"})
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "codsocpr", referencedColumnName = "codsocpr")
    private CateSocioProf cateSocioProf;

    @Column(name = "sectacti", length = 5)
    private String sectacti;

    @Column(name = "ageneco", length = 5)
    private String ageneco;

    @Column(name = "codforju")
    private Integer codforju;

    @Column(name = "statclie")
    private Integer statclie;

    @Column(name = "numregco", length = 20)
    private String numregco;

    @Email(message = "Invalid email format")
    @Column(name = "adremail", length = 60)
    private String adremail;

    @Column(name = "numepate", length = 11)
    private String numepate;

    @Column(name = "numevisa", length = 11)
    private String numevisa;

    @Column(name = "capisoci", precision = 14)
    private BigDecimal capisoci;

    @Column(name = "nombempl")
    private Integer nombempl;

    @Column(name = "centrc", length = 4)
    private String centrc;

    @Column(name = "titre", length = 2)
    private String titre;

    @Column(name = "domisala", length = 1)
    private String domisala;

    @Column(name = "misecont", length = 1)
    private String misecont;

    @Column(name = "segment")
    private Integer segment;

    @Column(name = "cotarisq", length = 8)
    private String cotarisq;

    @Column(name = "datecrea")
    @Temporal(TemporalType.DATE)
    private Date datecrea;

    @Column(name = "datogmaj")
    @Temporal(TemporalType.DATE)
    private Date datogmaj;

    @Column(name = "proplog", length = 1)
    private String proplog;

    @Column(name = "idenbam", length = 12)
    private String idenbam;

    @Column(name = "numcenrs", length = 15)
    private String numcenrs;

    @Column(name = "agegescl", precision = 5)
    private BigDecimal agegescl;

    @Column(name = "usegescl", length = 7)
    private String usegescl;

    @Column(name = "codetati")
    private Integer codetati;

    @Column(name = "codpayrs", length = 2)
    private String codpayrs;

    @Column(name = "codenate", length = 2)
    private String codenate;

    @Column(name = "donneerro", length = 1)
    private String donneerro;

    @Column(name = "cliefiab", nullable = false)
    private Short cliefiab;

    @Column(name = "cliepros", nullable = false)
    private Short cliepros;

    @Column(name = "nompere", length = 50)
    private String nompere;

    @Column(name = "nommere", length = 50)
    private String nommere;

    @Column(name = "precture")
    private Integer precture;

    @Column(name = "patente", length = 50)
    private String patente;

    @Column(name = "codpaysnaiss", length = 2)
    private String codpaysnaiss;

    @Column(name = "codpayspass", length = 2)
    private String codpayspass;

    @Email(message = "Invalid email format")
    @Column(name = "adressemailnov", length = 120)
    private String adressemailnov;

    @Column(name = "code_fdc_user")
    private Integer codeFdcUser;

    @Column(name = "code_info_clt")
    private Integer codeInfoClt;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties("client")
    private List<Compte> comptes;

    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonIgnoreProperties("client")
    private List<AdresseLink> adresses;
}
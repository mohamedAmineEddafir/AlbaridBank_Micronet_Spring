package com.albaridbank.BankinApp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cate_socio_prof")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CateSocioProf implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "codsocpr", nullable = false, precision = 3)
    private BigDecimal codsocpr;

    @NotBlank(message = "Social professional category name cannot be empty")
    @Column(name = "libsocpr", nullable = false, length = 200)
    private String libsocpr;

    @Column(name = "cofacasp", nullable = false)
    private Integer cofacasp;

    @Column(name = "datdebeff", nullable = false)
    @Temporal(TemporalType.DATE)
    private Date datdebeff;

    @Column(name = "datfineff")
    @Temporal(TemporalType.DATE)
    private Date datfineff;

    @OneToMany(mappedBy = "cateSocioProf", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("cateSocioProf")
    private List<Client> clients;
}
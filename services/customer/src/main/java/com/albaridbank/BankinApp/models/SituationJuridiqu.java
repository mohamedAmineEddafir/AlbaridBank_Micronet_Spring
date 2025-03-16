package com.albaridbank.BankinApp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "situation_juridiqu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SituationJuridiqu implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @NotNull(message = "Legal situation code cannot be null")
    @Column(name = "codsitju", nullable = false, precision = 2)
    private BigDecimal codsitju;

    @Column(name = "libesitu", length = 32)
    private String libesitu;

    @Column(name = "datdebeff")
    @Temporal(TemporalType.DATE)
    private Date datdebeff;

    @Column(name = "datfineff")
    @Temporal(TemporalType.DATE)
    private Date datfineff;

    @OneToMany(mappedBy = "situationJuridiqu", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("situationJuridiqu")
    private List<Client> clients;
}
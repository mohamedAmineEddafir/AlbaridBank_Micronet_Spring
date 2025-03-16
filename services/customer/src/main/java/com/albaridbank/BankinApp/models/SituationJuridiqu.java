package com.albaridbank.BankinApp.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "situation_juridiqu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SituationJuridiqu {

    @Id
    @Column(name = "codsitju", nullable = false)
    private Integer codsitju;

    @Column(name = "libesitu", length = 32)
    private String libesitu;

    @Column(name = "datdebeff")
    @Temporal(TemporalType.DATE)
    private Date datdebeff;

    @Column(name = "datfineff")
    @Temporal(TemporalType.DATE)
    private Date datfineff;

    @OneToMany(mappedBy = "situationJuridiqu")
    private List<Client> clients;
}
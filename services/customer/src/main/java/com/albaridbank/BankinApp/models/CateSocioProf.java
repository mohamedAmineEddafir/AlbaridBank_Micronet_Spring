package com.albaridbank.BankinApp.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Table(name = "cate_socio_prof")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CateSocioProf {

    @Id
    @Column(name = "codsocpr", nullable = false)
    private Long codsocpr;

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

    @OneToMany(mappedBy = "cateSocioProf") // one cate socio prof can classify many clients
    private List<Client> clients;

}

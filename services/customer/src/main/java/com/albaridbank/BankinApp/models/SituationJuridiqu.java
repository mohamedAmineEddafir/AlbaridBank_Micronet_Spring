package com.albaridbank.BankinApp.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Entity class representing a legal situation.
 * This class is mapped to the "situation_juridiqu" table in the database.
 * Implements Serializable for object serialization.
 */
@Entity
@Table(name = "situation_juridiqu")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SituationJuridiqu implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * Unique identifier for the legal situation.
     * Cannot be null.
     */
    @Id
    @NotNull(message = "Legal situation code cannot be null")
    @Column(name = "codsitju", nullable = false, precision = 2)
    private BigDecimal codsitju;

    /**
     * Description of the legal situation.
     */
    @Column(name = "libesitu", length = 32)
    private String libesitu;

    /**
     * Start date of the legal situation's effectiveness.
     */
    @Column(name = "datdebeff")
    @Temporal(TemporalType.DATE)
    private Date datdebeff;

    /**
     * End date of the legal situation's effectiveness.
     */
    @Column(name = "datfineff")
    @Temporal(TemporalType.DATE)
    private Date datfineff;

    /**
     * List of clients associated with this legal situation.
     */
    @OneToMany(mappedBy = "situationJuridiqu", fetch = FetchType.LAZY)
    @JsonIgnoreProperties("situationJuridiqu")
    private List<Client> clients;
}
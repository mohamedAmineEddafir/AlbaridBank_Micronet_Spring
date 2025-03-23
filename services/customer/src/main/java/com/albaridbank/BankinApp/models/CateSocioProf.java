package com.albaridbank.BankinApp.models;

    import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
    import jakarta.persistence.*;
    import lombok.AllArgsConstructor;
    import lombok.Data;
    import lombok.NoArgsConstructor;
    import jakarta.validation.constraints.NotBlank;

    import java.io.Serial;
    import java.io.Serializable;
    import java.math.BigDecimal;
    import java.time.LocalDate;
    import java.util.List;

    /**
     * Entity class representing a socio-professional category.
     * This class is mapped to the "cate_socio_prof" table in the database.
     * Implements Serializable for object serialization.
     */
    @Entity
    @Table(name = "cate_socio_prof")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public class CateSocioProf implements Serializable {
        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * Unique identifier for the socio-professional category.
         */
        @Id
        @Column(name = "codsocpr", nullable = false, precision = 3)
        private BigDecimal codsocpr;

        /**
         * Name of the socio-professional category.
         * Cannot be empty.
         */
        @NotBlank(message = "Social professional category name cannot be empty")
        @Column(name = "libsocpr", nullable = false, length = 200)
        private String libsocpr;

        /**
         * Coefficient associated with the socio-professional category.
         */
        @Column(name = "cofacasp", nullable = false)
        private Integer cofacasp;

        /**
         * Start date of the socio-professional category's effectiveness.
         */
        @Column(name = "datdebeff", nullable = false)
        @Temporal(TemporalType.DATE)
        private LocalDate datdebeff;

        /**
         * End date of the socio-professional category's effectiveness.
         */
        @Column(name = "datfineff")
        @Temporal(TemporalType.DATE)
        private LocalDate datfineff;

        /**
         * List of clients associated with this socio-professional category.
         */
        @OneToMany(mappedBy = "cateSocioProf", fetch = FetchType.LAZY)
        @JsonIgnoreProperties("cateSocioProf")
        private List<Client> clients;
    }
package com.albaridbank.BankinApp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdressLinkDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String id;

    @NotNull(message = "Le type d'adresse est obligatoire")
    private String addressType; // codtypad

    private String addressCategory; // codcatad

    @NotBlank(message = "La ligne principale d'adresse ne peut pas être vide")
    private String line1; // intitule1

    private String line2; // intitule2
    private String line3; // intitule3
    private String line4; // intitule4

    @NotNull(message = "Le code pays est obligatoire")
    private String countryCode; // codepays

    private Integer cityCode; // codevill
    private String postalCode; // codepost
    private String cityName; // libeville

    // Références - seulement les IDs pour éviter les références circulaires
    private String clientId;
    private String compteId;

    private LocalDate lastUpdateDate; // datogmaj
    private String lastUpdateUser; // utilmaj

    // Champs supplémentaires spécifiques aux adresses
    private Character defaultAddress; // adresse par défaut
    private String phoneNumber;
    private String mobileNumber;
    private String emailAddress;
}
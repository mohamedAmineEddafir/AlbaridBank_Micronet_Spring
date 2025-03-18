// Path: src/main/java/com/albaridbank/BankinApp/dtos/ClientDTO.java
package com.albaridbank.BankinApp.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClientDTO {
    private String clientId;

    @NotBlank(message = "Client name is required")
    private String lastName;

    private String firstName;
    private String clientType;

    @Email(message = "Invalid email format")
    private String email;

    private String phoneNumber;
    private Integer status;
    private Date birthDate;
    private String birthPlace;

    // Situation Juridique
    private BigDecimal legalSituationCode;
    private String legalSituationName;
    // CateSocioProf
    private BigDecimal socioProfessionalCode;
    private String socioProfessionalName;

    // Related data (simplified for DTOs)
    // private List<AccountSummaryDTO> accounts;
    private List<CompteDTO> accounts;
    private List<AdressLinkDTO> addresses;
}
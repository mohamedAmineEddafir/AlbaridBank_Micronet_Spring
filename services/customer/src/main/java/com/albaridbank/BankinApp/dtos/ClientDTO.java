// Path: src/main/java/com/albaridbank/BankinApp/dtos/ClientDTO.java
package com.albaridbank.BankinApp.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
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

    // Reference data
    private String legalSituationCode;
    private String legalSituationName;
    private String socioProfessionalCode;
    private String socioProfessionalName;

    // Related data (simplified for DTOs)
    // private List<AccountSummaryDTO> accounts;
    private List<AdressLinkDTO> addresses;
}
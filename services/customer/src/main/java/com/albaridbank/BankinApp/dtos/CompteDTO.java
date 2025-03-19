package com.albaridbank.BankinApp.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    private String compteId;

    @NotNull(message = "Account category code is required")
    private String categoryCode;

    @NotNull(message = "Account subcategory code is required")
    private String subCategoryCode;

    // Client reference - using ID only to avoid circular references
    private String clientId;

    @NotBlank(message = "Account title cannot be empty")
    private String accountTitle;

    @NotNull(message = "Opening date is required")
    private LocalDate openingDate;

    private LocalDate closingDate;
    private LocalDate lastUpdateDate;

    private Character addressBlocked;
    private LocalDate accountStatusDate;

    private Integer signaturesCount;
    private String institutionCode;
    private String rateClass;

    @NotBlank(message = "Account status cannot be empty")
    private String accountStatus;

    private Short checkDigit;

    @NotNull(message = "Branch code is required")
    private String branchCode;

    private LocalDate repaymentDate;
    private Character mandateAccount;
    private Character overdrawnAccount;

    private String initialBranchCode;
    private String accountManager;
    private String commissionMode;
    private String productCode;
    private Character monitoredAccount;

    private String bookSerialNumber;
    private String relationshipType;
    private String releaseInterval;
    private String currencyCode;

    // Simplified list of addresses - optional
    private List<AdressLinkDTO> addresses;
}
package com.albaridbank.edition.model.cen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

// Classe ID composée
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperCompCENId implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private LocalDate dateOperation;
    private Long codeBureau;
    private Long numeroOrdre;
}

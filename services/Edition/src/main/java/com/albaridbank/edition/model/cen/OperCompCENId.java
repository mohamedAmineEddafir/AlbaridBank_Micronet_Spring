package com.albaridbank.edition.model.cen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents the composite primary key for the OperCompCEN entity.
 * This class is used to uniquely identify an OperCompCEN record in the database.
 * It implements {@link Serializable} to ensure the key can be serialized.
 *
 * @author Mohamed Amine Eddafir
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OperCompCENId implements Serializable {

    /**
     * Serial version UID for ensuring compatibility during serialization.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The date of the operation.
     */
    private LocalDate dateOperation;

    /**
     * The code of the bureau associated with the operation.
     */
    private Long codeBureau;

    /**
     * The unique order number of the operation.
     */
    private Long numeroOrdre;
}
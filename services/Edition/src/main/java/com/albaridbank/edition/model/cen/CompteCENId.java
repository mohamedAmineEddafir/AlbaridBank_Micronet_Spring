package com.albaridbank.edition.model.cen;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

/**
 * Represents the composite primary key for the CompteCEN entityƒƒ.
 * This class is used to uniquely identify a CompteCEN record in the database.
 * It implements {@link Serializable} to ensure the key can be serialized.
 *
 * @author Mohamed Amine Eddafir
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompteCENId implements Serializable {

    /**
     * Serial version UID for ensuring compatibility during serialization.
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * The product code associated with the account.
     */
    private Integer codeProduit;

    /**
     * The unique identifier of the account.
     */
    private Long idCompte;
}
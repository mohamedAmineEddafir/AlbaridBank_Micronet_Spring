package com.albaridbank.edition.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PortefeuilleCENRequestDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long codeBureau;       // Code de l'agence (Parameter1)
}

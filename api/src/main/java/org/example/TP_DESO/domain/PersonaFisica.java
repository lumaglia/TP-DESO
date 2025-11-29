package org.example.TP_DESO.domain;

import jakarta.persistence.*;

public class PersonaFisica extends ResponsablePago {

    @OneToOne(optional = false)
    @JoinColumn(name = "huesped_id",
            nullable = false,
            unique = true)
    private Huesped huesped;
}

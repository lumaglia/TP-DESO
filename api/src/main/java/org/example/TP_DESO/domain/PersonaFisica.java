package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "PersonaFisica")
@Getter
@Setter

public class PersonaFisica extends ResponsablePago {
    @OneToOne(optional = false)
    @JoinColumns({
            @JoinColumn(name = "huesped_tipoDoc", referencedColumnName = "tipoDoc", nullable = false),
            @JoinColumn(name = "huesped_nroDoc", referencedColumnName = "nroDoc", nullable = false)
    })
    private Huesped huesped;

}

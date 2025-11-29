package org.example.TP_DESO.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class PersonaJuridica extends ResponsablePago {
    private String razonSocial;
    private String cuit;
    private String telefono;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;
}

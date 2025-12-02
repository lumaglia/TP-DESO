package org.example.TP_DESO.domain;

import jakarta.persistence.CascadeType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class PersonaJuridica extends ResponsablePago {
    private String razonSocial;
    private String cuit;
    private String telefono;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumns({
            @JoinColumn(name = "domicilio", referencedColumnName = "domicilio"),
            @JoinColumn(name = "depto", referencedColumnName = "depto"),
            @JoinColumn(name = "codigo_postal", referencedColumnName = "codigoPostal"),
            @JoinColumn(name = "pais", referencedColumnName = "pais"),
    })
    private Direccion direccion;
}

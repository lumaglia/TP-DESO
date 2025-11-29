package org.example.TP_DESO.domain.Pks;

import java.io.Serializable;

public class DireccionId implements Serializable {
    private String domicilio;
    private String depto;
    private String codigoPostal;
    private String pais;

    public DireccionId() {}
    public DireccionId(String domicilio, String depto, String codigoPostal, String pais) {
        this.domicilio = domicilio;
        this.depto = depto;
        this.codigoPostal = codigoPostal;
        this.pais = pais;
    }
}
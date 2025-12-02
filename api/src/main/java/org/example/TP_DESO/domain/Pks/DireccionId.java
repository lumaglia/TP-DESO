package org.example.TP_DESO.domain.Pks;

import java.io.Serializable;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DireccionId that = (DireccionId) o;
        return Objects.equals(domicilio, that.domicilio) &&
                Objects.equals(depto, that.depto) &&
                Objects.equals(codigoPostal, that.codigoPostal) &&
                Objects.equals(pais, that.pais);
    }

    @Override
    public int hashCode() {
        return Objects.hash(domicilio, depto, codigoPostal, pais);
    }
}
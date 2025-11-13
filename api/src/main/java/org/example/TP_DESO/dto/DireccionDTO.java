package org.example.TP_DESO.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DireccionDTO {
    private String domicilio;
    private String depto;
    private String codigoPostal;
    private String localidad;
    private String provincia;
    private String pais;

    public DireccionDTO(String domicilio, String depto, String codigoPostal, String localidad, String provincia, String pais) {
        this.domicilio = domicilio;
        this.depto = depto;
        this.codigoPostal = codigoPostal;
        this.localidad = localidad;
        this.provincia = provincia;
        this.pais = pais;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public String getDepto() {
        return depto;
    }

    public String getCodigoPostal() {
        return codigoPostal;
    }

    public String getLocalidad() {
        return localidad;
    }

    public String getProvincia() {
        return provincia;
    }

    public String getPais() {
        return pais;
    }

    @Override
    public String toString() {
        return
                "domicilio='" + domicilio + "'\n" +
                "depto='" + depto + "'\n" +
                "codigoPostal='" + codigoPostal + "'\n" +
                "localidad='" + localidad + "'\n" +
                "provincia='" + provincia + "'\n" +
                "pais='" + pais + "'\n";
    }
}

package org.example.TP_DESO.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class HuespedDTO {
    private String nombre;
    private String apellido;
    private String tipoDoc;
    private String nroDoc;
    private String cuil;
    private String posicionIva;
    private LocalDate fechaNac;
    private String telefono;
    private String email;
    private String ocupacion;
    private String nacionalidad;
    private DireccionDTO direccion;

    public HuespedDTO(String tipoDoc, String nroDoc, String apellido, String nombre, String cuil, String posicionIva, LocalDate fechaNac, String telefono, String email, String ocupacion, String nacionalidad, DireccionDTO direccion) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
        this.cuil = cuil;
        this.posicionIva = posicionIva;
        this.fechaNac = fechaNac;
        this.telefono = telefono;
        this.email = email;
        this.ocupacion = ocupacion;
        this.nacionalidad = nacionalidad;
        this.direccion = direccion;
    }

    public HuespedDTO(String tipoDoc, String nroDoc) {
        this.tipoDoc = tipoDoc;
        this.nroDoc = nroDoc;
    }

    @Override
    public String toString() {
        String d = "";
        if (direccion != null) {
            d = direccion.toString();
        }
        return
                "nombre='" + nombre + "'\n" +
                "apellido='" + apellido + "'\n" +
                "tipoDoc='" + tipoDoc + "'\n" +
                "nroDoc='" + nroDoc + "'\n" +
                "cuil='" + cuil + "'\n" +
                "posicionIva='" + posicionIva + "'\n" +
                "fechaNac=" + fechaNac + "'\n" +
                "telefono='" + telefono + "'\n" +
                "email='" + email + "'\n" +
                "ocupacion='" + ocupacion + "'\n" +
                "nacionalidad='" + nacionalidad + "'\n" +
                d;
    }
}

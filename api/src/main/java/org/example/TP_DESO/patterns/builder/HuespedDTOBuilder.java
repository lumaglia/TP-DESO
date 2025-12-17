package org.example.TP_DESO.patterns.builder;

import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.dto.HuespedDTO;

import java.time.LocalDate;

public class HuespedDTOBuilder {
    private String tipoDoc;
    private String nroDoc;
    private String apellido;
    private String nombre;
    private String cuil;
    private String posicionIva;
    private LocalDate fechaNac;
    private String telefono;
    private String email;
    private String ocupacion;
    private String nacionalidad;
    private DireccionDTO direccion;

    public HuespedDTOBuilder setTipoDoc(String tipoDoc) {
        this.tipoDoc = tipoDoc;
        return this;
    }

    public HuespedDTOBuilder setNroDoc(String nroDoc) {
        this.nroDoc = nroDoc;
        return this;
    }

    public HuespedDTOBuilder setApellido(String apellido) {
        this.apellido = apellido;
        return this;
    }

    public HuespedDTOBuilder setNombre(String nombre) {
        this.nombre = nombre;
        return this;
    }

    public HuespedDTOBuilder setCuil(String cuil) {
        this.cuil = cuil;
        return this;
    }

    public HuespedDTOBuilder setPosicionIva(String posicionIva) {
        this.posicionIva = posicionIva;
        return this;
    }

    public HuespedDTOBuilder setFechaNac(LocalDate fechaNac) {
        this.fechaNac = fechaNac;
        return this;
    }

    public HuespedDTOBuilder setTelefono(String telefono) {
        this.telefono = telefono;
        return this;
    }

    public HuespedDTOBuilder setEmail(String email) {
        this.email = email;
        return this;
    }

    public HuespedDTOBuilder setOcupacion(String ocupacion) {
        this.ocupacion = ocupacion;
        return this;
    }

    public HuespedDTOBuilder setNacionalidad(String nacionalidad) {
        this.nacionalidad = nacionalidad;
        return this;
    }

    public HuespedDTOBuilder setDireccion(DireccionDTO direccion) {
        this.direccion = direccion;
        return this;
    }

    public HuespedDTO createHuespedDTO() {
        return new HuespedDTO(tipoDoc, nroDoc, apellido, nombre, cuil, posicionIva, fechaNac, telefono, email, ocupacion, nacionalidad, direccion);
    }
}
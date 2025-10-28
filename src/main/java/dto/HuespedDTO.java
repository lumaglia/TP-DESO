package dto;

import java.time.LocalDate;

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

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }

    public String getCuil() {
        return cuil;
    }

    public String getPosicionIva() {
        return posicionIva;
    }

    public LocalDate getFechaNac() {
        return fechaNac;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getEmail() {
        return email;
    }

    public String getOcupacion() {
        return ocupacion;
    }

    public String getNacionalidad() {
        return nacionalidad;
    }

    public DireccionDTO getDireccion() {
        return direccion;
    }
}

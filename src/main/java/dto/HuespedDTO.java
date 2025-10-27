package dto;

import domain.Direccion;
import domain.Estadia;
import domain.Reserva;

import java.time.LocalDate;
import java.util.ArrayList;

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
}

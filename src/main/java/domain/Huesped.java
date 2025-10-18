package domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Huesped {

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
    private Direccion direccion;
    private ArrayList<Reserva> reservas;
    ArrayList<Estadia> estadias;


    public String getNombre() {
        return nombre;
    }

    public static class Builder {
        private Huesped huesped = new Huesped();

        public Builder nombre(String nombre) {
            huesped.nombre = nombre;
            return this;
        }

        public Huesped build() {
            return huesped;
        }

    }
}

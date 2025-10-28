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
    private ArrayList<Estadia> estadias;


    public String getNombre() {
        return nombre;
    }

    public String getTipoDoc() {
        return tipoDoc;
    }

    public String getNroDoc() {
        return nroDoc;
    }
    //    public static class Builder {
//        private Huesped huesped = new Huesped();
//
//        public Builder nombre(String nombre) {
//            huesped.nombre = nombre;
//            return this;
//        }
//
//        public Huesped build() {
//            return huesped;
//        }
//
//    }

    public Huesped(String nombre, String apellido, String tipoDoc, String nroDoc, String cuil, String posicionIva, LocalDate fechaNac, String telefono, String email, String ocupacion, String nacionalidad, Direccion direccion, ArrayList<Reserva> reservas, ArrayList<Estadia> estadias) {
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
        this.reservas = reservas;
        this.estadias = estadias;
    }

    @Override
    public String toString() {
        return
                tipoDoc +
                        ";" + nroDoc +
                        ";" + apellido +
                        ";" + nombre +
                        ";" + cuil +
                        ";" + posicionIva +
                        ";" + fechaNac +
                        ";" + telefono +
                        ";" + email +
                        ";" + ocupacion +
                        ";" + nacionalidad +
                        ";" + direccion.getPais() +
                        ";" + direccion.getCodigoPostal() +
                        ";" + direccion.getDomicilio() +
                        ";" + direccion.getDepto();
    }
}

package org.example.TP_DESO.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(
    name = "huesped",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tipo_doc", "nro_doc"})
    }
    )

@Getter
@Setter

public class Huesped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "direccion_id")
    private Direccion direccion;

    @Transient
    private ArrayList<Reserva> reservas;
    @Transient
    private ArrayList<Estadia> estadias;

    public Huesped() {

    }

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

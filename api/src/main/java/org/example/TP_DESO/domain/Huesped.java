package org.example.TP_DESO.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.domain.Pks.DocumentoId;

@Entity
@Table (name = "huesped")
@IdClass(DocumentoId.class)
@Getter
@Setter

public class Huesped {

    @Id
    private String tipoDoc;

    @Id
    private String nroDoc;

    private String nombre;
    private String apellido;
    private String cuil;
    private String posicionIva;
    private LocalDate fechaNac;
    private String telefono;
    private String email;
    private String ocupacion;
    private String nacionalidad;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "domicilio", referencedColumnName = "domicilio"),
            @JoinColumn(name = "depto", referencedColumnName = "depto"),
            @JoinColumn(name = "codigo_postal", referencedColumnName = "codigoPostal"),
            @JoinColumn(name = "pais", referencedColumnName = "pais"),
    })
    private Direccion direccion;

    @ManyToMany(mappedBy = "huespedes")
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

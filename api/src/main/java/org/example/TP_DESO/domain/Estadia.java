package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name = "estadia")
@Getter
@Setter

public class Estadia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "estadia_huesped",
            joinColumns = @JoinColumn(name = "estadia_id"), // PK de Estadia
            inverseJoinColumns = {
                    @JoinColumn(name = "huesped_tipoDoc", referencedColumnName = "tipoDoc"),
                    @JoinColumn(name = "huesped_nroDoc", referencedColumnName = "nroDoc")
            }
    )
    private ArrayList<Huesped> huespedes;


    @OneToOne(mappedBy = "estadia", optional = true)
    private Reserva reserva;


    @ManyToOne(optional = false)
    @JoinColumn(
            name = "habitacion_id",
            nullable = false
    )
    private Habitacion habitacion;
}

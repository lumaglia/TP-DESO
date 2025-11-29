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

    @ManyToMany (cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "estadia_huesped",
            joinColumns = @JoinColumn(name = "estadia_id"),
            inverseJoinColumns = @JoinColumn(name = "huesped_id")
    )
    private ArrayList<Huesped> huespedes;

    @OneToOne(mappedBy = "estadia", optional = true)
    private Reserva reserva;

    @OneToMany(mappedBy = "estadia")
    private ArrayList<Factura> facturas;

    @OneToMany(mappedBy = "estadia")
    private ArrayList<Consumo> consumos;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "habitacion_id",
            nullable = false
    )
    private Habitacion habitacion;
}

package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "reserva")
@Getter
@Setter

public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaReserva;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String apellido;
    private String nombre;
    private String telefono;
    private boolean cancelada;
    //private Huesped huesped;   No hay relacion directa en el DER

    @ManyToOne
    @JoinColumn(
            name = "habitacion_id",
            nullable = false
    )
    private Habitacion habitacion;

    @OneToOne(optional = true)
    @JoinColumn(
            name = "estadia_id",
            unique = true,
            nullable = true
    )
    private Estadia estadia;
}

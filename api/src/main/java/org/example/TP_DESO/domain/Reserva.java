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
    private Long idReserva;

    private LocalDate fechaReserva;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String apellido;
    private String nombre;
    private String telefono;
    private boolean cancelada;

    @OneToOne
    @JoinColumn(name = "id_estadia", referencedColumnName = "idEstadia")
    private Estadia estadia;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_habitacion", referencedColumnName = "nroHabitacion")
    private Habitacion habitacion;
}

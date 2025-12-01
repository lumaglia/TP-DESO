package org.example.TP_DESO.dto;


import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.domain.Habitacion;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.domain.Reserva;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
public class EstadiaDTO {
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private Reserva reserva;
    private ArrayList<Huesped> huespedes;
    private HabitacionDTO habitacion;

    public EstadiaDTO(Long id, LocalDate fechaInicio, LocalDate fechaFin, Reserva reserva, ArrayList<Huesped> huespedes, HabitacionDTO habitacion) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.reserva = reserva;
        this.huespedes = huespedes;
        this.habitacion = habitacion;
    }
}

package org.example.TP_DESO.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.domain.Habitacion;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.domain.Reserva;

import java.time.LocalDate;
import java.util.ArrayList;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class EstadiaDTO {
    private Long id;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private ArrayList<HuespedDTO> huespedes;
    private HabitacionDTO habitacion;

    public EstadiaDTO(LocalDate fechaInicio, LocalDate fechaFin, ArrayList<HuespedDTO> huespedes, HabitacionDTO habitacion) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.huespedes = huespedes;
        this.habitacion = habitacion;
    }
}

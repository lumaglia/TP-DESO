package org.example.TP_DESO.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ReservaDTO {
    private Long id;
    private LocalDate fechaReserva;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String apellido;
    private String nombre;
    private String telefono;
    private boolean cancelada;
    @JsonIgnore
    private HabitacionDTO habitacion;
    private EstadiaDTO estadia;

    public ReservaDTO(LocalDate fechaReserva, LocalDate fechaInicio, LocalDate fechaFin, String apellido, String nombre, String telefono, boolean cancelada, HabitacionDTO habitacion, EstadiaDTO estadia){
        this.fechaReserva = fechaReserva;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.apellido = apellido;
        this.nombre = nombre;
        this.telefono = telefono;
        this.cancelada = cancelada;
        this.habitacion = habitacion;
        this.estadia = estadia;
    }


}

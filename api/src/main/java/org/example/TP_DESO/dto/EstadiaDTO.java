package org.example.TP_DESO.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.patterns.mappers.HabitacionMapper;
import org.example.TP_DESO.patterns.mappers.HuespedMapper;
import org.example.TP_DESO.domain.Estadia;
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
    private Long idEstadia;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private ArrayList<HuespedDTO> huespedes;
    private ArrayList<ConsumoDTO> consumos;
    private HabitacionDTO habitacion;

    public EstadiaDTO(LocalDate fechaInicio, LocalDate fechaFin, ArrayList<HuespedDTO> huespedes, HabitacionDTO habitacion) {
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.huespedes = huespedes;
        this.habitacion = habitacion;
        this.consumos = null;
    }
    public EstadiaDTO(Estadia estadia) {
        this.idEstadia = estadia.getIdEstadia();
        this.fechaInicio = estadia.getFechaInicio();
        this.fechaFin = estadia.getFechaFin();
        this.habitacion = HabitacionMapper.toDTO(estadia.getHabitacion());
        this.huespedes = (ArrayList<HuespedDTO>) estadia.getHuespedes().stream().map(HuespedMapper::toDTO).toList();
        this.consumos = (ArrayList<ConsumoDTO>) estadia.getConsumos().stream().map(ConsumoDTO::new).toList();
    }

    public EstadiaDTO(Long idEstadia, LocalDate fechaInicio, LocalDate fechaFin, ArrayList<HuespedDTO> huespedDTO, HabitacionDTO habitacionDTO) {
        this.idEstadia = idEstadia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.huespedes = huespedDTO;
        this.habitacion = habitacionDTO;
    }
}

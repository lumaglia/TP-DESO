package org.example.TP_DESO.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.domain.*;
import org.example.TP_DESO.patterns.mappers.HabitacionMapper;
import org.example.TP_DESO.patterns.mappers.HuespedMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder

public class EstadiaDTO {
    private Long idEstadia;
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
    public EstadiaDTO(Estadia estadia, List<Consumo> consumos) {
        this.idEstadia = estadia.getIdEstadia();
        this.fechaInicio = estadia.getFechaInicio();
        this.fechaFin = estadia.getFechaFin();
        this.habitacion = HabitacionMapper.toDTO(estadia.getHabitacion());
        this.huespedes = (ArrayList<HuespedDTO>) estadia.getHuespedes().stream().map(HuespedMapper::toDTO).collect(Collectors.toCollection(ArrayList::new));
    }

    public EstadiaDTO(Long idEstadia, LocalDate fechaInicio, LocalDate fechaFin, ArrayList<HuespedDTO> huespedDTO, HabitacionDTO habitacionDTO) {
        this.idEstadia = idEstadia;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.huespedes = huespedDTO;
        this.habitacion = habitacionDTO;
    }
}

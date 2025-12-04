package org.example.TP_DESO.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ReservasEstadiasPorHabitacionDTO {
    private HabitacionDTO habitacion;
    private List<EstadiaDTO> estadiasAsociadas;
    private List<ReservaDTO> reservasAsociadas;

    public ReservasEstadiasPorHabitacionDTO(HabitacionDTO h, List<EstadiaDTO> listaEstadiasDTO, List<ReservaDTO> listaReservasDTO) {
        this.habitacion = h;
        this.estadiasAsociadas = listaEstadiasDTO;
        this.reservasAsociadas = listaReservasDTO;
    }
}

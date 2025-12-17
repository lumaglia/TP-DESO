package org.example.TP_DESO.dto.CU07;

import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.domain.Habitacion;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.domain.Reserva;
import org.example.TP_DESO.dto.ConsumoDTO;
import org.example.TP_DESO.dto.EstadiaDTO;
import org.example.TP_DESO.dto.HuespedDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

@Getter
@Setter
public class EstadiaFacturacionDTO {
    private Long id;
    private String nroHabitacion;
    private Float montoEstadia;
    private ArrayList<HuespedDTO> huespedes;
    private ArrayList<ConsumoDTO> consumos;

    public EstadiaFacturacionDTO(EstadiaDTO estadiaDTO) {
        long noches = ChronoUnit.DAYS.between(estadiaDTO.getFechaInicio(), estadiaDTO.getFechaFin());
        if (LocalDateTime.now().toLocalTime().isAfter(LocalTime.of(18, 0))){
            noches++;
        }

        this.id = estadiaDTO.getIdEstadia();
        this.nroHabitacion = estadiaDTO.getHabitacion().getNroHabitacion();
        this.montoEstadia = noches * estadiaDTO.getHabitacion().getPrecioNoche();
        this.huespedes = estadiaDTO.getHuespedes();
        this.consumos = estadiaDTO.getConsumos();
    }
}

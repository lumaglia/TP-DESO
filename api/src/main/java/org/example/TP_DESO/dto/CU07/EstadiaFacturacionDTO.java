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
        long dias = ChronoUnit.DAYS.between(estadiaDTO.getFechaInicio(), estadiaDTO.getFechaFin());

        this.id = estadiaDTO.getId();
        this.nroHabitacion = estadiaDTO.getHabitacion().getNroHabitacion();
        this.montoEstadia = dias * estadiaDTO.getHabitacion().getPrecioNoche();
        this.huespedes = estadiaDTO.getHuespedes();
        this.consumos = estadiaDTO.getConsumos();
    }
}

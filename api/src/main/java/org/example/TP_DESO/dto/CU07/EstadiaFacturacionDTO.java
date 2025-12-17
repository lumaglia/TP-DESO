package org.example.TP_DESO.dto.CU07;

import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.domain.Consumo;
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
import java.util.stream.Collectors;

@Getter
@Setter
public class EstadiaFacturacionDTO {
    public class ConsumosEstadiaDTO {
        private String nombre;
        private String detalle;
        private float monto;

        public ConsumosEstadiaDTO(Consumo consumo) {
            this.nombre = String.valueOf(consumo.getTipo());
            this.detalle = consumo.getDetalle();
            this.monto = consumo.getMonto();
        }
    }

    private Float montoEstadia;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private ArrayList<HuespedCheckoutDTO> huespedes;
    private ArrayList<ConsumosEstadiaDTO> consumos;

    public EstadiaFacturacionDTO(EstadiaDTO estadiaDTO, float montoEstadia, ArrayList<Consumo> consumos) {
        this.montoEstadia = montoEstadia;
        this.fechaInicio = estadiaDTO.getFechaInicio();
        this.fechaFin = estadiaDTO.getFechaFin();
        this.huespedes = estadiaDTO.getHuespedes().stream().map(HuespedCheckoutDTO::new).collect(Collectors.toCollection(ArrayList::new));
        this.consumos = consumos.stream().map(ConsumosEstadiaDTO::new).collect(Collectors.toCollection(ArrayList::new));
    }
}

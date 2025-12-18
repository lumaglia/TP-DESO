package org.example.TP_DESO.dto.CU07;

import lombok.*;
import org.example.TP_DESO.domain.Consumo;
import org.example.TP_DESO.dto.EstadiaDTO;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Getter
@Setter
public class EstadiaFacturacionDTO {

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ConsumosEstadiaDTO {
        private Long id;
        private String tipo;
        private String descripcion;
        private float monto;

        public ConsumosEstadiaDTO(Consumo consumo) {
            this.id = consumo.getId();
            this.tipo = String.valueOf(consumo.getTipo());
            this.descripcion = consumo.getDetalle();
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

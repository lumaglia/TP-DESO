package org.example.TP_DESO.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.domain.Consumo;

@Getter
@Setter
public class ConsumoDTO {
    private Long id;
    private String tipo;
    private Float monto;
    private String detalle;
    private Long idEstadiaAsociada;

    public ConsumoDTO(Consumo consumo){
        this.id = consumo.getId();
        this.tipo = consumo.getTipo().toString();
        this.monto = consumo.getMonto();
        this.detalle = consumo.getDetalle();
        this.idEstadiaAsociada = consumo.getEstadia().getIdEstadia();
    }
}

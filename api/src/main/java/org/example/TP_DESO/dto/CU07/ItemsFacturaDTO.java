package org.example.TP_DESO.dto.CU07;

import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.domain.Consumo;
import org.example.TP_DESO.domain.Estadia;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.dto.ConsumoDTO;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ItemsFacturaDTO {
    private String nombreResponsablePago;
    private String posicionIVA;
    private Character tipoFactura;
    private Float montoTotal;
    private Float montoIVA;
    private List<ConsumoDTO> consumos;

    public ItemsFacturaDTO(Estadia estadia, Huesped huesped){
        float sum = 0;
        for(Consumo consumo: estadia.getConsumos()){ sum += consumo.getMonto();}
        this.montoTotal = sum;
        this.posicionIVA = huesped.getPosicionIva();
        if(!Objects.equals(huesped.getPosicionIva(), "Consumidor final")){
            this.tipoFactura = 'A';
            this.montoIVA = 0f;
        }
        else{
            this.tipoFactura = 'B';
            this.montoIVA = (float) (montoTotal * 0.21);
        }

        this.consumos = estadia.getConsumos().stream().map(ConsumoDTO::new).toList();
    }
}

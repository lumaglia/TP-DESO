package org.example.TP_DESO.dto.CU07;

import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.dto.CU12.ResponsablePagoDTO;
import org.example.TP_DESO.dto.ConsumoDTO;
import org.example.TP_DESO.dto.EstadiaDTO;
import org.example.TP_DESO.dto.HuespedDTO;

import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class ItemsFacturaDTO {
    private Long idResponsablePago;
    private String posicionIVA;
    private Character tipoFactura;
    private Float montoTotal;
    private Float montoIVA;
    private List<ConsumoDTO> consumos;

    public ItemsFacturaDTO(EstadiaDTO estadiaDTO, ResponsablePagoDTO responsablePagoDTO, HuespedCheckoutDTO huespedCheckoutDTO, List<ConsumoDTO> consumos) {
        HuespedDTO responsableHuesped = estadiaDTO.getHuespedes().stream().filter(h -> Objects.equals(h.getCuil(), huespedCheckoutDTO.getCuil())).findFirst().orElse(null);
        assert responsableHuesped != null;
        Float monto = (float) (0);
        for(ConsumoDTO c : consumos) {
            monto += c.getMonto();
        }

        this.idResponsablePago = responsablePagoDTO.getId();
        this.posicionIVA = responsableHuesped.getPosicionIva();
        this.montoTotal = monto;
        this.montoIVA = (float) (monto * 0.21);
        this.consumos = consumos;

        if(Objects.equals(responsableHuesped.getPosicionIva(), "Consumidor final")){
            this.tipoFactura = Character.valueOf('A');
        }
        else {
            this.tipoFactura = Character.valueOf('B');
        }
    }
}

package org.example.TP_DESO.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.TP_DESO.domain.Factura;
import org.example.TP_DESO.domain.PersonaJuridica;

@Getter
@Setter
@NoArgsConstructor
public class FacturaDTO {
    private String idFactura;
    private String idNota;
    private Long idEstadia;
    private Long idPago;
    private Long idResponsable;

    public FacturaDTO(String idFactura, String idNota, Long idEstadia, Long idPago, Long idResponsable) {
        this.idFactura = idFactura;
        this.idNota = idNota;
        this.idEstadia = idEstadia;
        this.idPago = idPago;
        this.idResponsable = idResponsable;
    }

    public FacturaDTO(Factura factura){
        this.idFactura = factura.getNroFactura();
        this.idNota = factura.getNotaCredito().getNroNotaCredito();
        this.idEstadia = factura.getEstadia().getIdEstadia();
    }
}

package org.example.TP_DESO.dto.CU07;

import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.dto.CU12.ResponsablePagoDTO;
import org.example.TP_DESO.dto.ConsumoDTO;
import org.example.TP_DESO.dto.EstadiaDTO;

import java.util.List;

@Getter
@Setter
public class RequestDeItemsDTO {
    private EstadiaDTO estadia;
    private HuespedCheckoutDTO responsablePago;
    private List<ConsumoDTO> consumos;
}

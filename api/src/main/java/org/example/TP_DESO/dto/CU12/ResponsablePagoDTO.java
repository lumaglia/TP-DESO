package org.example.TP_DESO.dto.CU12;

import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.dto.DireccionDTO;

@Getter
@Setter
public class ResponsablePagoDTO {
    private String razonSocial;
    private String cuit;
    private String telefono;
    private DireccionDTO direccion;
}

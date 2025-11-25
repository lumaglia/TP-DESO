package org.example.TP_DESO.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class BuscarHuespedDTO {
    private String nombre;
    private String apellido;
    private String tipoDoc;
    private String nroDoc;

    public BuscarHuespedDTO(HuespedDTO huespedDTO) {
        this.nombre = huespedDTO.getNombre();
        this.apellido = huespedDTO.getApellido();
        this.tipoDoc = huespedDTO.getTipoDoc();
        this.nroDoc = huespedDTO.getNroDoc();
    }
}

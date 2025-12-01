package org.example.TP_DESO.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DobleSuperiorDTO extends HabitacionDTO {

    private int camasKingInd;
    private int camasKingDob;

    public DobleSuperiorDTO(String nroHabitacion, float precioNoche, int capacidad, String tamanno, int camasKingInd, int camasKingDob) {
        super(nroHabitacion, precioNoche, capacidad, tamanno);
        this.camasKingInd = camasKingInd;
        this.camasKingDob = camasKingDob;
    }
}
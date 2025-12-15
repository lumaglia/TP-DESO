package org.example.TP_DESO.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuiteDobleDTO extends HabitacionDTO {

    private int camasKingDob;

    public SuiteDobleDTO(String nroHabitacion, float precioNoche, int capacidad, String tamanno, int camasKingDob) {
        super(nroHabitacion, precioNoche, capacidad, tamanno);
        this.camasKingDob = camasKingDob;
    }
}
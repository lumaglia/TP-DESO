package org.example.TP_DESO.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IndividualEstandarDTO extends HabitacionDTO {

    private int camasInd;

    public IndividualEstandarDTO(String nroHabitacion, float precioNoche, int capacidad, String tamanno, Integer camasInd) {
        super(nroHabitacion, precioNoche, capacidad, tamanno);
        this.camasInd = camasInd;
    }
}

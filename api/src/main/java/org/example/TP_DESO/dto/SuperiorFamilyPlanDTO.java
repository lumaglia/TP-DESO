package org.example.TP_DESO.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuperiorFamilyPlanDTO extends HabitacionDTO {

    private int camasInd;
    private int camasDob;

    public SuperiorFamilyPlanDTO(String nroHabitacion, float precioNoche, int capacidad, String tamanno, int camasInd, int camasDob) {
        super(nroHabitacion, precioNoche, capacidad, tamanno);
        this.camasInd = camasInd;
        this.camasDob = camasDob;
    }
}
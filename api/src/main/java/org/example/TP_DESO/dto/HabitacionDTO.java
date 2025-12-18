package org.example.TP_DESO.dto;

import lombok.Getter;

@Getter

public abstract class HabitacionDTO {

    private String nroHabitacion;
    private float precioNoche;
    private int capacidad;
    private String tamanno;

    public HabitacionDTO(String nroHabitacion, float precioNoche, int capacidad, String tamanno){
        this.nroHabitacion = nroHabitacion;
        this.precioNoche = precioNoche;
        this.capacidad = capacidad;
        this.tamanno = tamanno;
    }

}

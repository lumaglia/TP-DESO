package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter

public abstract class Habitacion {

    @Id
    private String nroHabitacion;

    private float precioNoche;
    private int capacidad;
    private String tamanno;


    public Habitacion() {}

    public Habitacion(String nro_Habitacion, float precio_Noche, int capacidad, String tamanno) {
        this.nroHabitacion = nro_Habitacion;
        this.precioNoche = precio_Noche;
        this.capacidad = capacidad;
        this.tamanno = tamanno;
    }

    static public void sortHabitacionesByPrice(List<Habitacion> lh){
        org.example.TP_DESO.patterns.strategy.HabitacionSorter.sort(
                lh,
                new org.example.TP_DESO.patterns.strategy.SortByPrecioNoche()
        );
    }

    static public void sortHabitaciones(
            List<Habitacion> lh,
            org.example.TP_DESO.patterns.strategy.HabitacionSortingStrategy strategy
    ) {
        org.example.TP_DESO.patterns.strategy.HabitacionSorter.sort(lh, strategy);
    }
}

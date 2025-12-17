package org.example.TP_DESO.patterns.strategy;

import org.example.TP_DESO.domain.Habitacion;

import java.util.Comparator;

public class SortByPrecioNoche implements HabitacionSortingStrategy {
    @Override
    public Comparator<Habitacion> comparator() {
        return Comparator.comparing(Habitacion::getPrecioNoche); // ascendente
    }
}
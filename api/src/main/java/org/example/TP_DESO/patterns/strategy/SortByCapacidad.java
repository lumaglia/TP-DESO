package org.example.TP_DESO.patterns.strategy;

import org.example.TP_DESO.domain.Habitacion;

import java.util.Comparator;

public class SortByCapacidad implements HabitacionSortingStrategy {
    @Override
    public Comparator<Habitacion> comparator() {
        return Comparator.comparingInt(Habitacion::getCapacidad); // ascendente
    }
}
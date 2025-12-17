package org.example.TP_DESO.patterns.strategy;

import org.example.TP_DESO.domain.Habitacion;

import java.util.Comparator;

public interface HabitacionSortingStrategy {
    Comparator<Habitacion> comparator();
}
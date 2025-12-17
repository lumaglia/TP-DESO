package org.example.TP_DESO.patterns.strategy;

import org.example.TP_DESO.domain.Habitacion;

import java.util.List;

public final class HabitacionSorter {

    private HabitacionSorter() {
        // Utility class
    }

    public static void sort(List<Habitacion> habitaciones, HabitacionSortingStrategy strategy) {
        if (habitaciones == null || strategy == null) return;
        habitaciones.sort(strategy.comparator());
    }
}
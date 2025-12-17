package org.example.TP_DESO.patterns.strategy;

import org.example.TP_DESO.dto.HabitacionDTO;

import java.time.LocalDate;

public interface PrecioHabitacionStrategy {
    double calcularPrecio(HabitacionDTO habitacion, LocalDate inicio, LocalDate fin);
}

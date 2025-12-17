package org.example.TP_DESO.patterns.strategy;

import org.example.TP_DESO.dto.HabitacionDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@Component
public class PrecioHabitacion implements PrecioHabitacionStrategy {
    @Override
    public double calcularPrecio(HabitacionDTO h, LocalDate inicio, LocalDate fin) {
        double precio = h.getPrecioNoche();

        long noches = ChronoUnit.DAYS.between(inicio, fin);

        return precio * noches;
    }
}
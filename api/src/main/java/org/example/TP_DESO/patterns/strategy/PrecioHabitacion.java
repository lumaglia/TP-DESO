package org.example.TP_DESO.patterns.strategy;

import org.example.TP_DESO.dto.HabitacionDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

@Component
public class PrecioHabitacion implements PrecioHabitacionStrategy {

    public double calcularPrecio(
            HabitacionDTO h,
            LocalDate inicio,
            LocalDateTime fin
    ) {
        double precioNoche = h.getPrecioNoche();
        LocalDate fechaFin = fin.toLocalDate();
        long noches = ChronoUnit.DAYS.between(inicio, fechaFin);
        double total = precioNoche * noches;

        if (fin.toLocalTime().isAfter(LocalTime.of(10, 0))) {
            total += precioNoche / 2;
        }

        return total;
    }
}
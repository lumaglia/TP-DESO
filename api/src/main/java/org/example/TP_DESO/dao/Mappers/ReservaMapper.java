package org.example.TP_DESO.dao.Mappers;

import org.example.TP_DESO.domain.Reserva;
import org.example.TP_DESO.dto.ReservaDTO;

public class ReservaMapper {

    public static ReservaDTO toDTO(Reserva r) {

        if (r == null) return null;

        return new ReservaDTO(
                r.getFechaReserva(),
                r.getFechaInicio(),
                r.getFechaFin(),
                r.getApellido(),
                r.getNombre(),
                r.getTelefono(),
                r.isCancelada(),
                HabitacionMapper.toDTO(r.getHabitacion()),
                EstadiaMapper.toDTO(r.getEstadia())
        );
    }
}

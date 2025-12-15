package org.example.TP_DESO.dao.Mappers;

import org.example.TP_DESO.domain.Reserva;
import org.example.TP_DESO.dto.ReservaDTO;

public class ReservaMapper {

    public static ReservaDTO toDTO(Reserva r) {

        if (r == null) return null;

        return new ReservaDTO(
                r.getIdReserva(),
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

    public static Reserva toDomain(ReservaDTO r) {
        if (r == null){
            return null;
        }
        Reserva reserva = new Reserva();
        reserva.setFechaReserva(r.getFechaReserva());
        reserva.setFechaInicio(r.getFechaInicio());
        reserva.setFechaFin(r.getFechaFin());
        reserva.setApellido(r.getApellido());
        reserva.setNombre(r.getNombre());
        reserva.setTelefono(r.getTelefono());
        reserva.setCancelada(r.isCancelada());
        reserva.setIdReserva(r.getId());
        reserva.setHabitacion(HabitacionMapper.toDomain(r.getHabitacion()));
        reserva.setEstadia(EstadiaMapper.toDomain(r.getEstadia()));

        return reserva;
    }
}

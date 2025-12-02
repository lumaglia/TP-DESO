package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Reserva;
import org.example.TP_DESO.dto.ReservaDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;

import java.time.LocalDate;
import java.util.ArrayList;

public interface ReservaDAO {

    void crearReserva(Reserva reserva) throws FracasoOperacion;
    ArrayList<ReservaDTO> obtenerReservasEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) throws FracasoOperacion;
    void modificarReserva(Long idReserva, Reserva reserva) throws FracasoOperacion;
    void eliminarReserva(Long idReserva) throws FracasoOperacion;
    ArrayList<ReservaDTO> buscarReservasPorApellidoYNombre(String apellido, String nombre) throws FracasoOperacion;

}

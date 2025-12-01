package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Habitacion;
import org.example.TP_DESO.dto.*;
import org.example.TP_DESO.exceptions.FracasoOperacion;

import java.time.LocalDate;
import java.util.ArrayList;

public interface HabitacionDAO {

    // CRUD GENERAL, si se complica, se puede utilizar el HabitacionMapper en su implementacion
    void crearHabitacion(Habitacion habitacion) throws FracasoOperacion;
    void modificarHabitacion(Long id, Habitacion habitacion) throws FracasoOperacion;
    void eliminarHabitacion(Long id) throws FracasoOperacion;
    HabitacionDTO obtenerHabitacion(Long id) throws FracasoOperacion;

    ArrayList<HabitacionDTO> obtenerTodas() throws FracasoOperacion;
    HabitacionDTO buscarPorNumero(int numero) throws FracasoOperacion;
    ArrayList<HabitacionDTO> buscarHabitacionesDisponibles(LocalDate desde, LocalDate hasta) throws FracasoOperacion;
    ArrayList<HabitacionDTO> buscarHabitacionesOcupadas(LocalDate desde, LocalDate hasta) throws FracasoOperacion;
    ArrayList<HabitacionDTO> buscarPorTipo(String tipoHabitacion) throws FracasoOperacion;
}


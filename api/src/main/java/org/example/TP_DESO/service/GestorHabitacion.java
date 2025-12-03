package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.HabitacionDAOMySQL;
import org.example.TP_DESO.dto.HabitacionDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;

import java.time.LocalDate;
import java.util.ArrayList;

public class GestorHabitacion {
    private static GestorHabitacion singleton_instance;

    private GestorHabitacion() {

    }

    public synchronized static GestorHabitacion getInstance() {
        if(singleton_instance == null) singleton_instance = new GestorHabitacion();
        return singleton_instance;
    }

    public ArrayList<HabitacionDTO> mostrarHabitaciones() throws FracasoOperacion {
        try{
            HabitacionDAOMySQL dao = new HabitacionDAOMySQL();
            return dao.obtenerTodas();
        } catch (FracasoOperacion e) {
            throw new FracasoOperacion("Error al obtener las habitaciones." + e.getMessage());
        }
    }

    public boolean obtenerEstadoHabitacion(String nroHabitacion, LocalDate desde, LocalDate hasta) throws FracasoOperacion {
        try {
            HabitacionDAOMySQL dao = new HabitacionDAOMySQL();
            HabitacionDTO dto = dao.obtenerHabitacion(nroHabitacion);

            return !(dao.buscarHabitacionesOcupadas(desde, hasta).contains(dto));

        } catch (FracasoOperacion e) {
            throw new FracasoOperacion("Error al obtener el estado de la habitacion: " + e.getMessage());
        }
    }
}

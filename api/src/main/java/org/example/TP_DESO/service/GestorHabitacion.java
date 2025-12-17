package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.HabitacionDAO;
import org.example.TP_DESO.dao.HabitacionDAOMySQL;
import org.example.TP_DESO.domain.Habitacion;
import org.example.TP_DESO.dto.HabitacionDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class GestorHabitacion {
    @Autowired
    private HabitacionDAO dao;

    private static GestorHabitacion singleton_instance;

    private GestorHabitacion() {

    }

    public synchronized static GestorHabitacion getInstance() {
        if(singleton_instance == null) singleton_instance = new GestorHabitacion();
        return singleton_instance;
    }

    public ArrayList<Habitacion> mostrarHabitacionDomain() throws FracasoOperacion{
        try{
            return dao.obtenerTodasDomainForm();
        } catch (Exception e) {
            throw new FracasoOperacion("Error al obtener las habitaciones." + e.getMessage());
        }
    }

    public ArrayList<HabitacionDTO> mostrarHabitacionesDTO() throws FracasoOperacion {
        try{
            return dao.obtenerTodas();
        } catch (Exception e) {
            throw new FracasoOperacion("Error al obtener las habitaciones." + e.getMessage());
        }
    }
    public HabitacionDTO obtenerHabitacion(String id) throws FracasoOperacion {
        return dao.obtenerHabitacion(id);
    }
}

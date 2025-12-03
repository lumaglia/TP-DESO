package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.EstadiaDAOMySQL;
import org.example.TP_DESO.dto.EstadiaDTO;
import org.example.TP_DESO.dto.ReservaDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;

public class GestorReserva {
    private static GestorReserva singleton_instance;

    private GestorReserva() {

    }

    public synchronized static GestorReserva getInstance() {
        if(singleton_instance == null) singleton_instance = new GestorReserva();
        return singleton_instance;
    }

    public void hacerReserva(){

    }

    public ReservaDTO mostrarReserva(){

    }

    public boolean cancelarReserva(EstadiaDTO estadia) throws  FracasoOperacion {
        try {
            EstadiaDAOMySQL dao = new EstadiaDAOMySQL();
            dao.eliminarEstadia(estadia.getId());
            return true;
        } catch (FracasoOperacion e) {
            throw new FracasoOperacion("No se ha podido cancelar la reserva" +  e.getMessage());
        }
    }

    public void checkIn(){

    }

    public void checkOut(EstadiaDTO estadiaDTO){

    }
}

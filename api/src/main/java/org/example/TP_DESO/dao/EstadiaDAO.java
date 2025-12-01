package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Estadia;
import org.example.TP_DESO.dto.EstadiaDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;

import java.time.LocalDate;
import java.util.ArrayList;

public interface EstadiaDAO {
    void crearEstadia(Estadia estadia) throws FracasoOperacion;
    ArrayList <EstadiaDTO> obtenerEstadiaEntreFechas(LocalDate fechaInicio, LocalDate fechaFin) throws FracasoOperacion;
    void modificarEstadia(Long idEstadia, Estadia estadia) throws FracasoOperacion;
    void eliminarEstadia(Long idEstadia) throws FracasoOperacion;
    EstadiaDTO buscarEstadiaPorHabitacionYFechaFin(int numeroHabitacion, LocalDate fechaFin) throws FracasoOperacion;
    ArrayList<EstadiaDTO> obtenerEstadiasDeHuesped(String tipoDoc, String nroDoc) throws FracasoOperacion;
    //ArrayList<FacturaDTO> obtenerFacturasDeEstadiaPorHabitacionYEstado(int numeroHabitacion, EstadoFactura estado) throws FracasoOperacion;
    //Hacer cuando se implemente lo de factura

}

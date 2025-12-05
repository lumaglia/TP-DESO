package org.example.TP_DESO.dao;

import org.example.TP_DESO.exceptions.FracasoOperacion;

public interface PagoDAO {
    void crearPago() throws FracasoOperacion;
    void obtenerPago() throws FracasoOperacion;
    void modificarPago() throws FracasoOperacion;
    void eliminarPago() throws FracasoOperacion;

    void crearPagoParcial() throws FracasoOperacion;
    void obtenerPagoParcial() throws FracasoOperacion;
    void modificarPagoParcial() throws FracasoOperacion;
    void eliminarPagoParcial() throws FracasoOperacion;
}

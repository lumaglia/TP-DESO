package org.example.TP_DESO.dao;

import org.example.TP_DESO.exceptions.FracasoOperacion;

public interface NotaCreditoDAO {
    void crearNotaCredito() throws FracasoOperacion;
    void obtenerNotaCredito() throws FracasoOperacion;
    void modificarNotaCredito() throws FracasoOperacion;
    void eliminarNotaCredito() throws FracasoOperacion;
}

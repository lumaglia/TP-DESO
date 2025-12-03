package org.example.TP_DESO.dao;

import org.example.TP_DESO.exceptions.FracasoOperacion;

public interface MetodoPagoDAO {
    void crearMonedaEfectivo() throws FracasoOperacion;
    void obtenerMonedaEfectivo() throws FracasoOperacion;
    void modificarMonedaEfectivo() throws FracasoOperacion;
    void eliminarMonedaEfectivo() throws FracasoOperacion;

    void crearTarjetaCredito() throws FracasoOperacion;
    void obtenerTarjetaCredito() throws FracasoOperacion;
    void modificarTarjetaCredito() throws FracasoOperacion;
    void eliminarTarjetaCredito() throws FracasoOperacion;

    void crearTarjetaDebito() throws FracasoOperacion;
    void obtenerTarjetaDebito() throws FracasoOperacion;
    void modificarTarjetaDebito() throws FracasoOperacion;
    void eliminarTarjetaDebito() throws FracasoOperacion;

    void crearCheque() throws FracasoOperacion;
    void obtenerCheque() throws FracasoOperacion;
    void modificarCheque() throws FracasoOperacion;
    void eliminarCheque() throws FracasoOperacion;
}

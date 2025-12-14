package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Factura;
import org.example.TP_DESO.exceptions.FracasoOperacion;

public interface FacturaDAO {
    void crearFactura(Factura factura) throws FracasoOperacion;
    void obtenerFactura() throws FracasoOperacion;
    void modificarFactura() throws FracasoOperacion;
    void eliminarFactura() throws FracasoOperacion;
}

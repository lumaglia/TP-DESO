package org.example.TP_DESO.dao;

import org.example.TP_DESO.exceptions.FracasoOperacion;

public interface ResponsablePagoDAO {
    // FUNCIONES PARA LA PERSONA FISICA (NO SOY UNA IA SOY JUAN)
    void crearPersonaFisica() throws FracasoOperacion;
    void obtenerPersonaFisica() throws FracasoOperacion;
    void modificarPersonaFisica() throws FracasoOperacion;
    void eliminarPersonaFisica() throws FracasoOperacion;

    // FUNCIONES PARA LA PERSONA JURIDICA (TAMPOCO SOY UNA IA SOY JUAN)
    void crearPersonaJuridica() throws FracasoOperacion;
    void obtenerPersonaJuridica() throws FracasoOperacion;
    void modificarPersonaJuridica() throws FracasoOperacion;
    void eliminarPersonaJuridica() throws FracasoOperacion;
}

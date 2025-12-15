package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.PersonaFisica;
import org.example.TP_DESO.domain.PersonaJuridica;
import org.example.TP_DESO.exceptions.FracasoOperacion;

import java.util.List;

public interface ResponsablePagoDAO {
    // FUNCIONES PARA LA PERSONA FISICA (NO SOY UNA IA SOY JUAN)
    void crearPersonaFisica() throws FracasoOperacion;
    void obtenerPersonaFisica(String cuit) throws FracasoOperacion;
    List<PersonaFisica> obtenerTodasPersonaFisica() throws FracasoOperacion;
    void modificarPersonaFisica() throws FracasoOperacion;
    void eliminarPersonaFisica() throws FracasoOperacion;

    // FUNCIONES PARA LA PERSONA JURIDICA (TAMPOCO SOY UNA IA SOY JUAN)
    void crearPersonaJuridica() throws FracasoOperacion;
    PersonaJuridica obtenerPersonaJuridica(String cuit) throws FracasoOperacion;
    void modificarPersonaJuridica() throws FracasoOperacion;
    void eliminarPersonaJuridica() throws FracasoOperacion;
}

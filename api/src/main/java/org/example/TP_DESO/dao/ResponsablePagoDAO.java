package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Direccion;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.domain.PersonaFisica;
import org.example.TP_DESO.domain.PersonaJuridica;
import org.example.TP_DESO.exceptions.FracasoOperacion;

import java.util.List;

public interface ResponsablePagoDAO {
    PersonaFisica crearPersonaFisica(Huesped huesped) throws FracasoOperacion;
    PersonaFisica obtenerPersonaFisica(String cuit) throws FracasoOperacion;
    List<PersonaFisica> obtenerTodasPersonaFisica() throws FracasoOperacion;
    void modificarPersonaFisica() throws FracasoOperacion;
    void eliminarPersonaFisica() throws FracasoOperacion;

    PersonaJuridica crearPersonaJuridica(PersonaJuridica personaJuridica) throws FracasoOperacion;
    PersonaJuridica obtenerPersonaJuridica(String cuit) throws FracasoOperacion;
    void modificarPersonaJuridica(Long id, String razonSocial, String cuit, String telefono, Direccion direccion) throws FracasoOperacion;
    void eliminarPersonaJuridica() throws FracasoOperacion;
}

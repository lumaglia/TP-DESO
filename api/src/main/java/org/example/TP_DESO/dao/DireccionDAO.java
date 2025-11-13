package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Direccion;
import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;

public interface DireccionDAO {
    void crearDireccion(Direccion direccion) throws FracasoOperacion;
    DireccionDTO obtenerDireccion(String pais, String codigoPostal, String domicilio, String depto) throws FracasoOperacion;
}

package dao;

import domain.Direccion;
import dto.DireccionDTO;
import exceptions.FracasoOperacion;

import java.io.IOException;

public interface DireccionDAO {
    void crearDireccion(Direccion direccion) throws FracasoOperacion;
    DireccionDTO obtenerDireccion(String pais, String codigoPostal, String domicilio, String depto) throws FracasoOperacion;
}

package dao;

import domain.Direccion;
import dto.DireccionDTO;
import java.io.IOException;

public interface DireccionDAO {
    void crearDireccion(Direccion direccion);
    DireccionDTO obtenerDireccion(String pais, String codigoPostal, String domicilio, String depto) throws IOException;
}

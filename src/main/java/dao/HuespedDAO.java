package dao;

import domain.Direccion;
import domain.Huesped;
import dto.DireccionDTO;
import dto.HuespedDTO;

import java.io.IOException;
import java.util.ArrayList;

public interface HuespedDAO {

    void crearHuesped(Huesped huesped);
    ArrayList<HuespedDTO> obtenerHuesped(HuespedDTO huespedDTO) throws IOException;
    void modificarHuesped(String tipoDoc, String numeroDoc, Huesped huesped) throws IOException;
    void eliminarHuesped(String tipoDoc, String numeroDoc);

    //void crearDireccion(Direccion direccion);
    //DireccionDTO obtenerDireccion(String pais, String codigoPostal, String domicilio, String depto) throws IOException;

}

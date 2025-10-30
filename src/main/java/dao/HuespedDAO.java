package dao;

import domain.Direccion;
import domain.Huesped;
import dto.DireccionDTO;
import dto.HuespedDTO;
import exceptions.FracasoOperacion;

import java.io.IOException;
import java.util.ArrayList;

public interface HuespedDAO {

    void crearHuesped(Huesped huesped) throws FracasoOperacion;
    ArrayList<HuespedDTO> obtenerHuesped(HuespedDTO huespedDTO) throws FracasoOperacion;
    void modificarHuesped(String tipoDoc, String numeroDoc, Huesped huesped) throws FracasoOperacion;
    void eliminarHuesped(String tipoDoc, String numeroDoc) throws FracasoOperacion;

    //void crearDireccion(Direccion direccion);
    //DireccionDTO obtenerDireccion(String pais, String codigoPostal, String domicilio, String depto) throws IOException;

}

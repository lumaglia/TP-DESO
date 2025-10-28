package dao;

import domain.Huesped;
import dto.HuespedDTO;

import java.io.IOException;
import java.util.ArrayList;

public interface HuespedDAO {

    void crearHuesped(Huesped huesped);
    ArrayList<HuespedDTO> obtenerHuesped(HuespedDTO huespedDTO) throws IOException;
    void modificarHuesped(String tipoDoc, String numeroDoc, Huesped huesped) throws IOException;
    void eliminarHuesped(String tipoDoc, String numeroDoc);

}

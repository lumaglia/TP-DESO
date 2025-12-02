package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.dto.HuespedDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;

import java.util.ArrayList;

public interface HuespedDAO {

    void crearHuesped(Huesped huesped) throws FracasoOperacion;
    ArrayList<HuespedDTO> obtenerHuesped(HuespedDTO huespedDTO) throws FracasoOperacion;
    void modificarHuesped(String tipoDoc, String numeroDoc, Huesped huesped) throws FracasoOperacion;
    void eliminarHuesped(String tipoDoc, String numeroDoc) throws FracasoOperacion;

}

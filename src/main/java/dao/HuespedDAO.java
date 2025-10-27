package dao;

import domain.Huesped;

public interface HuespedDAO {

    void crearHuesped(Huesped huesped);
    String obtenerHuesped(String tipoDoc, String numeroDoc);
    void modificarHuesped(String tipoDoc, String numeroDoc, Huesped huesped);
    void eliminarHuesped(String tipoDoc, String numeroDoc);

}

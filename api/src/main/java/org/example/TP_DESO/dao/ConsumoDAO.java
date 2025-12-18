package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Consumo;

import java.util.ArrayList;

public interface ConsumoDAO {
    ArrayList<Consumo> consumosEstadiaNoPagos(Long idEstadia);
    ArrayList<Consumo> consumosEstadia(Long idEstadia);
}

package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Consumo;
import org.example.TP_DESO.domain.Factura;
import org.example.TP_DESO.dto.FacturaDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;

import java.util.ArrayList;
import java.util.Optional;

public interface ConsumoDAO {
    ArrayList<Consumo> consumosEstadia(Long idEstadia) throws FracasoOperacion;
    Consumo obtenerConsumoPorId(Long idConsumo) throws FracasoOperacion;
}

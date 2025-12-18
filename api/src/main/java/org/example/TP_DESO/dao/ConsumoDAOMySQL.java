package org.example.TP_DESO.dao;

import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.springframework.transaction.annotation.Transactional;
import org.example.TP_DESO.domain.Consumo;
import org.example.TP_DESO.domain.Factura;
import org.example.TP_DESO.repository.ConsumoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
@Transactional
public class ConsumoDAOMySQL implements ConsumoDAO {
    @Autowired
    private ConsumoRepository consumoRepository;

    @Override
    public ArrayList<Consumo> consumosEstadia(Long idEstadia) {
        return new ArrayList<>(consumoRepository.findByEstadia_IdEstadiaAndFacturaIsNull(idEstadia));
    }
    public ArrayList<Consumo> consumosEstadiaNoPagos(Long idEstadia)  throws FracasoOperacion {
        try{
            return consumoRepository.findByEstadia_IdEstadiaAndFacturaIsNull(idEstadia);
            // return consumos.stream().filter(c -> c.getFactura()==null).collect(Collectors.toCollection(ArrayList::new));
        } catch (Exception e) {
            throw new FracasoOperacion("Error al obtener los consumos de la esstadia no pagados : "+ e.getMessage());
        }
    }
    @Override
    public Consumo obtenerConsumoPorId(Long idConsumo) throws FracasoOperacion{
        try{
            return consumoRepository.findById(idConsumo).get();
        } catch (Exception e) {
            throw new FracasoOperacion("Error al obtener el consumo por id:" + e.getMessage());
        }
    }
}

package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Factura;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class FacturaDAOMySQL implements FacturaDAO {
    @Autowired
    private FacturaRepository facturaRepository;

    @Override
    public void crearFactura(Factura factura) throws FracasoOperacion {
        try{
            facturaRepository.save(factura);
        }
        catch(Exception e){
            throw new FracasoOperacion("Error al crear factura: " + e.getMessage());
        }
    }

    @Override
    public void obtenerFactura() throws FracasoOperacion {

    }

    @Override
    public void modificarFactura() throws FracasoOperacion {

    }

    @Override
    public void eliminarFactura() throws FracasoOperacion {

    }
}

package org.example.TP_DESO.dao;

import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class MetodoPagoDAOMySQL implements MetodoPagoDAO{
    //@Autowired
    // Private MetodoPagoRepository metodoPagoRepository;

    @Override
    public void crearMonedaEfectivo() throws FracasoOperacion {

    }

    @Override
    public void obtenerMonedaEfectivo() throws FracasoOperacion {

    }

    @Override
    public void modificarMonedaEfectivo() throws FracasoOperacion {

    }

    @Override
    public void eliminarMonedaEfectivo() throws FracasoOperacion {

    }

    @Override
    public void crearTarjetaCredito() throws FracasoOperacion {

    }

    @Override
    public void obtenerTarjetaCredito() throws FracasoOperacion {

    }

    @Override
    public void modificarTarjetaCredito() throws FracasoOperacion {

    }

    @Override
    public void eliminarTarjetaCredito() throws FracasoOperacion {

    }

    @Override
    public void crearTarjetaDebito() throws FracasoOperacion {

    }

    @Override
    public void obtenerTarjetaDebito() throws FracasoOperacion {

    }

    @Override
    public void modificarTarjetaDebito() throws FracasoOperacion {

    }

    @Override
    public void eliminarTarjetaDebito() throws FracasoOperacion {

    }

    @Override
    public void crearCheque() throws FracasoOperacion {

    }

    @Override
    public void obtenerCheque() throws FracasoOperacion {

    }

    @Override
    public void modificarCheque() throws FracasoOperacion {

    }

    @Override
    public void eliminarCheque() throws FracasoOperacion {

    }
}

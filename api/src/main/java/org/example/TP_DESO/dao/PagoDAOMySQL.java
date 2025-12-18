package org.example.TP_DESO.dao;

import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class PagoDAOMySQL implements PagoDAO{
    //@Autowired
    // -> agregar repositorio de pagos

    @Override
    public void crearPago() throws FracasoOperacion {

    }

    @Override
    public void obtenerPago() throws FracasoOperacion {

    }

    @Override
    public void modificarPago() throws FracasoOperacion {

    }

    @Override
    public void eliminarPago() throws FracasoOperacion {

    }

    @Override
    public void crearPagoParcial() throws FracasoOperacion {

    }

    @Override
    public void obtenerPagoParcial() throws FracasoOperacion {

    }

    @Override
    public void modificarPagoParcial() throws FracasoOperacion {

    }

    @Override
    public void eliminarPagoParcial() throws FracasoOperacion {

    }
}

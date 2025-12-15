package org.example.TP_DESO.dao;

import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.NotaCreditoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Primary
@Service
public class NotaCreditoDAOMySQL implements NotaCreditoDAO {
    @Autowired
    private NotaCreditoRepository notaCreditoRepository;

    @Override
    public void crearNotaCredito() throws FracasoOperacion {

    }

    @Override
    public void obtenerNotaCredito() throws FracasoOperacion {

    }

    @Override
    public void modificarNotaCredito() throws FracasoOperacion {

    }

    @Override
    public void eliminarNotaCredito() throws FracasoOperacion {

    }
}

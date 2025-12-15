package org.example.TP_DESO.dao;

import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResponsablePagoDAOMySQL implements ResponsablePagoDAO{
    //@Autowired
    // -> Agregar repositorio para las personas fisicas y juridicas

    // FUNCIONES PARA LA PERSONA FISICA (NO SOY UNA IA SOY JUAN)
    @Override
    public void crearPersonaFisica() throws FracasoOperacion{

    }
    @Override
    public void obtenerPersonaFisica() throws FracasoOperacion{

    }
    @Override
    public void modificarPersonaFisica() throws FracasoOperacion{

    }
    @Override
    public void eliminarPersonaFisica() throws FracasoOperacion{

    }

    // FUNCIONES PARA LA PERSONA JURIDICA (TAMPOCO SOY UNA IA SOY JUAN)
    @Override
    public void crearPersonaJuridica() throws FracasoOperacion{

    }
    @Override
    public void obtenerPersonaJuridica() throws FracasoOperacion{

    }
    @Override
    public void modificarPersonaJuridica() throws FracasoOperacion{

    }
    @Override
    public void eliminarPersonaJuridica() throws FracasoOperacion{

    }
}

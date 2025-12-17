package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.PersonaFisica;
import org.example.TP_DESO.domain.PersonaJuridica;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.PersonaFisicaRepository;
import org.example.TP_DESO.repository.PersonaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResponsablePagoDAOMySQL implements ResponsablePagoDAO{
    @Autowired
    private PersonaFisicaRepository pfRepository;
    @Autowired
    private PersonaJuridicaRepository pjRepository;



    // FUNCIONES PARA LA PERSONA FISICA (NO SOY UNA IA SOY JUAN)
    @Override
    public void crearPersonaFisica() throws FracasoOperacion{

    }
    @Override
    public PersonaFisica obtenerPersonaFisica(String cuit) throws FracasoOperacion{
        try{
            return pfRepository.findByHuesped_Cuil(cuit);
        }
        catch (Exception e) {
            throw new FracasoOperacion("Error: " + e.getMessage());
        }
    }
    @Override
    public List<PersonaFisica> obtenerTodasPersonaFisica() throws FracasoOperacion{
        try{
            return pfRepository.findAll();
        }
        catch(Exception e){throw new FracasoOperacion("Error:" + e.getMessage());}
    }
    @Override
    public void modificarPersonaFisica() throws FracasoOperacion{

    }
    @Override
    public void eliminarPersonaFisica() throws FracasoOperacion{

    }

    @Override
    public void crearPersonaJuridica() throws FracasoOperacion {

    }

    // FUNCIONES PARA LA PERSONA JURIDICA (TAMPOCO SOY UNA IA SOY JUAN), pero las hizo nacho
    @Override
    public PersonaJuridica crearPersonaJuridica(PersonaJuridica personaJuridica) throws FracasoOperacion {
        try {
            if (personaJuridica == null) {
                throw new FracasoOperacion("PersonaJuridica no puede ser null");
            }
            return pjRepository.save(personaJuridica);
        } catch (Exception e) {
            throw new FracasoOperacion("Error al crear PersonaJuridica: " + e.getMessage());
        }
    }
    @Override
    public PersonaJuridica obtenerPersonaJuridica(String cuit) throws FracasoOperacion{
        try{
            return pjRepository.findByCuit(cuit);
        }
        catch (Exception e){
            throw new  FracasoOperacion("Error:" + e.getMessage());
        }
    }
    @Override
    public void modificarPersonaJuridica() throws FracasoOperacion{

    }
    @Override
    public void eliminarPersonaJuridica() throws FracasoOperacion{

    }
}

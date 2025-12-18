package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Direccion;
import org.example.TP_DESO.domain.PersonaFisica;
import org.example.TP_DESO.domain.PersonaJuridica;
import org.example.TP_DESO.dto.CU12.PersonaJuridicaDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.PersonaFisicaRepository;
import org.example.TP_DESO.repository.PersonaJuridicaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResponsablePagoDAOMySQL implements ResponsablePagoDAO{
    @Autowired
    private PersonaFisicaRepository pfRepository;
    @Autowired
    private PersonaJuridicaRepository pjRepository;
    @Autowired
    private DireccionDAOMySQL direccionDAO;

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
    public void modificarPersonaJuridica(Long id, String razonSocial, String cuit, String telefono, Direccion direccion) throws FracasoOperacion{
        try{
            Optional<PersonaJuridica> existente = pjRepository.findById(id);
            if(existente.isPresent()){
                PersonaJuridica pj = existente.get();
                try{
                    pjRepository.delete(pj);
                } catch (Exception e) {
                    throw new FracasoOperacion("No se pudo eliminar a la persona juridica: " + e.getMessage());
                }

                pj.setRazonSocial(razonSocial);
                pj.setCuit(cuit);
                pj.setTelefono(telefono);

                if(pj.getDireccion() != null) {
                    Direccion direccionProcesada = direccionDAO.crearDireccion(pj.getDireccion());
                    pj.setDireccion(direccionProcesada);
                }

                pjRepository.save(pj);
            }
        } catch (Exception e) {
            throw new FracasoOperacion("Error al modificar PersonaJuridica: " + e.getMessage());
        }
    }
    @Override
    public void eliminarPersonaJuridica() throws FracasoOperacion{

    }
}

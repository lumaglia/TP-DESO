package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.DobleEstandar;
import org.example.TP_DESO.domain.Habitacion;
import org.example.TP_DESO.domain.IndividualEstandar;
import org.example.TP_DESO.domain.SuiteDoble;
import org.example.TP_DESO.dto.HabitacionDTO;
import org.example.TP_DESO.dao.Mappers.HabitacionMapper;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.HabitacionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Service
public class HabitacionDAOMySQL implements HabitacionDAO{
    @Autowired
    private HabitacionRepository habitacionRepository;

    @Override
    public void crearHabitacion(Habitacion habitacion) throws FracasoOperacion {
        try{
            habitacionRepository.save(habitacion);
        } catch (Exception e) {
            throw new FracasoOperacion("Error al guardar la habitacion en la bdd: " +  e.getMessage());
        }
    }

    @Override
    public void modificarHabitacion(Long id, Habitacion habitacion) throws FracasoOperacion {
        try{
            Optional<Habitacion> existente = habitacionRepository.findById(String.valueOf(id));
            if(existente.isPresent()){
                Habitacion h = existente.get();

                h.setCapacidad(existente.get().getCapacidad());
                h.setNroHabitacion(existente.get().getNroHabitacion());
                h.setTamanno(existente.get().getTamanno());
                h.setPrecioNoche(existente.get().getPrecioNoche());

                habitacionRepository.save(h);
            }
            else{
                throw new FracasoOperacion("No se ha encontrado a la habitacion" + id);
            }
        } catch (Exception e) {
            throw new FracasoOperacion("Error al modificar la habitacion: " + e.getMessage());
        }
    }

    @Override
    public void eliminarHabitacion(Long id) throws FracasoOperacion {
        try{
            Optional<Habitacion> h = habitacionRepository.findById(String.valueOf(id));
            if(h.isPresent()){
                habitacionRepository.delete(h.get());
            }
            else{
                throw new FracasoOperacion("Error al eliminar la habitacion en la bdd: " + id);
            }
        }
        catch (Exception e){
            throw new FracasoOperacion("Error al eliminar:" + e.getMessage());
        }
    }

    @Override
    public HabitacionDTO obtenerHabitacion(Long id) throws FracasoOperacion {
        return null;
    }

    @Override
    public ArrayList<HabitacionDTO> obtenerTodas() throws FracasoOperacion {
        try{
            ArrayList<HabitacionDTO> resultado = new ArrayList<>();
            List<Habitacion> bddResult = habitacionRepository.findAll();

            for(Habitacion h : bddResult){
                resultado.add(HabitacionMapper.toDTO(h));
            }

            return resultado;
        }
        catch (Exception e) {
            throw new FracasoOperacion("Error al recuperar las habitaciones" + e.getMessage());
        }
    }

    @Override
    public HabitacionDTO buscarPorNumero(int numero) throws FracasoOperacion {
        return null;
    }

    @Override
    public ArrayList<HabitacionDTO> buscarHabitacionesOcupadas(LocalDate desde, LocalDate hasta) throws FracasoOperacion {
        return null;
    }

    @Override
    public ArrayList<HabitacionDTO> buscarHabitacionesDisponibles(LocalDate desde, LocalDate hasta) throws FracasoOperacion {
        return null;
    }

    @Override
    public ArrayList<HabitacionDTO> buscarPorTipo(String tipoHabitacion) throws FracasoOperacion {
        return null;
    }
}
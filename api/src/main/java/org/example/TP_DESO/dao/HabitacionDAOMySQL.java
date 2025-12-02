package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.*;
import org.example.TP_DESO.dto.EstadiaDTO;
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
import java.util.Objects;
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
    public HabitacionDTO obtenerHabitacion(String id) throws FracasoOperacion {
        try{
            Habitacion h = habitacionRepository.getByNroHabitacion(id);

            if(h != null){
                return HabitacionMapper.toDTO(h);
            }
            else{
                throw new FracasoOperacion("No se ha encontrado la habitación de ID:"+id);
            }
        }
        catch (Exception e){
            throw new FracasoOperacion("No se pudo obtener la habitacion " + id + ":" + e.getMessage());
        }
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

    @Override // No es lo mismo que obtenerHabitacion?
    public HabitacionDTO buscarPorNumero(int numero) throws FracasoOperacion {
        try{
            return this.obtenerHabitacion(String.valueOf(numero));
        } catch (FracasoOperacion e) {
            throw new FracasoOperacion("Error al buscar por numero: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<HabitacionDTO> buscarHabitacionesOcupadas(LocalDate desde, LocalDate hasta) throws FracasoOperacion {
        try{
            ArrayList<HabitacionDTO> resultado = new ArrayList<>();
            EstadiaDAOMySQL daoEstadias = new EstadiaDAOMySQL();

            ArrayList<EstadiaDTO> estadias = daoEstadias.obtenerEstadiaEntreFechas(desde, hasta);
            for(EstadiaDTO e : estadias) resultado.add(e.getHabitacion());

            return resultado;
        } catch (Exception e) {
            throw new FracasoOperacion("No se pudo recuperar las habitaciones ocupadas" + e.getMessage());
        }
    }

    @Override
    public ArrayList<HabitacionDTO> buscarHabitacionesDisponibles(LocalDate desde, LocalDate hasta) throws FracasoOperacion {
        try{
            ArrayList<HabitacionDTO> totales = this.obtenerTodas();
            ArrayList<HabitacionDTO> ocupadas = this.buscarHabitacionesOcupadas(desde, hasta);

            totales.removeAll(ocupadas);

            return totales;
        } catch (Exception e) {
            throw new FracasoOperacion("No se pudo buscar las habitaciones disponibles" + e.getMessage());
        }
    }

    @Override
    public ArrayList<HabitacionDTO> buscarPorTipo(String tipoHabitacion) throws FracasoOperacion {
        try{
            ArrayList<HabitacionDTO> resultado = new ArrayList<>();
            List<Habitacion> bddResult = habitacionRepository.findAll();
            for(Habitacion h : bddResult){
                if(h instanceof SuiteDoble && Objects.equals(tipoHabitacion, "Suite Doble")){
                    resultado.add(HabitacionMapper.toDTO(h));
                }
                else if(h instanceof DobleEstandar && Objects.equals(tipoHabitacion, "Doble Estandar")){
                    resultado.add(HabitacionMapper.toDTO(h));
                }
                else if(h instanceof IndividualEstandar && Objects.equals(tipoHabitacion, "Individual Estándar")){
                    resultado.add(HabitacionMapper.toDTO(h));
                }
                else if(h instanceof SuperiorFamilyPlan && Objects.equals(tipoHabitacion, "Superior Family Plan")){
                    resultado.add(HabitacionMapper.toDTO(h));
                }
            }
            return resultado;
        }
        catch (Exception e){
            throw new FracasoOperacion("Error al buscar por tipo: " + e.getMessage());
        }
    }
}
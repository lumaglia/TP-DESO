package org.example.TP_DESO.dao;


import org.example.TP_DESO.domain.Estadia;
import org.example.TP_DESO.dto.EstadiaDTO;
import org.example.TP_DESO.dto.HabitacionDTO;
import org.example.TP_DESO.dto.HabitacionMapper;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.EstadiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Service
public class EstadiaDAOMySQL implements EstadiaDAO{

    @Autowired
    private EstadiaRepository estadiaRepository;

    @Override
    public void crearEstadia(Estadia estadia) throws FracasoOperacion {
        try {
            estadiaRepository.save(estadia);
        } catch (Exception e) {
            throw new FracasoOperacion("Error al crear estadía: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<EstadiaDTO> obtenerEstadiaEntreFechas(LocalDate fechaInicio, LocalDate fechaFin)
            throws FracasoOperacion {

        try {
            List<Estadia> estadias = estadiaRepository
                    .findByFechaInicioBetween(fechaInicio, fechaFin);

            if (estadias.isEmpty()) {
                throw new FracasoOperacion("No existen estadías en ese rango de fechas");
            }

            ArrayList<EstadiaDTO> resultado = new ArrayList<>();

            for (Estadia e : estadias) {

                HabitacionDTO habitacionDTO = HabitacionMapper.toDTO(e.getHabitacion());

                resultado.add(new EstadiaDTO(
                        e.getId(),
                        e.getFechaInicio(),
                        e.getFechaFin(),
                        e.getReserva(),
                        e.getHuespedes(),
                        habitacionDTO
                ));
            }

            return resultado;

        } catch (Exception ex) {
            throw new FracasoOperacion("Error al obtener estadías: " + ex.getMessage());
        }
    }




    @Override
    public void modificarEstadia(Long idEstadia, Estadia estadia) throws FracasoOperacion {
        try {
            Optional<Estadia> existente = estadiaRepository.findById(idEstadia);
            if (existente.isPresent()) {
                Estadia e = existente.get();
                // Actualizar campos
                e.setFechaInicio(estadia.getFechaInicio());
                e.setFechaFin(estadia.getFechaFin());
                e.setHabitacion(estadia.getHabitacion());
                e.setReserva(estadia.getReserva());
                e.setHuespedes(estadia.getHuespedes());

                estadiaRepository.save(e);

            } else {
                throw new FracasoOperacion("Estadia no encontrada");
            }
        } catch (Exception e) {
            throw new FracasoOperacion("Error al modificar la estadia: " + e.getMessage());
        }

    }

    @Override
    public void eliminarEstadia(Long idEstadia) throws FracasoOperacion {
        try {
            Optional<Estadia> existente = estadiaRepository.findById(idEstadia);

            if (existente.isPresent()) {

                estadiaRepository.delete(existente.get());

            } else {
                throw new FracasoOperacion("Estadía no encontrada con ID: " + idEstadia);
            }

        } catch (Exception e) {
            throw new FracasoOperacion("Error al eliminar estadía: " + e.getMessage());
        }
    }




}

package org.example.TP_DESO.dao;


import org.example.TP_DESO.domain.Estadia;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.dto.*;
import org.example.TP_DESO.patterns.mappers.EstadiaMapper;
import org.example.TP_DESO.patterns.mappers.HabitacionMapper;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.EstadiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Primary
@Service
@Transactional
public class EstadiaDAOMySQL implements EstadiaDAO{

    @Autowired
    private EstadiaRepository estadiaRepository;

    @Override
    public void crearEstadia(Estadia estadia) throws FracasoOperacion {
        try {
            if (estadia == null) {
                throw new FracasoOperacion("La estadia no puede ser null");
            }
            estadiaRepository.save(estadia);

        } catch (Exception e) {
            throw new FracasoOperacion("Error al crear estadía: " + e.getMessage());
        }
    }

    @Override
    public Estadia obtenerEstadiaNroHabitacionFechaFin(String nroHabitacion, LocalDate fin) throws FracasoOperacion{
        try{
            return estadiaRepository.findByHabitacionNroHabitacionAndFechaFin(nroHabitacion, fin).get();
        } catch (Exception e) {
            throw new FracasoOperacion("Error al obtener la estadia en su forma domain (nroHabitacion y fecha fin):" + e.getMessage());
        }
    }

    @Override
    public ArrayList<EstadiaDTO> obtenerEstadiaEntreFechas(LocalDate fechaInicio, LocalDate fechaFin)
            throws FracasoOperacion {

        try {
            List<Estadia> estadias = estadiaRepository
                    .findByFechaFinGreaterThanEqualAndFechaInicioLessThanEqual(fechaInicio, fechaFin);

            if (estadias.isEmpty()) {
                throw new FracasoOperacion("No existen estadías en ese rango de fechas");
            }

            ArrayList<EstadiaDTO> resultado = new ArrayList<>();

            for (Estadia e : estadias) {
                resultado.add(EstadiaMapper.toDTO(e));
            }

            return resultado;

        } catch (Exception ex) {
            throw new FracasoOperacion("Error al obtener estadías: " + ex.getMessage());
        }
    }

    @Override
    public ArrayList<Estadia> obtenerEstadiaEntreFechasDomainForm(LocalDate fechaInicio, LocalDate fechaFin)
        throws FracasoOperacion {
        try{
            ArrayList<Estadia> estadias = estadiaRepository.findByFechaFinGreaterThanEqualAndFechaInicioLessThanEqual(fechaInicio, fechaFin);

            if (estadias.isEmpty()) {
                throw new FracasoOperacion("No existen estadías en ese rango de fechas");
            }

            return estadias;
        }
        catch (Exception e){
            throw new FracasoOperacion("Error al obtener estadías: " + e.getMessage());
        }
    }

    @Override
    public void modificarEstadia(Long idEstadia, Estadia estadia) throws FracasoOperacion {
        try {
            Optional<Estadia> existente = estadiaRepository.findById(idEstadia);
            if (existente.isPresent()) {
                Estadia e = existente.get();

                e.setFechaInicio(estadia.getFechaInicio());
                e.setFechaFin(estadia.getFechaFin());
                e.setHabitacion(estadia.getHabitacion());
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

    @Override
    public EstadiaDTO buscarEstadiaPorHabitacionYFechaFin(String numeroHabitacion, LocalDate fechaFin) throws FracasoOperacion {
        try {
            Optional<Estadia> estadiaOpt = estadiaRepository.findByHabitacionNroHabitacionAndFechaFin(numeroHabitacion, fechaFin);

            if (estadiaOpt.isEmpty()) {
                throw new FracasoOperacion("Estadía no encontrada con habitacion y fecha fin: " + numeroHabitacion + ", " + fechaFin);
            }

            Estadia e = estadiaOpt.get();
            HabitacionDTO habitacionDTO = HabitacionMapper.toDTO(e.getHabitacion());
            ArrayList<HuespedDTO> huespedDTO = new ArrayList<HuespedDTO>();

            for (Huesped h : e.getHuespedes()) {

                DireccionDTO direccionDTO = null;
                if (h.getDireccion() != null) {
                    direccionDTO = new DireccionDTO(
                            h.getDireccion().getDomicilio(),
                            h.getDireccion().getDepto(),
                            h.getDireccion().getCodigoPostal(),
                            h.getDireccion().getLocalidad(),
                            h.getDireccion().getProvincia(),
                            h.getDireccion().getPais()
                    );
                }

                huespedDTO.add(new HuespedDTO(
                        h.getTipoDoc(),
                        h.getNroDoc(),
                        h.getApellido(),
                        h.getNombre(),
                        h.getCuil(),
                        h.getPosicionIva(),
                        h.getFechaNac(),
                        h.getTelefono(),
                        h.getEmail(),
                        h.getOcupacion(),
                        h.getNacionalidad(),
                        direccionDTO
                ));
            }

            return new EstadiaDTO(
                    e.getIdEstadia(),
                    e.getFechaInicio(),
                    e.getFechaFin(),
                    huespedDTO,
                    habitacionDTO
            );

        } catch (Exception ex) {
            throw new FracasoOperacion(
                    "Error al buscar estadía por habitación y fecha fin: " + ex.getMessage()
            );
        }
    }

    @Override
    public ArrayList<EstadiaDTO> obtenerEstadiasDeHuesped(String tipoDoc, String nroDoc)
            throws FracasoOperacion {

        try {
            ArrayList<Estadia> estadias = estadiaRepository.findByHuespedesTipoDocAndHuespedesNroDoc(tipoDoc, nroDoc);

            if (estadias.isEmpty()) {
                throw new FracasoOperacion("El huésped no posee estadías registradas");
            }

            ArrayList<EstadiaDTO> resultado = new ArrayList<>();

            for (Estadia e : estadias) {

                HabitacionDTO habitacionDTO = HabitacionMapper.toDTO(e.getHabitacion());
                ArrayList<HuespedDTO> huespedDTO = new ArrayList<>();

                for (Huesped h : e.getHuespedes()) {

                    DireccionDTO direccionDTO = null;
                    if (h.getDireccion() != null) {
                        direccionDTO = new DireccionDTO(
                                h.getDireccion().getDomicilio(),
                                h.getDireccion().getDepto(),
                                h.getDireccion().getCodigoPostal(),
                                h.getDireccion().getLocalidad(),
                                h.getDireccion().getProvincia(),
                                h.getDireccion().getPais()
                        );
                    }

                    huespedDTO.add(new HuespedDTO(
                            h.getTipoDoc(),
                            h.getNroDoc(),
                            h.getApellido(),
                            h.getNombre(),
                            h.getCuil(),
                            h.getPosicionIva(),
                            h.getFechaNac(),
                            h.getTelefono(),
                            h.getEmail(),
                            h.getOcupacion(),
                            h.getNacionalidad(),
                            direccionDTO
                    ));
                }

                resultado.add(new EstadiaDTO(
                        e.getFechaInicio(),
                        e.getFechaFin(),
                        huespedDTO,
                        habitacionDTO
                ));
            }

            return resultado;

        } catch (Exception e) {
            throw new FracasoOperacion("Error al buscar estadías del huésped: " + e.getMessage());
        }
    }
    public boolean existeEstadiaDeHuesped(String tipoDoc, String nroDoc) {
        return estadiaRepository.existsByHuespedes_TipoDocAndHuespedes_NroDoc(tipoDoc, nroDoc);
    }



}

package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Habitacion;
import org.example.TP_DESO.domain.Reserva;
import org.example.TP_DESO.dto.EstadiaDTO;
import org.example.TP_DESO.dto.HabitacionDTO;
import org.example.TP_DESO.patterns.mappers.EstadiaMapper;
import org.example.TP_DESO.patterns.mappers.HabitacionMapper;
import org.example.TP_DESO.patterns.mappers.ReservaMapper;
import org.example.TP_DESO.dto.ReservaDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

@Service
@Transactional
public class ReservaDAOMySQL implements ReservaDAO{

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public void crearReserva(Reserva reserva) throws FracasoOperacion {
        try {

            if (reserva == null) {
                throw new FracasoOperacion("La reserva no puede ser null");
            }

            reservaRepository.save(reserva);

        } catch (Exception e) {
            throw new FracasoOperacion("Error al crear la reserva: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<ReservaDTO> obtenerReservasEntreFechas(LocalDate fechaInicio, LocalDate fechaFin)
            throws FracasoOperacion {

        try {

            ArrayList<Reserva> reservas =
                    reservaRepository.findByFechaFinGreaterThanEqualAndFechaInicioLessThanEqual(fechaInicio, fechaFin);

            if (reservas.isEmpty()) {
                return new ArrayList<ReservaDTO>();
            }

            ArrayList<ReservaDTO> resultado = new ArrayList<>();

            for (Reserva r : reservas) {

                HabitacionDTO habitacionDTO = null;
                if(r.getHabitacion() != null) {
                    habitacionDTO = HabitacionMapper.toDTO(r.getHabitacion());
                }

                EstadiaDTO estadiaDTO = null;
                if (r.getEstadia() != null) {
                    estadiaDTO = EstadiaMapper.toDTO(r.getEstadia());
                }

                resultado.add(new ReservaDTO(
                        r.getIdReserva(),
                        r.getFechaReserva(),
                        r.getFechaInicio(),
                        r.getFechaFin(),
                        r.getApellido(),
                        r.getNombre(),
                        r.getTelefono(),
                        r.isCancelada(),
                        habitacionDTO,
                        estadiaDTO
                ));
            }

            return resultado;

        } catch (Exception e) {
            throw new FracasoOperacion("Error al obtener reservas: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<Reserva> obtenerReservasEntreFechasDomainForm(LocalDate fechaInicio, LocalDate fechaFin)
            throws FracasoOperacion{
        try{
            ArrayList<Reserva> reservas =
                    reservaRepository.findByFechaFinGreaterThanEqualAndFechaInicioLessThanEqual(fechaInicio, fechaFin);

            if (reservas.isEmpty()) throw new FracasoOperacion("No hay reservas en ese rango de fechas");

            return reservas;
        }
        catch (Exception e){
            throw new FracasoOperacion("Error al obtener reservas: " + e.getMessage());
        }
    }

    @Override
    public void modificarReserva(Long idReserva, Reserva reserva) throws FracasoOperacion {

        try {
            Optional<Reserva> existente = reservaRepository.findById(idReserva);

            if (existente.isPresent()) {

                Reserva r = existente.get();

                r.setFechaReserva(reserva.getFechaReserva());
                r.setFechaInicio(reserva.getFechaInicio());
                r.setFechaFin(reserva.getFechaFin());
                r.setApellido(reserva.getApellido());
                r.setNombre(reserva.getNombre());
                r.setTelefono(reserva.getTelefono());
                r.setCancelada(reserva.isCancelada());
                r.setHabitacion(reserva.getHabitacion());
                r.setEstadia(reserva.getEstadia());

                reservaRepository.save(r);

            } else {
                throw new FracasoOperacion("Reserva no encontrada con ID: " + idReserva);
            }

        } catch (Exception e) {
            throw new FracasoOperacion("Error al modificar reserva: " + e.getMessage());
        }
    }

    @Override
    public void eliminarReserva(Long idReserva) throws FracasoOperacion {

        try {
            Optional<Reserva> existente = reservaRepository.findById(idReserva);

            if (existente.isPresent()) {
                reservaRepository.delete(existente.get());
            } else {
                throw new FracasoOperacion("Reserva no encontrada con ID: " + idReserva);
            }

        } catch (Exception e) {
            throw new FracasoOperacion("Error al eliminar reserva: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<ReservaDTO> buscarReservasPorApellidoYNombre(String apellido, String nombre) throws FracasoOperacion {

        try {
            ArrayList<Reserva> reservas;

            if (nombre == null || nombre.isBlank()) {
                reservas = reservaRepository.findByApellido(apellido);
            } else {
                reservas = reservaRepository.findByApellidoAndNombre(apellido, nombre);
            }

            ArrayList<ReservaDTO> resultado = new ArrayList<>();

            for (Reserva r : reservas) {
                resultado.add(ReservaMapper.toDTO(r));
            }

            return resultado;

        } catch (Exception e) {
            throw new FracasoOperacion("Error al buscar reservas: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<ReservaDTO> buscarReservasPorHabitacionFechaInicio(Habitacion habitacion, LocalDate fechaInicio) throws FracasoOperacion{
        try {
            ArrayList<Reserva> reservas;

            reservas = reservaRepository.findByHabitacionAndFechaInicioAndCancelada(habitacion,fechaInicio,false);

            ArrayList<ReservaDTO> resultado = new ArrayList<>();

            for (Reserva r : reservas) {
                resultado.add(ReservaMapper.toDTO(r));
            }

            return resultado;

        } catch (Exception e) {
            throw new FracasoOperacion("Error al buscar reservas: " + e.getMessage());
        }
    }




}

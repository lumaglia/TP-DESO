package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.*;
import org.example.TP_DESO.dao.Mappers.HabitacionMapper;
import org.example.TP_DESO.dao.Mappers.HuespedMapper;
import org.example.TP_DESO.dao.Mappers.ReservaMapper;
import org.example.TP_DESO.domain.*;
import org.example.TP_DESO.dto.*;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class GestorReserva {
    private static GestorReserva singleton_instance;

    @Autowired
    private ReservaDAOMySQL daoReserva;
    @Autowired
    private EstadiaDAOMySQL daoEstadia;
    @Autowired
    private GestorHabitacion gestorHabitacion;

    private GestorReserva() {
    }

    public synchronized static GestorReserva getInstance() {
        if(singleton_instance == null) singleton_instance = new GestorReserva();
        return singleton_instance;
    }

    public String hacerReserva(ReservaDTO reservaDTO) throws FracasoOperacion {
        try{
            Reserva nuevaReserva = new Reserva();

            nuevaReserva.setFechaReserva(reservaDTO.getFechaReserva());
            nuevaReserva.setIdReserva(reservaDTO.getId());
            nuevaReserva.setApellido(reservaDTO.getApellido());
            nuevaReserva.setNombre(reservaDTO.getNombre());
            nuevaReserva.setTelefono(reservaDTO.getTelefono());
            nuevaReserva.setEstadia(null);
            nuevaReserva.setHabitacion(HabitacionMapper.toDomain(reservaDTO.getHabitacion()));
            nuevaReserva.setFechaInicio(reservaDTO.getFechaInicio());
            nuevaReserva.setFechaFin(reservaDTO.getFechaFin());
            nuevaReserva.setCancelada(false);

            daoReserva.crearReserva(nuevaReserva);

            return "Reserva creada exitosamente";
        } catch (Exception e) {
            throw new FracasoOperacion("Error al guardar la reserva" + e.getMessage());
        }
    }

    public void mostrarReserva(){

    }

    public boolean cancelarReserva() throws  FracasoOperacion {
        return false;
    }

    public boolean checkIn(EstadiaDTO estadiaDTO) throws FracasoOperacion{
        try{
            ArrayList<Huesped> huespedes = new ArrayList<>();

            for(HuespedDTO h : estadiaDTO.getHuespedes()){
                huespedes.add(HuespedMapper.toDomain(h));
            }

            Habitacion habitacion = HabitacionMapper.toDomain(gestorHabitacion.obtenerHabitacion(estadiaDTO.getHabitacion().getNroHabitacion()));

            ArrayList<ReservaDTO> reservaDTOS = daoReserva.obtenerReservasEntreFechas(estadiaDTO.getFechaInicio(), estadiaDTO.getFechaFin());
            Stream<ReservaDTO> reservaDTOStream = reservaDTOS.stream();
            ArrayList<Reserva> reservaList = reservaDTOStream
                    .filter(p -> Objects.equals(p.getHabitacion().getNroHabitacion(), habitacion.getNroHabitacion()) && !p.isCancelada()
                            && Objects.equals(p.getFechaInicio(),estadiaDTO.getFechaInicio())
                            && Objects.equals(p.getFechaFin(),estadiaDTO.getFechaFin())
                    )
                    .map(ReservaMapper::toDomain).collect(Collectors.toCollection(ArrayList::new));

            Estadia estadia = new Estadia();
            estadia.setHabitacion(habitacion);
            estadia.setFechaInicio(estadiaDTO.getFechaInicio());
            estadia.setFechaFin(estadiaDTO.getFechaFin());
            estadia.setHuespedes(huespedes);

            if(!reservaList.isEmpty()){
                Reserva reserva = reservaList.getFirst();
                reserva.setEstadia(estadia);
                daoReserva.modificarReserva(reserva.getIdReserva(),reserva);
            }

            daoEstadia.crearEstadia(estadia);
            return true;
        }
        catch (Exception e) {
            throw new FracasoOperacion("Error al hacer checkIn: " + e.getMessage());
        }
    }

    public void checkOut(EstadiaDTO estadiaDTO){

    }

    public ArrayList<ReservasEstadiasPorHabitacionDTO> getReservaEstadia(LocalDate desde, LocalDate hasta) throws FracasoOperacion {
        try{
            ArrayList<ReservasEstadiasPorHabitacionDTO> resultado = new ArrayList<>();
            ArrayList<HabitacionDTO> habitaciones = gestorHabitacion.mostrarHabitaciones();
            ArrayList<ReservaDTO> reservaDTOs = daoReserva.obtenerReservasEntreFechas(desde, hasta);
            ArrayList<EstadiaDTO> estadiaDTOS = daoEstadia.obtenerEstadiaEntreFechas(desde, hasta);

            Map<String, Integer> nroHabitacionToIndex = new HashMap<>();

            habitaciones.forEach((habitacion) -> {
                nroHabitacionToIndex.put(habitacion.getNroHabitacion(), nroHabitacionToIndex.size());
                resultado.add(new ReservasEstadiasPorHabitacionDTO(habitacion, new ArrayList<EstadiaDTO>(), new ArrayList<ReservaDTO>()));
            });

            reservaDTOs.forEach((reserva) -> {
                resultado.get(nroHabitacionToIndex.get(reserva.getHabitacion().getNroHabitacion())).getReservasAsociadas().add(reserva);
            });

            estadiaDTOS.forEach((estadia) -> {
                resultado.get(nroHabitacionToIndex.get(estadia.getHabitacion().getNroHabitacion())).getEstadiasAsociadas().add(estadia);
            });

            return resultado;
        }
        catch (Exception e){
            throw new FracasoOperacion("Error al obtener la reserva" + e.getMessage());
        }
    }
}

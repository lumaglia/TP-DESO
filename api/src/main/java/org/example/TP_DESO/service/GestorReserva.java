package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.*;
import org.example.TP_DESO.dao.Mappers.HabitacionMapper;
import org.example.TP_DESO.dao.Mappers.HuespedMapper;
import org.example.TP_DESO.dao.Mappers.ReservaMapper;
import org.example.TP_DESO.domain.*;
import org.example.TP_DESO.dto.*;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

@Service
public class GestorReserva {
    private static GestorReserva singleton_instance;

    @Autowired
    private ReservaDAOMySQL daoReserva;
    @Autowired
    private HabitacionDAOMySQL daoHabitacion;
    @Autowired
    private HuespedDAOMySQL daoHuesped;
    @Autowired
    private EstadiaDAOMySQL daoEstadia;

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
            ArrayList<HuespedDTO> huespedesDTO = new ArrayList<>();

            for(HuespedDTO h : estadiaDTO.getHuespedes()){
                huespedesDTO.addAll(daoHuesped.obtenerHuesped(h));
            }

            for(HuespedDTO h : huespedesDTO){
                huespedes.add(HuespedMapper.toDomain(h));
            }

            Habitacion habitacion = HabitacionMapper.toDomain(daoHabitacion.obtenerHabitacion(estadiaDTO.getHabitacion().getNroHabitacion()));

            ArrayList<ReservaDTO> reservaDTOS = daoReserva.obtenerReservasEntreFechas(estadiaDTO.getFechaInicio(), estadiaDTO.getFechaFin());
            Stream<ReservaDTO> reservaDTOStream = reservaDTOS.stream();
            ArrayList<Reserva> reservaList = (ArrayList<Reserva>) reservaDTOStream
                    .filter(p -> Objects.equals(p.getHabitacion().getNroHabitacion(), habitacion.getNroHabitacion()) && !p.isCancelada())
                    .map(p -> ReservaMapper.toDomain(p));

            Estadia estadia = new Estadia();
            estadia.setHabitacion(habitacion);
            estadia.setFechaInicio(LocalDate.now());
            estadia.setFechaFin(LocalDate.now());
            estadia.setHuespedes(huespedes);

            daoEstadia.crearEstadia(estadia);
            return true;
        }
        catch (Exception e) {
            throw new FracasoOperacion("Error al hacer checkIn: " + e.getMessage());
        }
    }

    public void checkOut(EstadiaDTO estadiaDTO){

    }

    public ArrayList<ReservasEstadiasPoHabitacionDTO> getReservaEstadia(LocalDate desde, LocalDate hasta) throws FracasoOperacion {
        try{
            ArrayList<ReservasEstadiasPoHabitacionDTO> resultado = new ArrayList<>();
            ArrayList<HabitacionDTO> habitaciones = daoHabitacion.obtenerTodas();

            for(HabitacionDTO h : habitaciones) {
                Stream<ReservaDTO> reservaDTOs = daoReserva.obtenerReservasEntreFechas(desde, hasta).stream().filter(p-> Objects.equals(p.getHabitacion().getNroHabitacion(), h.getNroHabitacion()));
                ArrayList<ReservaDTO> reservaList = (ArrayList<ReservaDTO>) reservaDTOs;

                Stream<EstadiaDTO> estadiaDTOS = daoEstadia.obtenerEstadiaEntreFechas(desde, hasta).stream().filter(p->Objects.equals(p.getHabitacion().getNroHabitacion(), h.getNroHabitacion()));
                ArrayList<EstadiaDTO> estadiaList = (ArrayList<EstadiaDTO>) estadiaDTOS;

                ReservasEstadiasPoHabitacionDTO e = new ReservasEstadiasPoHabitacionDTO(h, estadiaList, reservaList);
                resultado.add(e);
            }

            return resultado;
        }
        catch (Exception e){
            throw new FracasoOperacion("Error al obtener la reserva" + e.getMessage());
        }
    }
}

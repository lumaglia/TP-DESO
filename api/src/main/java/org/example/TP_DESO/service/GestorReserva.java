package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.*;
import org.example.TP_DESO.dao.Mappers.EstadiaMapper;
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
    private ReservaDAO daoReserva;
    @Autowired
    private EstadiaDAO daoEstadia;
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

    public ArrayList<ReservaDTO> buscarReserva(ReservaDTO reservaDTO) throws FracasoOperacion {
        return daoReserva.buscarReservasPorApellidoYNombre(reservaDTO.getApellido(), reservaDTO.getNombre());
    }

    public void cancelarReserva(ResponseReservaDTO responseReservaDTO) throws  FracasoOperacion {
        ArrayList<ReservaDTO> reservas = daoReserva.buscarReservasPorHabitacionFechaInicio(
                HabitacionMapper.toDomain(gestorHabitacion.obtenerHabitacion(responseReservaDTO.getNroHabitacion())),
                responseReservaDTO.getFechaInicio());
        if(reservas.isEmpty()){
            throw new FracasoOperacion("Error al cancelar reserva: No hay reservas pendientes de cancelar con los argumentos enviados");
        }else{
            Reserva reserva = ReservaMapper.toDomain(reservas.getFirst());
            reserva.setCancelada(true);
            daoReserva.modificarReserva(reserva.getIdReserva(),reserva);
        }
    }

    public boolean checkIn(EstadiaDTO estadiaDTO) throws FracasoOperacion{
        try{
            ArrayList<Huesped> huespedes = new ArrayList<>();

            for(HuespedDTO h : estadiaDTO.getHuespedes()){
                huespedes.add(HuespedMapper.toDomain(h));
            }

            Habitacion habitacion = HabitacionMapper.toDomain(gestorHabitacion.obtenerHabitacion(estadiaDTO.getHabitacion().getNroHabitacion()));

            /*List<ReservaDTO> reservaDTOS = daoReserva.obtenerReservasEntreFechas(estadiaDTO.getFechaInicio(), estadiaDTO.getFechaFin());
            Stream<ReservaDTO> reservaDTOStream = reservaDTOS.stream();
            List<Reserva> reservaList = reservaDTOStream
                    .filter(p -> Objects.equals(p.getHabitacion().getNroHabitacion(), habitacion.getNroHabitacion()) && !p.isCancelada()
                            && Objects.equals(p.getFechaInicio(),estadiaDTO.getFechaInicio())
                            && Objects.equals(p.getFechaFin(),estadiaDTO.getFechaFin())
                    )
                    .map(ReservaMapper::toDomain).collect(Collectors.toCollection(ArrayList::new));*/

            ArrayList<ReservaDTO> reservaDTOS = daoReserva.obtenerReservasEntreFechas(estadiaDTO.getFechaInicio(), estadiaDTO.getFechaFin());

            List<Reserva> reservaList = reservaDTOS.stream()
                    .filter(p -> Objects.equals(p.getHabitacion().getNroHabitacion(), habitacion.getNroHabitacion())
                            && !p.isCancelada()
                            && Objects.equals(p.getFechaInicio(), estadiaDTO.getFechaInicio())
                            && Objects.equals(p.getFechaFin(), estadiaDTO.getFechaFin()))
                    .map(ReservaMapper::toDomain)
                    .toList();


            Estadia estadia = new Estadia();
            estadia.setHabitacion(habitacion);
            estadia.setFechaInicio(estadiaDTO.getFechaInicio());
            estadia.setFechaFin(estadiaDTO.getFechaFin());
            estadia.setHuespedes(huespedes);

            daoEstadia.crearEstadia(estadia);

            if(!reservaList.isEmpty()){
                EstadiaDTO estadiaDTO1 = daoEstadia.buscarEstadiaPorHabitacionYFechaFin(habitacion.getNroHabitacion(), estadia.getFechaFin());
                Estadia estadia1 = EstadiaMapper.toDomain(estadiaDTO1);
                Reserva reserva = reservaList.getFirst();
                reserva.setEstadia(estadia1);
                daoReserva.modificarReserva(reserva.getIdReserva(),reserva);
            }

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
            ArrayList<Habitacion> hs = gestorHabitacion.mostrarHabitacionDomain();
            ArrayList<Reserva> rs;
            ArrayList<Estadia> es;
            try{
                rs = daoReserva.obtenerReservasEntreFechasDomainForm(desde, hasta);
            }
            catch (FracasoOperacion e) {
                rs = new ArrayList<>();
            }
            try{
                es = daoEstadia.obtenerEstadiaEntreFechasDomainForm(desde, hasta);
            }
            catch (FracasoOperacion e) {
                es = new ArrayList<>();
            }

            Map<String, Integer> nroHabitacionToIndex = new HashMap<>();

            hs.forEach((habitacion) -> {
                nroHabitacionToIndex.put(habitacion.getNroHabitacion(), nroHabitacionToIndex.size());
                resultado.add(new ReservasEstadiasPorHabitacionDTO(habitacion, new ArrayList<Estadia>(), new ArrayList<Reserva>()));
            });

            rs.forEach((reserva) -> {
                resultado.get(nroHabitacionToIndex.get(reserva.getHabitacion().getNroHabitacion()))
                        .getReservas()
                        .add(new ReservasEstadiasPorHabitacionDTO.ReservasAsociadas(reserva));
            });

            es.forEach((estadia) -> {
                resultado.get(nroHabitacionToIndex.get(estadia.getHabitacion().getNroHabitacion()))
                        .getEstadias()
                        .add(new ReservasEstadiasPorHabitacionDTO.EstadiasAsociadas(estadia));
            });

            return resultado;
        }
        catch (Exception e){
            throw new FracasoOperacion("Error al obtener la columna" + e.getMessage());
        }
    }
}

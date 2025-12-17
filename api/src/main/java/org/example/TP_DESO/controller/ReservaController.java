package org.example.TP_DESO.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.dao.Mappers.HabitacionMapper;
import org.example.TP_DESO.domain.DobleEstandar;
import org.example.TP_DESO.domain.IndividualEstandar;
import org.example.TP_DESO.domain.SuiteDoble;
import org.example.TP_DESO.domain.SuperiorFamilyPlan;
import org.example.TP_DESO.dto.*;
import org.example.TP_DESO.exceptions.DocumentoYaExistente;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.service.GestorHabitacion;
import org.example.TP_DESO.service.GestorHuesped;
import org.example.TP_DESO.service.GestorReserva;
import org.example.TP_DESO.dto.BuscarHuespedDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@RestController
public class ReservaController {
    private final GestorHuesped gestorHuesped;
    GestorReserva gestorReserva;
    GestorHabitacion gestorHabitacion;

    @Autowired
    public ReservaController(GestorReserva gestorReserva, GestorHabitacion gestorHabitacion, GestorHuesped gestorHuesped) {
        this.gestorReserva = gestorReserva;
        this.gestorHabitacion = gestorHabitacion;
        this.gestorHuesped = gestorHuesped;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/Habitacion/Buscar/{fechaInicio}/{fechaFin}")
    public ResponseEntity<ArrayList<ReservasEstadiasPorHabitacionDTO>> getReservasEstadiasPorHabitacion(@PathVariable LocalDate fechaInicio, @PathVariable LocalDate fechaFin) {
        try {
            return ResponseEntity.ok(gestorReserva.getReservaEstadia(fechaInicio, fechaFin));
        } catch (FracasoOperacion e) {
            throw new RuntimeException(e);
        }

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Habitacion/Reserva/Buscar")
    public ResponseEntity<ArrayList<ResponseReservaDTO>> buscarReserva(@RequestBody ReservaBusquedaDTO reservaBusquedaDTO) {
        ReservaDTO reservaDTO = new ReservaDTO(reservaBusquedaDTO.apellido, reservaBusquedaDTO.nombre);
        try {
            ArrayList<ResponseReservaDTO> reservas = gestorReserva.buscarReserva(reservaDTO)
                    .stream().filter(r -> !r.isCancelada() && r.getEstadia() == null).map(ResponseReservaDTO::new).collect(Collectors.toCollection(ArrayList::new));
            if(reservas.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return ResponseEntity.ok(reservas);
            }
        } catch (FracasoOperacion e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/Habitacion/Reserva/Cancelar")
    public ResponseEntity<ResponseReservaDTO> cancelarReserva(@RequestBody ResponseReservaDTO responseReservaDTO) {
        try {
            gestorReserva.cancelarReserva(responseReservaDTO);
            return ResponseEntity.ok(responseReservaDTO);
        } catch (FracasoOperacion e) {
            throw new RuntimeException(e);
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Habitacion/Reservar")
    public ResponseEntity<ArrayList<ReservaDTO>> hacerReserva(@RequestBody ArrayList<RequestReservaDTO> reservasDTO) {
        ArrayList<ReservaDTO> response = new ArrayList<>();
        reservasDTO.forEach(requestReservaDTO -> {
            try {
                System.out.print(requestReservaDTO.getNroHabitacion());
                HabitacionDTO habitacionDTO = gestorHabitacion.obtenerHabitacion(requestReservaDTO.getNroHabitacion());
                ReservaDTO reservaDTO = ReservaDTO.builder()
                        .fechaReserva(LocalDate.now())
                        .fechaInicio(requestReservaDTO.getFechaInicio())
                        .fechaFin(requestReservaDTO.getFechaFin())
                        .apellido(requestReservaDTO.getApellido())
                        .nombre(requestReservaDTO.getNombre())
                        .telefono(requestReservaDTO.getTelefono())
                        .habitacion(habitacionDTO).build();

                gestorReserva.hacerReserva(reservaDTO);
                response.add(reservaDTO);
            } catch (FracasoOperacion e) {
                throw new RuntimeException(e);
            }
        });

        return ResponseEntity.ok(response);

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Habitacion/Ocupar")
    public ResponseEntity<EstadiaDTO> hacerReserva(@RequestBody RequestEstadiaDTO estadiaDTO) {
        System.out.print(estadiaDTO.toString());

        try {
            System.out.print(estadiaDTO.getNroHabitacion());
            HabitacionDTO habitacionDTO = gestorHabitacion.obtenerHabitacion(estadiaDTO.getNroHabitacion());

            ArrayList<HuespedDTO> huespedDTO = estadiaDTO.getHuespedes().stream().map(h ->
            {
                try {
                    return gestorHuesped.buscarHuesped(new HuespedDTOBuilder()
                    .setNombre(h.getNombre())
                    .setApellido(h.getApellido())
                    .setTipoDoc(h.getTipoDoc())
                    .setNroDoc(h.getNroDoc())
                    .createHuespedDTO()).getFirst();
                } catch (FracasoOperacion e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toCollection(ArrayList::new));

            EstadiaDTO eDTO = EstadiaDTO.builder()
                    .fechaInicio(estadiaDTO.getFechaInicio())
                    .fechaFin(estadiaDTO.getFechaFin())
                    .huespedes(huespedDTO)
                    .habitacion(habitacionDTO).build();

            System.out.print(eDTO.toString());
            gestorReserva.checkIn(eDTO);

            return ResponseEntity.ok(eDTO);

        } catch (FracasoOperacion e) {
            throw new RuntimeException(e);
        }
    }

    @Getter
    @Setter
    private static class RequestReservaDTO {
        String nroHabitacion;
        LocalDate fechaInicio;
        LocalDate fechaFin;
        String nombre;
        String apellido;
        String telefono;

    }

    @Getter
    @Setter
    private static class RequestEstadiaDTO {
        String nroHabitacion;
        LocalDate fechaInicio;
        LocalDate fechaFin;
        ArrayList<BuscarHuespedDTO> huespedes;
    }

    @Getter
    @Setter
    private static class ReservaBusquedaDTO {
        String apellido;
        String nombre;
    }
}

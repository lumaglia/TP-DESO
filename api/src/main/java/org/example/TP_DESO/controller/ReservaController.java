package org.example.TP_DESO.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.dto.*;
import org.example.TP_DESO.exceptions.DocumentoYaExistente;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.service.GestorHabitacion;
import org.example.TP_DESO.service.GestorHuesped;
import org.example.TP_DESO.service.GestorReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.stream.Collectors;

@RestController
public class ReservaController {
    GestorReserva gestorReserva;
    GestorHabitacion gestorHabitacion;

    @Autowired
    public ReservaController(GestorReserva gestorReserva, GestorHabitacion gestorHabitacion) {
        this.gestorReserva = gestorReserva;
        this.gestorHabitacion = gestorHabitacion;
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
    @PostMapping("/Habitacion/Reservar/")
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
    @PostMapping("/Habitacion/Ocupar/")
    public ResponseEntity<EstadiaDTO> hacerReserva(@RequestBody EstadiaDTO estadiaDTO) {

        return ResponseEntity.ok(estadiaDTO);

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
}

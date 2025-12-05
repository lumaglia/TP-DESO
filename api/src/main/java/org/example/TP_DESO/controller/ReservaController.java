package org.example.TP_DESO.controller;

import org.example.TP_DESO.dto.BuscarHuespedDTO;
import org.example.TP_DESO.dto.HuespedDTO;
import org.example.TP_DESO.dto.HuespedDTOBuilder;
import org.example.TP_DESO.dto.ReservasEstadiasPorHabitacionDTO;
import org.example.TP_DESO.exceptions.DocumentoYaExistente;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.service.GestorHuesped;
import org.example.TP_DESO.service.GestorReserva;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
public class ReservaController {
    GestorReserva gestorReserva;

    @Autowired
    public ReservaController(GestorReserva gestorReserva) {
        this.gestorReserva = gestorReserva;
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
}

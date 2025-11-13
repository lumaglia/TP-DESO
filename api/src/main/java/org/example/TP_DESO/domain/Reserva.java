package org.example.TP_DESO.domain;

import java.time.LocalDate;

public class Reserva {
    private LocalDate fechaReserva;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String apellido;
    private String nombre;
    private String telefono;
    private boolean cancelada;
    private Habitacion habitacion;
    private Huesped huesped;
    private Estadia estadia;
}

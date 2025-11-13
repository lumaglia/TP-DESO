package org.example.TP_DESO.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Estadia {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private ArrayList<Huesped> huespedes;
    private Reserva reserva;
    private ArrayList<Factura> facturas;
}

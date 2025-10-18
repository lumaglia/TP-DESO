package domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

public class Estadia {
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private ArrayList<Huesped> huespedes;
    private Reserva reserva;
    private ArrayList<Factura> facturas;
}

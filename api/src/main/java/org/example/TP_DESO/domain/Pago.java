package org.example.TP_DESO.domain;

import java.time.LocalDate;
import java.util.Dictionary;

public class Pago {
    private LocalDate fechaCobro;
    private float montoTotal;
    private Dictionary<MetodoPago, Pago> pagosParciales;
    private Factura factura;
}

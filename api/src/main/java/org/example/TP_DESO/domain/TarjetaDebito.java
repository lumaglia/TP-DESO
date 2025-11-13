package org.example.TP_DESO.domain;

import java.time.LocalDate;

public class TarjetaDebito extends MetodoPago {
    private String nroTarjeta;
    private String codigo;
    private LocalDate fechaVen;
    private String titular;

    public void registrarPago(float importe) {
        System.out.println("Pago con tarjeta de debito registrado");
    }
}

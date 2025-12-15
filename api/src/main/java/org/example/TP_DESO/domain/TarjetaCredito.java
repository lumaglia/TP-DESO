package org.example.TP_DESO.domain;

import java.time.LocalDate;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class TarjetaCredito extends MetodoPago {
    private String nroTarjeta;
    private String codigo;
    private LocalDate fechaVen;
    private String titular;
    private int cuotas;

    public void registrarPago(float importe) {
        System.out.println("Pago con tarjeta de credito registrado");
    }
}

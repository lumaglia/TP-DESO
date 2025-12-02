package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class Efectivo extends MetodoPago {

    private String divisa;
    private double cotizacion;

    public void registrarPago(float importe) {
        System.out.println("Efectivo procesado");
    }

}

package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Dictionary;

@Entity
@Table(name = "pago")
@Getter
@Setter

public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate fechaCobro;
    private float montoTotal;

    private Dictionary<MetodoPago, Pago> pagosParciales;

    @OneToOne(optional = false)
    @JoinColumn(name = "factura_id", unique = true, nullable = false)
    private Factura factura;
}

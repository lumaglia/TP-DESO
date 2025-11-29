package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "pagoParcial")
@Getter
@Setter

public class PagoParcial {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pago_id", nullable = false)
    private Pago pago;

    @ManyToOne(optional = false)
    @JoinColumn(name = "metodo_pago_id", nullable = false)
    private MetodoPago metodoPago;

    private float monto;
    private LocalDate fechaPago;
}

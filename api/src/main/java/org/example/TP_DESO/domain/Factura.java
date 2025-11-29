package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "factura")
@Getter
@Setter

public class Factura {

    @Id
    private String nroFactura;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "estadia_id",
            nullable = false
    )
    private Estadia estadia;

    @ManyToOne(optional = true)
    @JoinColumn(
            name = "nota_credito_id",
            nullable = true
    )
    private NotaCredito notaCredito;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "responsable_pago_id",
            nullable = false
    )
    private ResponsablePago responsablePago;

    @OneToOne(mappedBy = "factura", optional = false)
    private Pago pago;
}

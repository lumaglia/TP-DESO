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
    private float precio;
    private boolean pagaEstadia;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "estadia_id", referencedColumnName = "idEstadia",
            nullable = false
    )
    private Estadia estadia;

    @ManyToOne(optional = true)
    @JoinColumn(
            name = "nota_credito_id", referencedColumnName = "nroNotaCredito",
            nullable = true
    )
    private NotaCredito notaCredito;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "responsable_pago_id", referencedColumnName = "Id",
            nullable = false
    )
    private ResponsablePago responsablePago;

    @OneToOne(mappedBy = "factura", cascade = CascadeType.ALL)
    private Pago pago;
}

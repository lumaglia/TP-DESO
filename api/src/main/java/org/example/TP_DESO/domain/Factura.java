package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "factura")
@Getter
@Setter

public class Factura {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String nroFactura;
    private float precio;
    private boolean pagaEstadia;

    @ManyToOne(optional = false, cascade = CascadeType.PERSIST)
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

    @ManyToOne
    @JoinColumn(
            name = "responsable_pago_id", referencedColumnName = "Id")
    private ResponsablePago responsablePago;
}

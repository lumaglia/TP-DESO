package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.transaction.annotation.Transactional;

@Entity
@Table(name = "factura")
@Getter
@Setter
@Transactional
public class Factura {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String nroFactura;
    private float precio;
    private boolean pagaEstadia;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(
            name = "estadia_id", referencedColumnName = "idEstadia"
    )
    private Estadia estadia;

    @ManyToOne
    @JoinColumn(
            name = "nota_credito_id", referencedColumnName = "nroNotaCredito"
    )
    private NotaCredito notaCredito;

    @ManyToOne
    @JoinColumn(
            name = "responsable_pago_id", referencedColumnName = "Id")
    private ResponsablePago responsablePago;
}

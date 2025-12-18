package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "consumo")
@Getter
@Setter

public class Consumo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private float monto;
    public enum TipoConsumo{
        LAVADOPLANCHADO,
        SAUNA,
        BAR
    }
    private TipoConsumo tipo;
    private String detalle;

    @ManyToOne(optional = false)
    @JoinColumn(
            name = "id_estadia",referencedColumnName = "idEstadia",
            nullable = false)
    private Estadia estadia;

    @ManyToOne
    @JoinColumn(
            name = "factura_id", referencedColumnName = "nroFactura"
    )
    private Factura factura;
}

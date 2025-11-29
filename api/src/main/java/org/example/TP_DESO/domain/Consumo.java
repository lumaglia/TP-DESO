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

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(
            name = "estadia_id",
            nullable = false)
    private Estadia estadia;
}

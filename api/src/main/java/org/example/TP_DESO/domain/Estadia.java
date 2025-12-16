package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estadia")
@Getter
@Setter

public class Estadia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idEstadia;

    private LocalDate fechaInicio;
    private LocalDate fechaFin;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "estadia_huesped",
            joinColumns = @JoinColumn(name = "id_estadia", referencedColumnName = "idEstadia"),
            inverseJoinColumns = {
                    @JoinColumn(name = "tipo_doc", referencedColumnName = "tipoDoc"),
                    @JoinColumn(name = "nro_doc",  referencedColumnName = "nroDoc")
            }
    )
    private List<Huesped> huespedes = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "id_habitacion", referencedColumnName = "nroHabitacion")
    private Habitacion habitacion;

    @OneToMany
    @JoinColumn(name = "id_estadia")
    private List<Consumo> consumos = new ArrayList<>();
}

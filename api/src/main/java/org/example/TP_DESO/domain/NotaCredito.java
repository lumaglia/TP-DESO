package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Table(name = "notaCredito")
@Getter
@Setter

public class NotaCredito {

    @Id
    private String nroNotaCredito;

    private float importeNeto;
    private float iva;
    private float importeTotal;

}

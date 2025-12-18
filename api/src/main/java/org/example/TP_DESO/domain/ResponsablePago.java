package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;

@Getter
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class ResponsablePago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

}

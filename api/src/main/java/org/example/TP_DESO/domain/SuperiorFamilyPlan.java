package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class SuperiorFamilyPlan extends Habitacion {
    private int camasInd;
    private int camasDob;
}

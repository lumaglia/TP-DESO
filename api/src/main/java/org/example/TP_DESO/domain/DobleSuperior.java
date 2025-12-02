package org.example.TP_DESO.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter

public class DobleSuperior extends Habitacion {
    private int camasKingInd;
    private int camasKingDob;
}

package org.example.TP_DESO.dto.CU07;

import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.domain.Huesped;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Getter
@Setter
public class HuespedCheckoutDTO {
    private String nombre;
    private String apellido;
    private String cuil;
    private boolean menorEdad;

    public HuespedCheckoutDTO(Huesped huesped) {
        this.nombre = huesped.getNombre();
        this.apellido = huesped.getApellido();
        this.cuil = huesped.getCuil();
        this.menorEdad = Period.between(huesped.getFechaNac(), LocalDate.now()).getYears() >= 18;
    }
}

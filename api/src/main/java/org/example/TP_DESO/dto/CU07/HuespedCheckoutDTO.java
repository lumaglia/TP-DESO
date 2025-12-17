package org.example.TP_DESO.dto.CU07;

import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.dto.HuespedDTO;

import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Getter
@Setter
public class HuespedCheckoutDTO {
    private String nombre;
    private String apellido;
    private String cuil;
    private String tipoDoc;
    private String nroDoc;
    private boolean menorEdad;

    public HuespedCheckoutDTO(Huesped huesped) {
        this.nombre = huesped.getNombre();
        this.apellido = huesped.getApellido();
        this.cuil = huesped.getCuil();
        this.menorEdad = Period.between(huesped.getFechaNac(), LocalDate.now()).getYears() < 18;
    }

    public HuespedCheckoutDTO(HuespedDTO huesped) {
        this.nombre = huesped.getNombre();
        this.apellido = huesped.getApellido();
        this.menorEdad = Period.between(huesped.getFechaNac(), LocalDate.now()).getYears() < 18;
        this.tipoDoc = huesped.getTipoDoc();
        this.nroDoc = huesped.getNroDoc();
        this.cuil = huesped.getCuil();
    }
}

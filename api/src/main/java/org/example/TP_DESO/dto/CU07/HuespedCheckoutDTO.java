package org.example.TP_DESO.dto.CU07;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class HuespedCheckoutDTO {
    private String nombre;
    private String apellido;
    private String cuil;

    public HuespedCheckoutDTO(String nombre, String apellido, String cuit){
        this.nombre = nombre;
        this.apellido = apellido;
        this.cuil = cuil;
    }
}

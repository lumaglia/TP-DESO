package org.example.TP_DESO.dto.CU07;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Getter
@Setter
public class EmitirFacturaDTO {
    private boolean pagaEstadia;
    private ArrayList<String> consumos;
    private String numHabitacion;
    private String diaCheckOut;
    private boolean esHuesped;
    private String cuit;
    private String tipoDoc;
    private String nroDoc;


}

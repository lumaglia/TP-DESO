package org.example.TP_DESO.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseReservaDTO {
    String nroHabitacion;
    LocalDate fechaInicio;
    LocalDate fechaFin;
    String nombre;
    String apellido;
    String tipoHabitacion;

    public ResponseReservaDTO(ReservaDTO reservaDTO) {
        nroHabitacion = reservaDTO.getHabitacion().getNroHabitacion();
        fechaInicio = reservaDTO.getFechaInicio();
        fechaFin = reservaDTO.getFechaFin();
        nombre = reservaDTO.getNombre();
        apellido = reservaDTO.getApellido();
        HabitacionDTO h = reservaDTO.getHabitacion();
        if(h instanceof SuiteDobleDTO){
            tipoHabitacion = "Suite Doble";
        }
        else if(h instanceof DobleEstandarDTO){
            tipoHabitacion = "Doble Estandar";
        }
        else if(h instanceof IndividualEstandarDTO){
            tipoHabitacion = "Individual Estandar";
        }
        else if(h instanceof SuperiorFamilyPlanDTO){
            tipoHabitacion = "Superior Family Plan";
        }else if(h instanceof DobleSuperiorDTO){
            tipoHabitacion = "Doble Superior";
        }
    }
}

package org.example.TP_DESO.dto.Mappers;

import org.example.TP_DESO.domain.*;
import org.example.TP_DESO.dto.*;

public class HabitacionMapper {

    public static HabitacionDTO toDTO(Habitacion h) {

        if (h instanceof DobleEstandar de) {
            return new DobleEstandarDTO(
                    de.getNroHabitacion(),
                    de.getPrecioNoche(),
                    de.getCapacidad(),
                    de.getTamanno(),
                    de.getCamasInd(),
                    de.getCamasDob()
            );
        }
        if (h instanceof DobleSuperior ds) {
            return new DobleEstandarDTO(
                    ds.getNroHabitacion(),
                    ds.getPrecioNoche(),
                    ds.getCapacidad(),
                    ds.getTamanno(),
                    ds.getCamasKingInd(),
                    ds.getCamasKingDob()
            );
        }
        if (h instanceof IndividualEstandar ie) {
            return new IndividualEstandarDTO(
                    ie.getNroHabitacion(),
                    ie.getPrecioNoche(),
                    ie.getCapacidad(),
                    ie.getTamanno(),
                    ie.getCamasInd()
            );
        }
        if (h instanceof SuiteDoble sd) {
            return new SuiteDobleDTO(
                    sd.getNroHabitacion(),
                    sd.getPrecioNoche(),
                    sd.getCapacidad(),
                    sd.getTamanno(),
                    sd.getCamasKingDob()
            );
        }
        if (h instanceof SuperiorFamilyPlan sfp){
            return new SuperiorFamilyPlanDTO(
                    sfp.getNroHabitacion(),
                    sfp.getPrecioNoche(),
                    sfp.getCapacidad(),
                    sfp.getTamanno(),
                    sfp.getCamasInd(),
                    sfp.getCamasDob()
            );
        }

        throw new IllegalArgumentException("Tipo de habitación desconocido: " + h.getClass());
    }
}
package org.example.TP_DESO.patterns.mappers;

import org.example.TP_DESO.domain.*;
import org.example.TP_DESO.dto.*;
import org.example.TP_DESO.patterns.factory.HabitacionFactory;
import org.example.TP_DESO.patterns.factory.HabitacionType;

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
            return new DobleSuperiorDTO(
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

    public static Habitacion toDomain(HabitacionDTO h) {

        if (h instanceof DobleEstandarDTO de) {
            DobleEstandar d = (DobleEstandar) HabitacionFactory.create(HabitacionType.DOBLE_ESTANDAR);

            d.setCapacidad(de.getCapacidad());
            d.setPrecioNoche(de.getPrecioNoche());
            d.setTamanno(de.getTamanno());
            d.setCamasInd(de.getCamasInd());
            d.setCamasDob(de.getCamasDob());
            d.setNroHabitacion(h.getNroHabitacion());

            return d;
        }
        if (h instanceof DobleSuperiorDTO ds) {
            DobleSuperior d = (DobleSuperior) HabitacionFactory.create(HabitacionType.DOBLE_SUPERIOR);

            d.setCapacidad(ds.getCapacidad());
            d.setPrecioNoche(ds.getPrecioNoche());
            d.setTamanno(ds.getTamanno());
            d.setCamasKingInd(ds.getCamasKingInd());
            d.setCamasKingDob(ds.getCamasKingDob());
            d.setNroHabitacion(h.getNroHabitacion());

            return d;
        }
        if (h instanceof IndividualEstandarDTO ie) {
            IndividualEstandar i = (IndividualEstandar) HabitacionFactory.create(HabitacionType.INDIVIDUAL_ESTANDAR);

            i.setCapacidad(ie.getCapacidad());
            i.setPrecioNoche(ie.getPrecioNoche());
            i.setTamanno(ie.getTamanno());
            i.setCamasInd(ie.getCamasInd());
            i.setNroHabitacion(ie.getNroHabitacion());

            return i;
        }
        if (h instanceof SuiteDobleDTO sd) {
            SuiteDoble s = (SuiteDoble) HabitacionFactory.create(HabitacionType.SUITE_DOBLE);

            s.setCapacidad(sd.getCapacidad());
            s.setPrecioNoche(sd.getPrecioNoche());
            s.setTamanno(sd.getTamanno());
            s.setCamasKingDob(sd.getCamasKingDob());
            s.setNroHabitacion(sd.getNroHabitacion());

            return s;
        }
        if (h instanceof SuperiorFamilyPlanDTO sfp){
            SuperiorFamilyPlan s = (SuperiorFamilyPlan) HabitacionFactory.create(HabitacionType.SUPERIOR_FAMILY_PLAN);

            s.setCapacidad(sfp.getCapacidad());
            s.setPrecioNoche(sfp.getPrecioNoche());
            s.setTamanno(sfp.getTamanno());
            s.setCamasInd(sfp.getCamasInd());
            s.setCamasDob(sfp.getCamasDob());
            s.setNroHabitacion(sfp.getNroHabitacion());

            return s;
        }

        throw new IllegalArgumentException("Tipo de habitación desconocido: " + h.getClass());
    }
}
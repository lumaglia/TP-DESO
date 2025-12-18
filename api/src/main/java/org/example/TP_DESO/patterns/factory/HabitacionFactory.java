package org.example.TP_DESO.patterns.factory;

import org.example.TP_DESO.domain.*;

public class HabitacionFactory {
    private void HabitacionFactory() {

    }

    public static Habitacion create(HabitacionType type) {
        if (type == null) {
            throw new IllegalArgumentException("HabitacionType no puede ser null");
        }

        return switch (type) {
            case DOBLE_ESTANDAR -> new DobleEstandar();
            case DOBLE_SUPERIOR -> new DobleSuperior();
            case INDIVIDUAL_ESTANDAR -> new IndividualEstandar();
            case SUITE_DOBLE -> new SuiteDoble();
            case SUPERIOR_FAMILY_PLAN -> new SuperiorFamilyPlan();
        };
    }
}

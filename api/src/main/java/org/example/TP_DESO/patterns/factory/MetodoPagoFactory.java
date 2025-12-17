package org.example.TP_DESO.patterns.factory;

import org.example.TP_DESO.domain.*;

public final class MetodoPagoFactory {

    private MetodoPagoFactory() {

    }

    public static MetodoPago create(MetodoPagoType type) {
        if (type == null) {
            throw new IllegalArgumentException("MetodoPagoType no puede ser null");
        }

        return switch (type) {
            case EFECTIVO -> new Efectivo();
            case TARJETA_CREDITO -> new TarjetaCredito();
            case TARJETA_DEBITO -> new TarjetaDebito();
            case CHEQUE -> new Cheque();
        };
    }
}
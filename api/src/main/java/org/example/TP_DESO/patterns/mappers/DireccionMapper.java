package org.example.TP_DESO.patterns.mappers;

import org.example.TP_DESO.domain.Direccion;
import org.example.TP_DESO.dto.DireccionDTO;

public class DireccionMapper {
    public static DireccionDTO toDTO(Direccion d) {
        if(d == null) return null;
        return new DireccionDTO(d.getDomicilio(), d.getDepto(), d.getCodigoPostal(), d.getLocalidad(), d.getProvincia(), d.getPais());
    }

    public static Direccion toDomain(DireccionDTO d) {
        if(d == null){
            return null;
        }
        return new Direccion(d.getDomicilio(), d.getDepto(), d.getCodigoPostal(), d.getLocalidad(), d.getProvincia(), d.getPais());
    }
}

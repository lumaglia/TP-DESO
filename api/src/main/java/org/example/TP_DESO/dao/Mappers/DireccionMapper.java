package org.example.TP_DESO.dao.Mappers;

import org.example.TP_DESO.domain.Direccion;
import org.example.TP_DESO.dto.DireccionDTO;

public class DireccionMapper {
    public static DireccionDTO toDTO(Direccion d) {
        if(d == null) return null;
        return new DireccionDTO(d.getDomicilio(), d.getDepto(), d.getCodigoPostal(), d.getLocalidad(), d.getProvincia(), d.getPais());
    }
}

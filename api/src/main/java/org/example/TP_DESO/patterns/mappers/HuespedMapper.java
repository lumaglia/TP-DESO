package org.example.TP_DESO.patterns.mappers;

import org.example.TP_DESO.domain.Direccion;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.dto.HuespedDTO;

public class HuespedMapper {
    static public HuespedDTO toDTO(Huesped h) {
        if (h == null)return null;
        DireccionDTO direccionDTO = DireccionMapper.toDTO(h.getDireccion());
        return new HuespedDTO(h.getTipoDoc(),
                h.getNroDoc(),
                h.getApellido(),
                h.getNombre(),
                h.getCuil(),
                h.getPosicionIva(),
                h.getFechaNac(),
                h.getTelefono(),
                h.getEmail(),
                h.getOcupacion(),
                h.getNacionalidad(),
                direccionDTO);
    }

    static public Huesped toDomain(HuespedDTO h){
        if(h == null){
            return null;
        }
        Direccion direccion = DireccionMapper.toDomain(h.getDireccion());

        return new Huesped(
                h.getNombre(),
                h.getApellido(),
                h.getTipoDoc(),
                h.getNroDoc(),
                h.getCuil(),
                h.getPosicionIva(),
                h.getFechaNac(),
                h.getTelefono(),
                h.getEmail(),
                h.getOcupacion(),
                h.getNacionalidad(),
                direccion
        );
    }
}

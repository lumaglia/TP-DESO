package org.example.TP_DESO.dto.Mappers;

import org.example.TP_DESO.domain.Estadia;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.dto.EstadiaDTO;
import org.example.TP_DESO.dto.HabitacionDTO;
import org.example.TP_DESO.dto.HuespedDTO;

import java.util.ArrayList;

public class EstadiaMapper {
     public static EstadiaDTO toDTO(Estadia e) {
        if (e == null) return null;

         ArrayList<HuespedDTO> huespedDTO = new ArrayList<>();

         for(Huesped h : e.getHuespedes()) {
             huespedDTO.add(HuespedMapper.toDTO(h));
         }

         HabitacionDTO habitacionDTO = HabitacionMapper.toDTO(e.getHabitacion());

         return new EstadiaDTO(
                 e.getFechaInicio(),
                 e.getFechaFin(),
                 huespedDTO,
                 habitacionDTO);
     }
}

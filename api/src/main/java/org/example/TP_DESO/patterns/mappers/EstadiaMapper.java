package org.example.TP_DESO.patterns.mappers;

import org.example.TP_DESO.domain.Estadia;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.dto.EstadiaDTO;
import org.example.TP_DESO.dto.HabitacionDTO;
import org.example.TP_DESO.dto.HuespedDTO;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class EstadiaMapper {
     public static EstadiaDTO toDTO(Estadia e) {
        if (e == null) return null;

         ArrayList<HuespedDTO> huespedDTO = new ArrayList<>();

         for(Huesped h : e.getHuespedes()) {
             huespedDTO.add(HuespedMapper.toDTO(h));
         }

         HabitacionDTO habitacionDTO = HabitacionMapper.toDTO(e.getHabitacion());

         return new EstadiaDTO(
                 e.getIdEstadia(),
                 e.getFechaInicio(),
                 e.getFechaFin(),
                 huespedDTO,
                 habitacionDTO);
     }

     /*public static Estadia toDomain(EstadiaDTO dto) {
         if (dto == null){
             return null;
         }
         Estadia estadia = new Estadia();
         Stream<HuespedDTO> huespedDTO = dto.getHuespedes().stream();
         ArrayList<Huesped> huespedes = (ArrayList<Huesped>) huespedDTO.map(p->HuespedMapper.toDomain(p));

         estadia.setFechaInicio(dto.getFechaInicio());
         estadia.setFechaFin(dto.getFechaFin());
         estadia.setIdEstadia(dto.getId());
         estadia.setHuespedes(huespedes);
         estadia.setHabitacion(HabitacionMapper.toDomain(dto.getHabitacion()));

         return  estadia;
     }*/
     public static Estadia toDomain(EstadiaDTO dto) {
         if (dto == null) {
             return null;
         }

         Estadia estadia = new Estadia();
         ArrayList<Huesped> huespedes = dto.getHuespedes().stream()
                                            .map(HuespedMapper::toDomain)
                                            .collect(Collectors.toCollection(ArrayList::new));

         estadia.setFechaInicio(dto.getFechaInicio());
         estadia.setFechaFin(dto.getFechaFin());
         estadia.setIdEstadia(dto.getIdEstadia());
         estadia.setHuespedes(huespedes);
         estadia.setHabitacion(HabitacionMapper.toDomain(dto.getHabitacion()));

         return estadia;
     }

}

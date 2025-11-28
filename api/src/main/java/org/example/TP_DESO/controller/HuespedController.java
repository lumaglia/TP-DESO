package org.example.TP_DESO.controller;

import org.example.TP_DESO.dto.BuscarHuespedDTO;
import org.example.TP_DESO.dto.HuespedDTO;
import org.example.TP_DESO.dto.HuespedDTOBuilder;
import org.example.TP_DESO.exceptions.DocumentoYaExistente;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.service.GestorHuesped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
public class HuespedController {

    GestorHuesped gestorHuesped;

    @Autowired
    public HuespedController(GestorHuesped gestorHuesped) {
        this.gestorHuesped = gestorHuesped;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Huesped/Alta")
    public ResponseEntity<HuespedDTO> altaHuesped(@RequestBody HuespedDTO huespedDTO, @RequestParam(required = false, defaultValue = "false") boolean modify) {
        if(!modify) {
            try {
                gestorHuesped.altaHuesped(huespedDTO);
            } catch (DocumentoYaExistente e) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } catch (FracasoOperacion e) {
                throw new RuntimeException(e);
            }
        }else{
            try {
                gestorHuesped.bajaHuesped(huespedDTO);
                gestorHuesped.altaHuesped(huespedDTO);
            } catch (DocumentoYaExistente e) {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            } catch (FracasoOperacion e) {
                throw new RuntimeException(e);
            }
        }

        return ResponseEntity.ok(huespedDTO);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Huesped/Buscar")
    public ResponseEntity<ArrayList<BuscarHuespedDTO>> buscarHuesped(@RequestBody BuscarHuespedDTO filtro) {
        HuespedDTO huespedDTO = new HuespedDTOBuilder()
                .setNombre(filtro.getNombre())
                .setApellido(filtro.getApellido())
                .setTipoDoc(filtro.getTipoDoc())
                .setNroDoc(filtro.getNroDoc())
                .createHuespedDTO();
        try {
            ArrayList<BuscarHuespedDTO> huespedes = gestorHuesped.buscarHuesped(huespedDTO)
                    .stream().map(BuscarHuespedDTO::new).collect(Collectors.toCollection(ArrayList::new));
            if(huespedes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return ResponseEntity.ok(huespedes);
            }
        } catch (FracasoOperacion e) {
            throw new RuntimeException(e);
        }

    }

}

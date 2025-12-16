package org.example.TP_DESO.controller;

import lombok.Getter;
import lombok.Setter;
import org.example.TP_DESO.dto.BuscarHuespedDTO;
import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.dto.HuespedDTO;
import org.example.TP_DESO.dto.HuespedDTOBuilder;
import org.example.TP_DESO.exceptions.DocumentoYaExistente;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.service.GestorHuesped;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HuespedController {

    GestorHuesped gestorHuesped;

    @Autowired
    public HuespedController(GestorHuesped gestorHuesped) {
        this.gestorHuesped = gestorHuesped;
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Huesped/Alta")
    public ResponseEntity<HuespedDTO> altaHuesped(@RequestBody HuespedDTO huespedDTO) {
        try {
            gestorHuesped.altaHuesped(huespedDTO);
        } catch (DocumentoYaExistente e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } catch (FracasoOperacion e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok(huespedDTO);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/Huesped/Modificar")
    public ResponseEntity<ModificarHuespedDTO> modificarHuesped(@RequestBody ModificarHuespedDTO modificarHuespedDTO) {

        try {
            HuespedDTO huespedDTO = new HuespedDTOBuilder()
                    .setNombre(modificarHuespedDTO.getNombre())
                    .setApellido(modificarHuespedDTO.getApellido())
                    .setTipoDoc(modificarHuespedDTO.getTipoDoc())
                    .setNroDoc(modificarHuespedDTO.getNroDoc())
                    .setCuil(modificarHuespedDTO.getCuil())
                    .setPosicionIva(modificarHuespedDTO.getPosicionIva())
                    .setFechaNac(modificarHuespedDTO.getFechaNac())
                    .setTelefono(modificarHuespedDTO.getTelefono())
                    .setEmail(modificarHuespedDTO.getEmail())
                    .setOcupacion(modificarHuespedDTO.getOcupacion())
                    .setNacionalidad(modificarHuespedDTO.getNacionalidad())
                    .setDireccion(modificarHuespedDTO.getDireccion())
                    .createHuespedDTO();
            gestorHuesped.modificarHuesped(modificarHuespedDTO.tipoDocViejo,modificarHuespedDTO.nroDocViejo,huespedDTO, false);
        } catch (FracasoOperacion e) {
            throw new RuntimeException(e);
        } catch (DocumentoYaExistente e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(modificarHuespedDTO);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/Huesped/Modificar/Override")
    public ResponseEntity<ModificarHuespedDTO> modificarHuespedOverride(@RequestBody ModificarHuespedDTO modificarHuespedDTO) {

        try {
            HuespedDTO huespedDTO = new HuespedDTOBuilder()
                    .setNombre(modificarHuespedDTO.getNombre())
                    .setApellido(modificarHuespedDTO.getApellido())
                    .setTipoDoc(modificarHuespedDTO.getTipoDoc())
                    .setNroDoc(modificarHuespedDTO.getNroDoc())
                    .setCuil(modificarHuespedDTO.getCuil())
                    .setPosicionIva(modificarHuespedDTO.getPosicionIva())
                    .setFechaNac(modificarHuespedDTO.getFechaNac())
                    .setTelefono(modificarHuespedDTO.getTelefono())
                    .setEmail(modificarHuespedDTO.getEmail())
                    .setOcupacion(modificarHuespedDTO.getOcupacion())
                    .setNacionalidad(modificarHuespedDTO.getNacionalidad())
                    .setDireccion(modificarHuespedDTO.getDireccion())
                    .createHuespedDTO();
            gestorHuesped.modificarHuesped(modificarHuespedDTO.tipoDocViejo,modificarHuespedDTO.nroDocViejo,huespedDTO, true);
        } catch (FracasoOperacion e) {
            throw new RuntimeException(e);
        } catch (DocumentoYaExistente e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        return ResponseEntity.ok(modificarHuespedDTO);
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


    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Huesped/Obtener")
    public ResponseEntity<HuespedDTO> obtenerHuespedCompleto(@RequestBody BuscarHuespedDTO filtro) {
        HuespedDTO huespedFilter = new HuespedDTOBuilder()
                .setTipoDoc(filtro.getTipoDoc())
                .setNroDoc(filtro.getNroDoc())
                .createHuespedDTO();

        try {
            var resultados = gestorHuesped.buscarHuesped(huespedFilter);

            if(resultados.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return ResponseEntity.ok(resultados.get(0));
            }
        } catch (FracasoOperacion e) {
            throw new RuntimeException(e);
        }
    }


    @CrossOrigin(origins = "http://localhost:3000")
    @DeleteMapping("/Huesped/Baja")
    public ResponseEntity<?> eliminarHuesped(@RequestBody HuespedDTO huespedDTO) {
        try {
            gestorHuesped.bajaHuesped(huespedDTO);
            return ResponseEntity.ok().build();
        } catch (FracasoOperacion e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar");
        }
    }

    @Getter
    @Setter
    private static class ModificarHuespedDTO{
        String tipoDocViejo;
        String nroDocViejo;
        String nombre;
        String apellido;
        String tipoDoc;
        String nroDoc;
        String cuil;
        String posicionIva;
        LocalDate fechaNac;
        String telefono;
        String email;
        String ocupacion;
        String nacionalidad;
        DireccionDTO direccion;
    }
}


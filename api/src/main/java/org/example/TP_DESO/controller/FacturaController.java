package org.example.TP_DESO.controller;

import org.example.TP_DESO.domain.Factura;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.domain.ResponsablePago;
import org.example.TP_DESO.dto.CU07.HuespedCheckoutDTO;
import org.example.TP_DESO.dto.CU07.RequestCheckoutDTO;
import org.example.TP_DESO.dto.CU12.ResponsablePagoDTO;
import org.example.TP_DESO.dto.FacturaDTO;
import org.example.TP_DESO.exceptions.DocumentoYaExistente;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.service.GestorFactura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FacturaController {
    GestorFactura gestorFactura;

    @Autowired
    public FacturaController(GestorFactura gestorFactura) {this.gestorFactura = gestorFactura;}

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Factura/Crear")
    public ResponseEntity<List<HuespedCheckoutDTO>> obtenerHuespedesCheckot(
            @RequestBody RequestCheckoutDTO request) throws FracasoOperacion{
        try{
            List<Huesped> huespedes = gestorFactura.buscarHuespedes(request.getNumHabitacion());

            List<HuespedCheckoutDTO> resultado = huespedes.stream()
                    .map(h -> new HuespedCheckoutDTO(
                            h.getNombre(),
                            h.getApellido(),
                            h.getCuil()
                    ))
                    .toList();

            return ResponseEntity.ok().body(resultado);
        } catch (Exception e) {
            throw new FracasoOperacion("Error : " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/ResponsablePago/Alta")
    public ResponseEntity<ResponsablePagoDTO> altaResponsablePago(
            @RequestBody ResponsablePagoDTO responsablePagoDTO) throws FracasoOperacion{
        try{
            gestorFactura.altaResponsablePago(responsablePagoDTO);
            return ResponseEntity.ok(responsablePagoDTO);
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}

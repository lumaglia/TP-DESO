package org.example.TP_DESO.controller;

import org.example.TP_DESO.domain.Factura;
import org.example.TP_DESO.dto.FacturaDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.service.GestorFactura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FacturaController {
    GestorFactura gestorFactura;

    @Autowired
    public FacturaController(GestorFactura gestorFactura) {this.gestorFactura = gestorFactura;}

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Factura/Alta")
    public ResponseEntity<FacturaDTO> altaFactura(@RequestBody FacturaDTO facturaDTO) throws FracasoOperacion {
        try{
            gestorFactura.generarFactura(facturaDTO);
        }
        catch(Exception e){
            throw new FracasoOperacion(e.getMessage());
        }
        return ResponseEntity.ok(facturaDTO);
    }
}

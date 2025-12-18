package org.example.TP_DESO.controller;

import org.example.TP_DESO.dto.CU07.RequestCheckoutDTO;
import org.example.TP_DESO.dto.CU12.PersonaJuridicaDTO;
import org.example.TP_DESO.dto.CU07.EstadiaFacturacionDTO;
import org.example.TP_DESO.dto.CU12.ResponsablePagoDTO;
import org.example.TP_DESO.dto.FacturaDTO;
import org.example.TP_DESO.exceptions.DocumentoYaExistente;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.service.GestorFactura;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class FacturaController {
    GestorFactura gestorFactura;

    @Autowired
    public FacturaController(GestorFactura gestorFactura) {this.gestorFactura = gestorFactura;}

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Factura/Checkout")
    public ResponseEntity<EstadiaFacturacionDTO> obtenerHuespedesCheckot(
            @RequestBody RequestCheckoutDTO request) throws FracasoOperacion {
        System.out.println(request.getDiaCheckOut());
        try{
            EstadiaFacturacionDTO resultado = gestorFactura.estadiaFacturacion(request);
            return ResponseEntity.ok().body(resultado);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Estadía no encontrada con habitacion y fecha fin:")){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            throw new FracasoOperacion("Error al obtener los huespedes que hacen checkout: " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Factura/Emitir")
    public ResponseEntity<FacturaDTO> crearFactura(@RequestBody FacturaDTO facturaDTO) throws DocumentoYaExistente, FracasoOperacion{
        try{
            gestorFactura.generarFactura(facturaDTO);
            return ResponseEntity.ok(facturaDTO);
        }
        catch (Exception e){
            throw new DocumentoYaExistente("Error al crear la factura: " + e.getMessage());
        }

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/ResponsablePago/Alta")
    public ResponseEntity<PersonaJuridicaDTO> altaResponsablePago(
            @RequestBody PersonaJuridicaDTO personaJuridicaDTO) throws FracasoOperacion{
        try{
            gestorFactura.altaPersonaJuridica(personaJuridicaDTO);
            return ResponseEntity.ok(personaJuridicaDTO);
        }
        catch (Exception e) {
            throw new FracasoOperacion("No se pudo dar de alta el responsable de pago: " + e.getMessage());
        }

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/ResponsablePago/Modificar")
    public ResponseEntity<PersonaJuridicaDTO> modificarResponsablePago(
            @RequestBody PersonaJuridicaDTO personaJuridicaDTO) throws FracasoOperacion{
        try{
            gestorFactura.modificarResponsablePago(personaJuridicaDTO);
            return ResponseEntity.ok(personaJuridicaDTO);
        }
        catch (Exception e) {
            throw new FracasoOperacion("Error al modificar el responsable de pago" + e.getMessage());
        }

    }

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/Factura/BuscarResponsablePago/{cuit}")
    public ResponseEntity<String> buscarResponsablePago(
            @PathVariable("cuit") String cuit) throws FracasoOperacion{
        try{
            ResponsablePagoDTO responsable = gestorFactura.buscarResponsablePago(cuit);
            if(responsable instanceof PersonaJuridicaDTO resultado){
                return ResponseEntity.ok(resultado.getRazonSocial());
            }
            else{
                throw new FracasoOperacion("El responsable de pago no es una persona juridica y este metodo no lo soporta");
            }
        }
        catch (Exception e) {
            throw new FracasoOperacion("Error al buscar el responsable de pago" + e.getMessage());
        }

    }
}

package org.example.TP_DESO.controller;

import org.example.TP_DESO.domain.Factura;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.domain.ResponsablePago;
import org.example.TP_DESO.dto.CU07.HuespedCheckoutDTO;
import org.example.TP_DESO.dto.CU07.ItemsFacturaDTO;
import org.example.TP_DESO.dto.CU07.RequestCheckoutDTO;
import org.example.TP_DESO.dto.CU07.RequestDeItemsDTO;
import org.example.TP_DESO.dto.CU12.ResponsablePagoDTO;
import org.example.TP_DESO.dto.EstadiaDTO;
import org.example.TP_DESO.dto.CU07.EstadiaFacturacionDTO;
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
        try{
            EstadiaFacturacionDTO resultado = gestorFactura.estadiaFacturacion(request.getNumHabitacion());
            return ResponseEntity.ok().body(resultado);
        } catch (Exception e) {
            if (e.getMessage() != null && e.getMessage().contains("Estadía no encontrada con habitacion y fecha fin:")){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            throw new FracasoOperacion("Error : " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Factura/BuscarResponsable")
    public ResponseEntity<ItemsFacturaDTO> buscarResponsablePago
            (@RequestBody RequestDeItemsDTO request) throws FracasoOperacion{
        try{
            ResponsablePagoDTO responsablePago = gestorFactura.buscarResponsablePago(request.getResponsablePago().getCuil());

            ItemsFacturaDTO items = new ItemsFacturaDTO(request.getEstadia(), responsablePago, request.getResponsablePago(), request.getConsumos());

            return ResponseEntity.ok().body(items);

        } catch (Exception e) {
            throw new FracasoOperacion("Error : " + e.getMessage());
        }
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/Factura/Emitir")
    public ResponseEntity<FacturaDTO> crearFactura(@RequestBody FacturaDTO facturaDTO) throws DocumentoYaExistente, FracasoOperacion{
        try{
            gestorFactura.generarFactura(facturaDTO);
            return ResponseEntity.ok(facturaDTO);
        }
        catch (FracasoOperacion e){
            throw new FracasoOperacion("Error: " + e.getMessage());
        }
        catch (Exception e){
            throw new DocumentoYaExistente("Error : " + e.getMessage());
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

    @CrossOrigin(origins = "http://localhost:3000")
    @PutMapping("/ResponsablePago/Modificar")
    public ResponseEntity<ResponsablePagoDTO> modificarResponsablePago(
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

package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.DireccionDAOMySQL;
import org.example.TP_DESO.dao.ResponsablePagoDAOMySQL;
import org.example.TP_DESO.dao.*;
import org.example.TP_DESO.dao.FacturaDAOMySQL;
import org.example.TP_DESO.dao.NotaCreditoDAOMySQL;
import org.example.TP_DESO.domain.*;
import org.example.TP_DESO.dto.CU07.EstadiaFacturacionDTO;
import org.example.TP_DESO.dto.CU12.ResponsablePagoDTO;
import org.example.TP_DESO.dto.ConsumoDTO;
import org.example.TP_DESO.dto.EstadiaDTO;
import org.example.TP_DESO.dto.FacturaDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.patterns.mappers.DireccionMapper;
import org.example.TP_DESO.patterns.strategy.PrecioHabitacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class GestorFactura {
    private static GestorFactura singleton_instance;

    @Autowired
    private FacturaDAOMySQL daoFactura;
    @Autowired
    private NotaCreditoDAOMySQL daoNotaCredito;
    @Autowired
    private GestorHuesped gestorHuesped;
    @Autowired
    private EstadiaDAOMySQL daoEstadia;
    @Autowired
    private ResponsablePagoDAOMySQL daoResponsablePago;
    @Autowired
    private PrecioHabitacion calcularPrecioHabitacion;
    @Autowired
    private DireccionDAOMySQL direccionDAO;
    @Autowired
    private ConsumoDAOMySQL daoConsumo;

    private GestorFactura() {

    }

    public synchronized static GestorFactura getInstance() {
        if(singleton_instance == null) singleton_instance = new GestorFactura();
        return singleton_instance;
    }

    public EstadiaDTO obtenerEstadia(String NroHabitacion) throws FracasoOperacion {
        LocalDate fecha = LocalDate.now();
        return daoEstadia.buscarEstadiaPorHabitacionYFechaFin(NroHabitacion, fecha);
    }

    public ResponsablePagoDTO buscarResponsablePago(String cuit) throws FracasoOperacion {
        try{
            PersonaFisica personaFisica = daoResponsablePago.obtenerPersonaFisica(cuit);
            PersonaJuridica personaJuridica = daoResponsablePago.obtenerPersonaJuridica(cuit);

            if(personaJuridica == null && personaFisica == null){
                throw new FracasoOperacion("No se encontro el responsable de pago");
            }
            else if (personaJuridica != null && personaFisica != null){
                throw new FracasoOperacion("El mismo cuit es tanto de una persona fisica como de una juridica");
            }
            else if (personaJuridica != null){
                return new ResponsablePagoDTO(personaJuridica);
            }
            else {
                return new ResponsablePagoDTO(personaFisica);
            }
        }
        catch (Exception e){ throw new FracasoOperacion("Error: " + e.getMessage()); }
    }

    public ResponsablePagoDTO altaResponsablePago(ResponsablePagoDTO responsablePagoDTO) throws FracasoOperacion {
        try {
            if (responsablePagoDTO == null) {
                throw new FracasoOperacion("El responsablePagoDTO no puede ser null");
            }
            if (responsablePagoDTO.getCuit() == null || responsablePagoDTO.getCuit().isBlank()) {
                throw new FracasoOperacion("El CUIT/CUIL no puede ser vacío");
            }

            PersonaFisica pf = daoResponsablePago.obtenerPersonaFisica(responsablePagoDTO.getCuit());
            if (pf != null) {
                return new ResponsablePagoDTO(pf);
            }

            PersonaJuridica pjExistente = daoResponsablePago.obtenerPersonaJuridica(responsablePagoDTO.getCuit());
            if (pjExistente != null) {
                return new ResponsablePagoDTO(pjExistente);
            }

            boolean esJuridica = responsablePagoDTO.getRazonSocial() != null
                    && !responsablePagoDTO.getRazonSocial().isBlank()
                    && !"N/A".equalsIgnoreCase(responsablePagoDTO.getRazonSocial().trim());

            if (!esJuridica) {
                throw new FracasoOperacion("Alta de Persona Física no implementada con este DTO");
            }

            PersonaJuridica pj = new PersonaJuridica();
            pj.setRazonSocial(responsablePagoDTO.getRazonSocial());
            pj.setCuit(responsablePagoDTO.getCuit());
            pj.setTelefono(responsablePagoDTO.getTelefono());

            pj.setDireccion(
                    direccionDAO.crearDireccion(
                            DireccionMapper.toDomain(responsablePagoDTO.getDireccion())
                    )
            );

            PersonaJuridica guardada = daoResponsablePago.crearPersonaJuridica(pj);
            return new ResponsablePagoDTO(guardada);

        } catch (FracasoOperacion e) {
            throw e;
        } catch (Exception e) {
            throw new FracasoOperacion("Error al dar de alta responsable de pago: " + e.getMessage());
        }
    }

    public void modificarResponsablePago(){

    }

    public void bajaResponsablePago(){

    }

    public void generarFactura(FacturaDTO facturaDTO) throws FracasoOperacion {
        try{

            Factura factura = new Factura();
            factura.setNroFactura(factura.getNroFactura());
            factura.setPago(factura.getPago());
            factura.setEstadia(factura.getEstadia());
            factura.setNotaCredito(factura.getNotaCredito());

            daoFactura.crearFactura(factura);
        }
        catch (Exception e){
            throw new FracasoOperacion("Error al crear factura: " + e.getMessage());
        }
    }

    public void mostrarFacturasResponsablePago(){

    }

    public void generarNotaCredito(){

    }

    public EstadiaFacturacionDTO estadiaFacturacion(String nrHabitacion) throws FracasoOperacion{
        try{
            EstadiaDTO estadia = this.obtenerEstadia(nrHabitacion);
            float montoEstadia = (float) calcularPrecioHabitacion.calcularPrecio(estadia.getHabitacion(), estadia.getFechaInicio(), estadia.getFechaFin());
            ArrayList<Consumo> consumos = daoConsumo.consumosEstadiaNoPagos(estadia.getIdEstadia());

            return new EstadiaFacturacionDTO(estadia, montoEstadia, consumos);
        }
        catch (Exception e){
            throw new FracasoOperacion("Erros: " + e.getMessage());
        }
    }

}

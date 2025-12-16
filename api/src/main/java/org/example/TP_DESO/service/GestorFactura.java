package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.*;
import org.example.TP_DESO.dao.Mappers.HuespedMapper;
import org.example.TP_DESO.domain.Factura;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.domain.PersonaFisica;
import org.example.TP_DESO.domain.PersonaJuridica;
import org.example.TP_DESO.dto.CU12.ResponsablePagoDTO;
import org.example.TP_DESO.dto.EstadiaDTO;
import org.example.TP_DESO.dto.FacturaDTO;
import org.example.TP_DESO.dto.HuespedDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.FacturaRepository;
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

    public void altaResponsablePago(ResponsablePagoDTO responsablePagoDTO){

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

}

package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.FacturaDAOMySQL;
import org.example.TP_DESO.dao.NotaCreditoDAOMySQL;
import org.example.TP_DESO.domain.Factura;
import org.example.TP_DESO.dto.FacturaDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.FacturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestorFactura {
    private static GestorFactura singleton_instance;

    @Autowired
    private FacturaDAOMySQL daoFactura;
    @Autowired
    private NotaCreditoDAOMySQL daoNotaCredito;
    @Autowired
    private GestorHuesped gestorHuesped;

    private GestorFactura() {

    }

    public synchronized static GestorFactura getInstance() {
        if(singleton_instance == null) singleton_instance = new GestorFactura();
        return singleton_instance;
    }

    public void buscarResponsablePago(){

    }

    public void altaResponsablePago(ResponsablePagoDTO responsablePagoDTO){
        try{

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

}

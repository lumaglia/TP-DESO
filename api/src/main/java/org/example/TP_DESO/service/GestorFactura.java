package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.*;
import org.example.TP_DESO.dao.Mappers.HuespedMapper;
import org.example.TP_DESO.domain.Factura;
import org.example.TP_DESO.domain.Huesped;
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

    public ArrayList<Huesped> buscarHuespedes(String NroHabitacion) throws FracasoOperacion {
        LocalDate fecha = LocalDate.now();
        EstadiaDTO estadiaDTO =  daoEstadia.buscarEstadiaPorHabitacionYFechaFin(NroHabitacion, fecha);
        ArrayList<HuespedDTO> huespedes = estadiaDTO.getHuespedes();
        ArrayList<Huesped> result = new ArrayList();
        for(HuespedDTO h : huespedes){
            result.add(HuespedMapper.toDomain(h));
        }
        return result;
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

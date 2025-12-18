package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.DireccionDAOMySQL;
import org.example.TP_DESO.dao.ResponsablePagoDAOMySQL;
import org.example.TP_DESO.dao.*;
import org.example.TP_DESO.dao.FacturaDAOMySQL;
import org.example.TP_DESO.dao.NotaCreditoDAOMySQL;
import org.example.TP_DESO.domain.*;
import org.example.TP_DESO.dto.*;
import org.example.TP_DESO.dto.CU07.EmitirFacturaDTO;
import org.example.TP_DESO.dto.CU07.EstadiaFacturacionDTO;
import org.example.TP_DESO.dto.CU07.RequestCheckoutDTO;
import org.example.TP_DESO.dto.CU12.PersonaFisicaDTO;
import org.example.TP_DESO.dto.CU12.PersonaJuridicaDTO;
import org.example.TP_DESO.dto.CU12.ResponsablePagoDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.patterns.mappers.DireccionMapper;
import org.example.TP_DESO.patterns.mappers.EstadiaMapper;
import org.example.TP_DESO.patterns.mappers.HabitacionMapper;
import org.example.TP_DESO.patterns.mappers.HuespedMapper;
import org.example.TP_DESO.patterns.strategy.PrecioHabitacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    @Autowired
    private GestorHabitacion gestorHabitacion;

    private GestorFactura() {

    }

    public synchronized static GestorFactura getInstance() {
        if(singleton_instance == null) singleton_instance = new GestorFactura();
        return singleton_instance;
    }

    public EstadiaDTO obtenerEstadia(String NroHabitacion, LocalDateTime fin) throws FracasoOperacion {
        try{
            return daoEstadia.buscarEstadiaPorHabitacionYFechaFin(NroHabitacion, fin.toLocalDate());
        }
        catch (Exception e){
            throw new FracasoOperacion("Error al obtener el dto de al estadia en gestor factura: " + e.getMessage());
        }
    }
    public Estadia obtenerEstadiaVinculada(String nroHabitacion, LocalDateTime fin) throws FracasoOperacion{
        try{
            return daoEstadia.obtenerEstadiaNroHabitacionFechaFin(nroHabitacion, fin.toLocalDate());
        }
        catch (Exception e){
            throw new FracasoOperacion("Error al obtener el domain de al estadia en gestor factura: " + e.getMessage());
        }
    }

    public ResponsablePagoDTO buscarResponsablePago(String cuit) throws FracasoOperacion {
        try{
            Optional<PersonaFisica> personaFisica = daoResponsablePago.obtenerPersonaFisicaCuit(cuit);
            PersonaJuridica personaJuridica = daoResponsablePago.obtenerPersonaJuridica(cuit);

            if(personaJuridica == null && personaFisica.isEmpty()){
                throw new FracasoOperacion("No se encontro el responsable de pago");
            }
            else if (personaJuridica != null && personaFisica.isPresent()){
                throw new FracasoOperacion("El mismo cuit es tanto de una persona fisica como de una juridica");
            }
            else if (personaJuridica != null){
                return new PersonaJuridicaDTO(personaJuridica);
            }
            else {
                return new PersonaFisicaDTO(personaFisica.get());
            }
        }
        catch (Exception e){ throw new FracasoOperacion("Error: " + e.getMessage()); }
    }

    public ResponsablePagoDTO altaPersonaJuridica(PersonaJuridicaDTO personaJuridica) throws FracasoOperacion {
        try {
            if (personaJuridica == null) {
                throw new FracasoOperacion("El personaJuridicaDTO no puede ser null");
            }
            if (personaJuridica.getCuit() == null || personaJuridica.getCuit().isBlank()) {
                throw new FracasoOperacion("El CUIT/CUIL no puede ser vacío");
            }

            PersonaJuridica pjExistente = daoResponsablePago.obtenerPersonaJuridica(personaJuridica.getCuit());
            if (pjExistente != null) {
                return new PersonaJuridicaDTO(pjExistente);
            }

            PersonaJuridica pj = new PersonaJuridica();
            pj.setCuit(personaJuridica.getCuit());
            pj.setRazonSocial(personaJuridica.getRazonSocial());
            pj.setTelefono(personaJuridica.getTelefono());

            pj.setDireccion(
                    direccionDAO.crearDireccion(
                            DireccionMapper.toDomain(personaJuridica.getDireccion())
                    )
            );

            PersonaJuridica guardada = daoResponsablePago.crearPersonaJuridica(pj);
            return new PersonaJuridicaDTO(guardada);

        } catch (FracasoOperacion e) {
            throw e;
        } catch (Exception e) {
            throw new FracasoOperacion("Error al dar de alta responsable de pago: " + e.getMessage());
        }
    }

    public void modificarResponsablePago(ResponsablePagoDTO nuevoResponsableDTO) throws FracasoOperacion{
        try{
            if(nuevoResponsableDTO instanceof PersonaJuridicaDTO pj){
                daoResponsablePago.modificarPersonaJuridica(pj.getId(), pj.getRazonSocial(), pj.getCuit(), pj.getTelefono(), DireccionMapper.toDomain(pj.getDireccion()));
            }
        }
        catch (Exception e){
            throw new FracasoOperacion("Error al modificar el responsable juridico: " + e.getMessage());
        }
    }

    public void bajaResponsablePago(){

    }

    public Factura generarFactura(EmitirFacturaDTO emitirFacturaDTO) throws FracasoOperacion {
        try {
            Factura factura = new Factura();

            factura.setPagaEstadia(emitirFacturaDTO.isPagaEstadia());
            factura.setNotaCredito(null);

            LocalDateTime fin = LocalDateTime.ofInstant(Instant.parse(emitirFacturaDTO.getDiaCheckOut()), ZoneId.systemDefault());
            Estadia estadia = obtenerEstadiaVinculada(emitirFacturaDTO.getNumHabitacion(), fin);

            factura.setEstadia(estadia);

            float montoEstadia = (float) calcularPrecioHabitacion.calcularPrecio(HabitacionMapper.toDTO(estadia.getHabitacion()), estadia.getFechaInicio(), fin);
            if(!emitirFacturaDTO.isPagaEstadia()){
                montoEstadia = 0F;
            }

            ArrayList<Consumo> consumos = new ArrayList<>();
            float montoConsumo = 0F;
            for(String idConsumo : emitirFacturaDTO.getConsumos()){
                Consumo consumo = daoConsumo.obtenerConsumoPorId(Long.parseLong(idConsumo));
                montoConsumo += consumo.getMonto();
                consumos.add(consumo);
            }

            factura.setPrecio((montoEstadia + montoConsumo) * 1.21F);

            if (emitirFacturaDTO.isEsHuesped()) {
                HuespedDTO huespedDTO = new HuespedDTO();
                huespedDTO.setTipoDoc(emitirFacturaDTO.getTipoDoc());
                huespedDTO.setNroDoc(emitirFacturaDTO.getNroDoc());

                Huesped huesped = gestorHuesped.buscarHuespedDomain(emitirFacturaDTO.getTipoDoc(), emitirFacturaDTO.getNroDoc());

                Optional<PersonaFisica> pf = daoResponsablePago.obtenerPersonaFisica(huesped.getTipoDoc(), huesped.getNroDoc());

                if(pf.isPresent()){
                    factura.setResponsablePago(pf.get());
                }
                else{
                    PersonaFisica newpf = daoResponsablePago.crearPersonaFisica(huesped);
                    factura.setResponsablePago(newpf);
                }

            }
            else{
                PersonaJuridica pj = daoResponsablePago.obtenerPersonaJuridica(emitirFacturaDTO.getCuit());

                if(pj == null){
                    throw new FracasoOperacion("No existe la persona juridica en el sistema");
                }

                factura.setResponsablePago(pj);
            }

            daoFactura.crearFactura(factura);

            for(Consumo c : consumos){
                c.setFactura(factura);
                daoConsumo.guardarConsumoConFactura(c);
            }

            return factura;
        }
        catch (Exception e){
            throw new FracasoOperacion("Error al crear factura /GestorFactura->GenerarFactura/: " + e.getMessage());
        }
    }

    public void mostrarFacturasResponsablePago(){

    }

    public void generarNotaCredito(){

    }

    public EstadiaFacturacionDTO estadiaFacturacion(RequestCheckoutDTO request) throws FracasoOperacion{
        try{
            LocalDateTime fin = LocalDateTime.ofInstant(Instant.parse(request.getDiaCheckOut()), ZoneId.systemDefault());
            EstadiaDTO estadia = obtenerEstadia(request.getNumHabitacion(), fin);
            float montoEstadia = (float) calcularPrecioHabitacion.calcularPrecio(estadia.getHabitacion(), estadia.getFechaInicio(), fin);
            ArrayList<Consumo> consumosEstadia = daoConsumo.consumosEstadia(estadia.getIdEstadia());

            boolean yaPagoLaEstadia = daoFactura.obtenerFacturaPorEstadia(estadia.getIdEstadia()).stream().anyMatch(Factura::isPagaEstadia);

            if(yaPagoLaEstadia){
                montoEstadia = 0F;
            }
            return new EstadiaFacturacionDTO(estadia, montoEstadia, consumosEstadia);
        }
        catch (Exception e){
            throw new FracasoOperacion("Error al obtener la estadia para facturar: " + e.getMessage());
        }
    }
}

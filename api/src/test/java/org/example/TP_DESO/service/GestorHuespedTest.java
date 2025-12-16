package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.HuespedDAO;
import org.example.TP_DESO.domain.Direccion;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.dto.HuespedDTO;
import org.example.TP_DESO.exceptions.DocumentoYaExistente;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GestorHuespedTest {

    @MockBean
    private HuespedDAO huespedDAO;

    @Autowired
    @InjectMocks
    private GestorHuesped gestorHuesped;

    @Test
    void testAltaHuesped() throws FracasoOperacion {
        DireccionDTO direccion = new DireccionDTO("Calle 1","","1230","CiudadA","ProvinciaB","PaisC");

        HuespedDTO huespedRepetido = new HuespedDTO("DNI","123","LASNAME","FIRSNAME","","Consumidor Final", LocalDate.of(2000,01,02),"1234","","Estudiante","Argentina",direccion);
        HuespedDTO huespedNuevo = new HuespedDTO("LC","245","LASNAME","FIRSNAME","","Consumidor Final", LocalDate.of(2000,01,02),"1234","","Estudiante","Argentina",direccion);


        HuespedDTO dniRepetido = new HuespedDTO("DNI","123");
        HuespedDTO dniNuevo = new HuespedDTO("LC","245");

        Direccion direccionDomain = new Direccion("Calle 1","","1230","CiudadA","ProvinciaB","PaisC");
        Huesped huespedRepetidoDomain = new Huesped("DNI","123","LASNAME","FIRSNAME","","Consumidor Final", LocalDate.of(2000,01,02),"1234","","Estudiante","Argentina",direccionDomain);
        Huesped huespedNuevoDomain = new Huesped("FIRSNAME","LASNAME","LC","245","","Consumidor Final", LocalDate.of(2000,01,02),"1234","","Estudiante","Argentina",direccionDomain);

        when(huespedDAO.obtenerHuesped(argThat(dto ->
        dto != null && "DNI".equals(dto.getTipoDoc()) && "123".equals(dto.getNroDoc()) ))).thenReturn(new ArrayList<>(List.of(huespedRepetido)));

        when(huespedDAO.obtenerHuesped(argThat(dto ->
        dto != null && "LC".equals(dto.getTipoDoc()) && "245".equals(dto.getNroDoc()) ))).thenReturn(new ArrayList<>());

        DocumentoYaExistente f = assertThrows(DocumentoYaExistente.class, () -> gestorHuesped.altaHuesped(huespedRepetido),"Tipo y Numero de Documento ya existentes deberían lanzar excepción.");
        //Revisar pq no sale este ^^^^^^^^^^ assertThrows :(

        assertEquals("El tipo y numero de documento ya existen en el sistema",f.getMessage(), "Tipo y Numero de Documento ya existentes deberían lanzar excepción.");


        assertDoesNotThrow(()-> gestorHuesped.altaHuesped(huespedNuevo), "Huesped no existente en el sistema debería funcionar");
    }

    @Test
    void testBajaHuesped() throws FracasoOperacion {
        DireccionDTO direccion = new DireccionDTO("Calle 1","","1230","CiudadA","ProvinciaB","PaisC");

        HuespedDTO huespedExistente = new HuespedDTO("DNI","123","LASNAME","FIRSNAME","","Consumidor Final", LocalDate.of(2000,01,02),"1234","","Estudiante","Argentina",direccion);
        HuespedDTO huespedNoExistente = new HuespedDTO("LC","245","LASNAME","FIRSNAME","","Consumidor Final", LocalDate.of(2000,01,02),"1234","","Estudiante","Argentina",direccion);


        HuespedDTO dniExistente = new HuespedDTO("DNI","123");
        HuespedDTO dniNoExistente = new HuespedDTO("LC","245");

        doThrow(new FracasoOperacion("Huesped no existente")).when(huespedDAO).eliminarHuesped("LC","245");
        assertDoesNotThrow(()->gestorHuesped.bajaHuesped(huespedExistente),"Huesped debe existir");
        FracasoOperacion f = assertThrows(FracasoOperacion.class,()->gestorHuesped.bajaHuesped(huespedNoExistente), "Huesped debe no existir");
        assertEquals("Huesped no existente",f.getMessage(),"Huesped debe no existir");
    }

    @Test
    void testBuscarHuesped() throws FracasoOperacion {

        //Faltan poner mensajes en los asserts

        // busqueda sin datos
        HuespedDTO filtroVacio = new HuespedDTO();

        ArrayList<HuespedDTO> listaCompleta = new ArrayList<>();
        listaCompleta.add(new HuespedDTO("DNI", "123"));

        when(huespedDAO.obtenerHuesped()).thenReturn(listaCompleta);

        ArrayList<HuespedDTO> res1 = gestorHuesped.buscarHuesped(filtroVacio);

        assertNotNull(res1);
        assertEquals(1, res1.size());
        assertEquals("DNI", res1.get(0).getTipoDoc());
        assertEquals("123", res1.get(0).getNroDoc());

        verify(huespedDAO, times(1)).obtenerHuesped();
        verify(huespedDAO, never()).obtenerHuesped(any(HuespedDTO.class));

        //busqueda con datos
        reset(huespedDAO);

        DireccionDTO direccion = new DireccionDTO("Calle 1", "", "1230", "CiudadA", "ProvinciaB", "PaisC");
        HuespedDTO filtroConDatos = new HuespedDTO(
                "LC", "245", "LASNAME", "FIRSNAME", "", "Consumidor Final",
                LocalDate.of(2000, 1, 2), "1234", "", "Estudiante", "Argentina", direccion
        );

        ArrayList<HuespedDTO> filtrados = new ArrayList<>(List.of(new HuespedDTO("LC", "245")));
        when(huespedDAO.obtenerHuesped(any(HuespedDTO.class))).thenReturn(filtrados);

        ArrayList<HuespedDTO> res2 = gestorHuesped.buscarHuesped(filtroConDatos);

        assertNotNull(res2);
        assertEquals(1, res2.size());
        assertEquals("LC", res2.get(0).getTipoDoc());
        assertEquals("245", res2.get(0).getNroDoc());

        verify(huespedDAO, never()).obtenerHuesped();
        verify(huespedDAO, times(1)).obtenerHuesped(any(HuespedDTO.class));
    }

    @Test
    void testModificarHuesped() throws FracasoOperacion {

        //Faltan poner mensajes en los asserts

        DireccionDTO direccion = new DireccionDTO("Calle 1", "", "1230", "CiudadA", "ProvinciaB", "PaisC");

        // doc vijeo
        String tipoDocViejo = "DNI";
        String nroDocViejo = "111";

        // doc nuevo
        String tipoDocNuevo = "DNI";
        String nroDocNuevo = "222";

        HuespedDTO huespedModificado = new HuespedDTO(
                tipoDocNuevo, nroDocNuevo, "PEREZ", "JUAN", "20-00000000-0", "Consumidor Final",
                LocalDate.of(2000, 1, 2), "1234", "mail@ejemplo.com", "Estudiante", "Argentina", direccion
        );

        // caso donde ya existe el documento
        when(huespedDAO.obtenerHuesped(argThat(dto ->
                dto != null && tipoDocNuevo.equals(dto.getTipoDoc()) && nroDocNuevo.equals(dto.getNroDoc()) ))).thenReturn(new ArrayList<>(List.of(new HuespedDTO(tipoDocNuevo, nroDocNuevo))));

        DocumentoYaExistente ex = assertThrows(
                DocumentoYaExistente.class, () -> gestorHuesped.modificarHuesped(tipoDocViejo, nroDocViejo, huespedModificado)
        );

        assertEquals("El tipo y numero de documento ya existen en el sistema", ex.getMessage());
        verify(huespedDAO, never()).modificarHuesped(anyString(), anyString(), any(Huesped.class));

        // "camino lindo"
        reset(huespedDAO);

        when(huespedDAO.obtenerHuesped(argThat(dto ->
                dto != null && tipoDocNuevo.equals(dto.getTipoDoc()) && nroDocNuevo.equals(dto.getNroDoc()) ))).thenReturn(new ArrayList<>());

        assertDoesNotThrow(() -> gestorHuesped.modificarHuesped(tipoDocViejo, nroDocViejo, huespedModificado));

        ArgumentCaptor<Huesped> captorHuesped = ArgumentCaptor.forClass(Huesped.class);
        verify(huespedDAO, times(1)).modificarHuesped(eq(tipoDocViejo), eq(nroDocViejo), captorHuesped.capture());

        Huesped enviadoAlDao = captorHuesped.getValue();
        assertNotNull(enviadoAlDao);

        assertEquals("JUAN", enviadoAlDao.getNombre());
        assertEquals("PEREZ", enviadoAlDao.getApellido());
        assertEquals(tipoDocNuevo, enviadoAlDao.getTipoDoc());
        assertEquals(nroDocNuevo, enviadoAlDao.getNroDoc());
        assertEquals("20-00000000-0", enviadoAlDao.getCuil());
        assertEquals("Consumidor Final", enviadoAlDao.getPosicionIva());
        assertEquals(LocalDate.of(2000, 1, 2), enviadoAlDao.getFechaNac());
        assertEquals("1234", enviadoAlDao.getTelefono());
        assertEquals("mail@ejemplo.com", enviadoAlDao.getEmail());
        assertEquals("Estudiante", enviadoAlDao.getOcupacion());
        assertEquals("Argentina", enviadoAlDao.getNacionalidad());

        assertNotNull(enviadoAlDao.getDireccion());
        assertEquals("Calle 1", enviadoAlDao.getDireccion().getDomicilio());
        assertEquals("", enviadoAlDao.getDireccion().getDepto());
        assertEquals("1230", enviadoAlDao.getDireccion().getCodigoPostal());
        assertEquals("CiudadA", enviadoAlDao.getDireccion().getLocalidad());
        assertEquals("ProvinciaB", enviadoAlDao.getDireccion().getProvincia());
        assertEquals("PaisC", enviadoAlDao.getDireccion().getPais());
    }

}

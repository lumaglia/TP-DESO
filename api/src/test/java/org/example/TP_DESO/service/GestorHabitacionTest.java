package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.HabitacionDAO;
import org.example.TP_DESO.domain.DobleEstandar;
import org.example.TP_DESO.domain.Habitacion;
import org.example.TP_DESO.dto.DobleEstandarDTO;
import org.example.TP_DESO.dto.HabitacionDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GestorHabitacionTest {

    @MockBean
    private HabitacionDAO dao;

    @Autowired
    @InjectMocks
    private GestorHabitacion gestorHabitacion;

    @Test
    void testMostrarHabitacionDomain_ok() throws FracasoOperacion {
        ArrayList<Habitacion> habitaciones = new ArrayList<>();

        DobleEstandar h1 = new DobleEstandar();
        h1.setNroHabitacion("101");
        h1.setPrecioNoche(1000);
        h1.setCapacidad(2);
        h1.setTamanno("20m2");
        h1.setCamasInd(2);
        h1.setCamasDob(0);

        habitaciones.add(h1);

        when(dao.obtenerTodasDomainForm()).thenReturn(habitaciones);

        ArrayList<Habitacion> res = gestorHabitacion.mostrarHabitacionDomain();

        assertNotNull(res, "La lista no debería ser null");
        assertEquals(1, res.size(), "Debería devolver 1 habitación");
        assertEquals("101", res.get(0).getNroHabitacion(), "Debería matchear el nroHabitacion");

        verify(dao, times(1)).obtenerTodasDomainForm();
        verify(dao, never()).obtenerTodas();
        verify(dao, never()).obtenerHabitacion(anyString());
    }

    @Test
    void testMostrarHabitacionDomain_error_FO() throws FracasoOperacion {
        when(dao.obtenerTodasDomainForm()).thenThrow(new FracasoOperacion("Fallo DAO"));

        FracasoOperacion ex = assertThrows(
                FracasoOperacion.class,
                () -> gestorHabitacion.mostrarHabitacionDomain(),
                "Cuando el DAO falla, el gestor debería lanzar FracasoOperacion"
        );

        assertTrue(
                ex.getMessage().startsWith("Error al obtener las habitaciones."),
                "El mensaje debería estar con el prefijo del gestor"
        );
        assertTrue(
                ex.getMessage().contains("Fallo DAO"),
                "El mensaje debería contener el detalle original"
        );

        verify(dao, times(1)).obtenerTodasDomainForm();
        verify(dao, never()).obtenerTodas();
        verify(dao, never()).obtenerHabitacion(anyString());
    }

    @Test
    void testMostrarHabitacionesDTO_ok() throws FracasoOperacion {
        ArrayList<HabitacionDTO> habitacionesDTO = new ArrayList<>(
                List.of(new DobleEstandarDTO("101", 1000, 2, "20m2", 2, 0))
        );

        when(dao.obtenerTodas()).thenReturn(habitacionesDTO);

        ArrayList<HabitacionDTO> res = gestorHabitacion.mostrarHabitacionesDTO();

        assertNotNull(res, "La lista no debería ser null");
        assertEquals(1, res.size(), "Debería devolver 1 habitación DTO");
        assertEquals("101", res.get(0).getNroHabitacion(), "Debería matchear el nroHabitacion");

        verify(dao, times(1)).obtenerTodas();
        verify(dao, never()).obtenerTodasDomainForm();
        verify(dao, never()).obtenerHabitacion(anyString());
    }

    @Test
    void testMostrarHabitacionesDTO_error_FO() throws FracasoOperacion {
        when(dao.obtenerTodas()).thenThrow(new FracasoOperacion("Fallo DAO"));

        FracasoOperacion ex = assertThrows(
                FracasoOperacion.class,
                () -> gestorHabitacion.mostrarHabitacionesDTO(),
                "Cuando el DAO falla, el gestor debería lanzar FracasoOperacion"
        );

        assertTrue(
                ex.getMessage().startsWith("Error al obtener las habitaciones."),
                "El mensaje debería estar con el prefijo del gestor"
        );
        assertTrue(
                ex.getMessage().contains("Fallo DAO"),
                "El mensaje debería contener el detalle original"
        );

        verify(dao, times(1)).obtenerTodas();
        verify(dao, never()).obtenerTodasDomainForm();
        verify(dao, never()).obtenerHabitacion(anyString());
    }

    @Test
    void testObtenerHabitacion_ok() throws FracasoOperacion {
        HabitacionDTO dto = new DobleEstandarDTO("101", 1000, 2, "20m2", 2, 0);
        when(dao.obtenerHabitacion("101")).thenReturn(dto);

        HabitacionDTO res = gestorHabitacion.obtenerHabitacion("101");

        assertNotNull(res, "El DTO no debería ser null");
        assertEquals("101", res.getNroHabitacion(), "Debería matchear el nroHabitacion");

        verify(dao, times(1)).obtenerHabitacion("101");
        verify(dao, never()).obtenerTodas();
        verify(dao, never()).obtenerTodasDomainForm();
    }

    @Test
    void testObtenerHabitacion_error_noHabitacion() throws FracasoOperacion {
        when(dao.obtenerHabitacion("404")).thenThrow(new FracasoOperacion("No se ha encontrado la habitación de ID:404"));

        FracasoOperacion ex = assertThrows(
                FracasoOperacion.class,
                () -> gestorHabitacion.obtenerHabitacion("404"),
                "Si el DAO lanza excepción, el gestor debería mostrarlo"
        );

        assertEquals("No se ha encontrado la habitación de ID:404", ex.getMessage());

        verify(dao, times(1)).obtenerHabitacion("404");
        verify(dao, never()).obtenerTodas();
        verify(dao, never()).obtenerTodasDomainForm();
    }
}
package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.EstadiaDAO;
import org.example.TP_DESO.dao.ReservaDAO;
import org.example.TP_DESO.domain.DobleEstandar;
import org.example.TP_DESO.domain.Estadia;
import org.example.TP_DESO.domain.Habitacion;
import org.example.TP_DESO.domain.Reserva;
import org.example.TP_DESO.dto.*;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GestorReservaTest {

    @MockBean
    private ReservaDAO daoReserva;

    @MockBean
    private EstadiaDAO daoEstadia;

    @MockBean
    private GestorHabitacion gestorHabitacion;

    @Autowired
    @InjectMocks
    private GestorReserva gestorReserva;

    @Test
    void testHacerReserva_ok() throws FracasoOperacion {
        HabitacionDTO habDTO = new DobleEstandarDTO("101", 1000, 2, "20m2", 2, 0);

        ReservaDTO dto = new ReservaDTO(
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 10),
                LocalDate.of(2025, 1, 12),
                "PEREZ",
                "JUAN",
                "1234",
                false,
                habDTO,
                null
        );

        String msg = gestorReserva.hacerReserva(dto);

        assertEquals("Reserva creada exitosamente", msg, "Debe crear la reserva");

        ArgumentCaptor<Reserva> captor = ArgumentCaptor.forClass(Reserva.class);
        verify(daoReserva, times(1)).crearReserva(captor.capture());

        Reserva enviada = captor.getValue();
        assertNotNull(enviada, "Reserva enviada no puede ser null");
        assertEquals("PEREZ", enviada.getApellido(), "Apellido mal");
        assertEquals("JUAN", enviada.getNombre(), "Nombre mal");
        assertEquals("101", enviada.getHabitacion().getNroHabitacion(), "Habitación mal");
        assertFalse(enviada.isCancelada(), "Debe quedar no cancelada");
        assertNull(enviada.getEstadia(), "Debe quedar sin estadía");
    }

    @Test
    void testHacerReserva_error() throws FracasoOperacion {
        doThrow(new RuntimeException("X")).when(daoReserva).crearReserva(any(Reserva.class));

        HabitacionDTO habDTO = new DobleEstandarDTO("101", 1000, 2, "20m2", 2, 0);
        ReservaDTO dto = new ReservaDTO(
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 10),
                LocalDate.of(2025, 1, 12),
                "PEREZ",
                "JUAN",
                "1234",
                false,
                habDTO,
                null
        );

        FracasoOperacion ex = assertThrows(FracasoOperacion.class, () -> gestorReserva.hacerReserva(dto), "Debe fallar");
        assertTrue(ex.getMessage().startsWith("Error al guardar la reserva"), "Mensaje mal");
    }

    @Test
    void testBuscarReserva_ok() throws FracasoOperacion {
        ReservaDTO filtro = new ReservaDTO("PEREZ", "JUAN");

        ArrayList<ReservaDTO> lista = new ArrayList<>();
        lista.add(new ReservaDTO("PEREZ", "JUAN"));

        when(daoReserva.buscarReservasPorApellidoYNombre("PEREZ", "JUAN")).thenReturn(lista);

        ArrayList<ReservaDTO> res = gestorReserva.buscarReserva(filtro);

        assertNotNull(res, "No debe ser null");
        assertEquals(1, res.size(), "Cantidad mal");
        verify(daoReserva, times(1)).buscarReservasPorApellidoYNombre("PEREZ", "JUAN");
    }

    @Test
    void testCancelarReserva_sinReserva() throws FracasoOperacion {
        ResponseReservaDTO req = mock(ResponseReservaDTO.class);
        when(req.getNroHabitacion()).thenReturn("101");
        when(req.getFechaInicio()).thenReturn(LocalDate.of(2025, 2, 1));

        HabitacionDTO habDTO = new DobleEstandarDTO("101", 1000, 2, "20m2", 2, 0);
        when(gestorHabitacion.obtenerHabitacion("101")).thenReturn(habDTO);

        when(daoReserva.buscarReservasPorHabitacionFechaInicio(any(Habitacion.class), eq(LocalDate.of(2025, 2, 1))))
                .thenReturn(new ArrayList<>());

        FracasoOperacion ex = assertThrows(FracasoOperacion.class, () -> gestorReserva.cancelarReserva(req), "Debe fallar");
        assertEquals(
                "Error al cancelar reserva: No hay reservas pendientes de cancelar con los argumentos enviados",
                ex.getMessage(),
                "Mensaje mal"
        );

        verify(daoReserva, never()).modificarReserva(anyLong(), any(Reserva.class));
    }

    @Test
    void testCancelarReserva_ok() throws FracasoOperacion {
        ResponseReservaDTO req = mock(ResponseReservaDTO.class);
        when(req.getNroHabitacion()).thenReturn("101");
        when(req.getFechaInicio()).thenReturn(LocalDate.of(2025, 2, 1));

        HabitacionDTO habDTO = new DobleEstandarDTO("101", 1000, 2, "20m2", 2, 0);
        when(gestorHabitacion.obtenerHabitacion("101")).thenReturn(habDTO);

        ReservaDTO encontrada = ReservaDTO.builder()
                .id(55L)
                .fechaInicio(LocalDate.of(2025, 2, 1))
                .fechaFin(LocalDate.of(2025, 2, 3))
                .apellido("PEREZ")
                .nombre("JUAN")
                .telefono("1234")
                .cancelada(false)
                .habitacion(habDTO)
                .estadia(null)
                .build();

        when(daoReserva.buscarReservasPorHabitacionFechaInicio(any(Habitacion.class), eq(LocalDate.of(2025, 2, 1))))
                .thenReturn(new ArrayList<>(List.of(encontrada)));

        assertDoesNotThrow(() -> gestorReserva.cancelarReserva(req), "Debe cancelar");

        ArgumentCaptor<Reserva> captor = ArgumentCaptor.forClass(Reserva.class);
        verify(daoReserva, times(1)).modificarReserva(eq(55L), captor.capture());

        assertTrue(captor.getValue().isCancelada(), "Debe quedar cancelada");
    }

    @Test
    void testCheckIn_ok_sinReserva() throws FracasoOperacion {
        HabitacionDTO habDTO = new DobleEstandarDTO("101", 1000, 2, "20m2", 2, 0);

        DireccionDTO dir = new DireccionDTO("Calle 1", "", "1230", "CiudadA", "ProvinciaB", "PaisC");
        HuespedDTO h = new HuespedDTO(
                "DNI", "123", "PEREZ", "JUAN", "", "Consumidor Final",
                LocalDate.of(2000, 1, 2), "1234", "", "Estudiante", "Argentina", dir
        );

        EstadiaDTO estadiaDTO = EstadiaDTO.builder()
                .fechaInicio(LocalDate.of(2025, 3, 1))
                .fechaFin(LocalDate.of(2025, 3, 5))
                .habitacion(habDTO)
                .huespedes(new ArrayList<>(List.of(h)))
                .build();

        when(gestorHabitacion.obtenerHabitacion("101")).thenReturn(habDTO);
        when(daoReserva.obtenerReservasEntreFechas(LocalDate.of(2025, 3, 1), LocalDate.of(2025, 3, 5)))
                .thenReturn(new ArrayList<>());

        boolean ok = gestorReserva.checkIn(estadiaDTO);

        assertTrue(ok, "Debe devolver true");
        verify(daoEstadia, times(1)).crearEstadia(any(Estadia.class));
        verify(daoReserva, never()).modificarReserva(anyLong(), any(Reserva.class));
    }

    @Test
    void testGetReservaEstadia_ok_vacio() throws FracasoOperacion {
        ArrayList<Habitacion> habitaciones = new ArrayList<>();
        DobleEstandar h1 = new DobleEstandar();
        h1.setNroHabitacion("101");
        habitaciones.add(h1);

        when(gestorHabitacion.mostrarHabitacionDomain()).thenReturn(habitaciones);
        when(daoReserva.obtenerReservasEntreFechasDomainForm(any(LocalDate.class), any(LocalDate.class)))
                .thenThrow(new FracasoOperacion("sin reservas"));
        when(daoEstadia.obtenerEstadiaEntreFechasDomainForm(any(LocalDate.class), any(LocalDate.class)))
                .thenThrow(new FracasoOperacion("sin estadias"));

        ArrayList<ReservasEstadiasPorHabitacionDTO> res = gestorReserva.getReservaEstadia(
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 31)
        );

        assertNotNull(res, "No debe ser null");
        assertEquals(1, res.size(), "Cantidad mal");
        assertEquals("101", res.get(0).getHabitacion().getNroHabitacion(), "Habitación mal");
    }
}

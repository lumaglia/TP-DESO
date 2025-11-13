package org.example.TP_DESO;

import org.example.TP_DESO.dao.HuespedDAOMySQL;
import org.example.TP_DESO.domain.Direccion;
import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.dto.HuespedDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestHuesped {

    @Autowired
    private HuespedDAOMySQL huespedDAO;

    @Test
    void crearYObtenerHuesped() throws FracasoOperacion {
        Direccion direccion = new Direccion("Calle Falsa 123", "1A", "3000", "Santa Fe", "Santa Fe", "Argentina");
        Huesped nuevo = new Huesped(
                "DNI", "12345678", "Perez", "Juan", "20-12345678-9",
                "Consumidor Final", LocalDate.of(1990, 5, 20),
                "3425555555", "juan@mail.com", "Ingeniero", "Argentina",
                direccion, new ArrayList<>(), new ArrayList<>()
        );

        huespedDAO.crearHuesped(nuevo);

        HuespedDTO filtro = new HuespedDTO(
                "DNI", "12345678", "Perez", "Juan", "20-12345678-9",
                "Consumidor Final", LocalDate.of(1990, 5, 20),
                "3425555555", "juan@mail.com", "Ingeniero", "Argentina",
                new DireccionDTO("Calle Falsa 123", "1A", "3000", "Santa Fe", "Santa Fe", "Argentina")
        );

        var encontrados = huespedDAO.obtenerHuesped(filtro);
        assertFalse(encontrados.isEmpty(), "El huésped debería existir");
        assertEquals("Juan", encontrados.get(0).getNombre());
        assertEquals("Perez", encontrados.get(0).getApellido());
    }

    @Test
    void modificarHuesped() throws FracasoOperacion {
        Direccion direccion = new Direccion("Calle Falsa 456", "2B", "3000", "Santa Fe", "Santa Fe", "Argentina");
        Huesped nuevo = new Huesped(
                "DNI", "87654321", "Lopez", "Maria", "27-87654321-5",
                "Monotributista", LocalDate.of(1985, 3, 15),
                "3424444444", "maria@mail.com", "Docente", "Argentina",
                direccion, new ArrayList<>(), new ArrayList<>()
        );

        huespedDAO.crearHuesped(nuevo);

        nuevo.setApellido("Gomez");
        huespedDAO.modificarHuesped("DNI", "87654321", nuevo);

        HuespedDTO filtro = new HuespedDTO(
                "DNI", "87654321", "Gomez", "Maria", "27-87654321-5",
                "Monotributista", LocalDate.of(1985, 3, 15),
                "3424444444", "maria@mail.com", "Docente", "Argentina",
                new DireccionDTO("Calle Falsa 456", "2B", "3000", "Santa Fe", "Santa Fe", "Argentina")
        );

        var encontrados = huespedDAO.obtenerHuesped(filtro);
        assertFalse(encontrados.isEmpty(), "El huésped debería haberse modificado");
        assertEquals("Gomez", encontrados.get(0).getApellido());
    }

    @Test
    void eliminarHuesped() throws FracasoOperacion {
        Direccion direccion = new Direccion("Av. Siempre Viva", "3C", "4000", "Rosario", "Santa Fe", "Argentina");
        Huesped nuevo = new Huesped(
                "DNI", "55555555", "Martinez", "Carlos", "23-55555555-7",
                "Responsable Inscripto", LocalDate.of(1975, 8, 10),
                "3423333333", "carlos@mail.com", "Abogado", "Argentina",
                direccion, new ArrayList<>(), new ArrayList<>()
        );

        huespedDAO.crearHuesped(nuevo);

        huespedDAO.eliminarHuesped("DNI", "55555555");

        HuespedDTO filtro = new HuespedDTO(
                "DNI", "55555555", "Martinez", "Carlos", "23-55555555-7",
                "Responsable Inscripto", LocalDate.of(1975, 8, 10),
                "3423333333", "carlos@mail.com", "Abogado", "Argentina",
                new DireccionDTO("Av. Siempre Viva", "3C", "4000", "Rosario", "Santa Fe", "Argentina")
        );

        var encontrados = huespedDAO.obtenerHuesped(filtro);
        assertTrue(encontrados.isEmpty(), "El huésped debería haber sido eliminado");
    }
}

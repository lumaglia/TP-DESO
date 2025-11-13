package org.example.TP_DESO;

import org.example.TP_DESO.dao.DireccionDAOMySQL;
import org.example.TP_DESO.domain.Direccion;
import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestDireccion {

    @Autowired
    private DireccionDAOMySQL direccionDAO;

    @Test
    void crearYObtenerDireccion() throws FracasoOperacion {
        Direccion nueva = new Direccion("Av. Siempre Viva", "3C", "4000", "Rosario", "Santa Fe", "Argentina");
        direccionDAO.crearDireccion(nueva);

        DireccionDTO dto = direccionDAO.obtenerDireccion("Argentina", "4000", "Av. Siempre Viva", "3C");

        assertNotNull(dto, "La dirección debería existir");
        assertEquals("Av. Siempre Viva", dto.getDomicilio());
        assertEquals("Rosario", dto.getLocalidad());
    }
}

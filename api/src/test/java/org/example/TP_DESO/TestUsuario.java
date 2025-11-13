package org.example.TP_DESO;

import org.example.TP_DESO.dao.UsuarioDAOMySQL;
import org.example.TP_DESO.domain.Usuario;
import org.example.TP_DESO.dto.UsuarioDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TestUsuario {

    @Autowired
    private UsuarioDAOMySQL usuarioDAO;

    @Test
    void crearYObtenerUsuario() throws FracasoOperacion {
        // Crear usuario de dominio
        Usuario nuevo = new Usuario("nacho", "1234");
        usuarioDAO.CrearUsuario(nuevo);

        // Crear DTO con los mismos datos
        UsuarioDTO dto = new UsuarioDTO("nacho", "1234");

        // Buscarlo usando el DTO
        UsuarioDTO obtenido = usuarioDAO.ObtenerUsuario(dto);

        assertNotNull(obtenido, "El usuario debería existir");
        assertEquals("nacho", obtenido.getUsuario());
        assertEquals("1234", obtenido.getContrasenna());
    }

    @Test
    void eliminarUsuario() throws FracasoOperacion {
        Usuario nuevo = new Usuario("borrar", "test");
        usuarioDAO.CrearUsuario(nuevo);

        usuarioDAO.EliminarUsuario("borrar");

        UsuarioDTO dto = new UsuarioDTO("borrar", "test");
        UsuarioDTO eliminado = usuarioDAO.ObtenerUsuario(dto);
        assertNull(eliminado, "El usuario debería haber sido eliminado");
    }
}

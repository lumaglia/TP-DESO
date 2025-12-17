package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.UsuarioDAO;
import org.example.TP_DESO.domain.Usuario;
import org.example.TP_DESO.dto.UsuarioDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class GestorUsuarioTest {

    @MockBean
    private UsuarioDAO dao;

    @MockBean
    private PasswordEncoder passwordEncoder;

    @Autowired
    @InjectMocks
    private GestorUsuario gestorUsuario;

    @Test
    void testAltaUsuario_ok() throws FracasoOperacion {
        when(passwordEncoder.encode("1234")).thenReturn("<HASH_PLACEHOLDER>");

        gestorUsuario.altaUsuario("juan", "1234");

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(dao, times(1)).CrearUsuario(captor.capture());

        Usuario enviado = captor.getValue();
        assertEquals("juan", enviado.getUsuario(), "Usuario mal");
        assertEquals("<HASH_PLACEHOLDER>", enviado.getContrasenna(), "Debe guardarse el hash");
        verify(passwordEncoder, times(1)).encode("1234");
    }

    @Test
    void testAltaUsuario_error_FO() throws FracasoOperacion {
        doThrow(new FracasoOperacion("Error al crear usuario: X")).when(dao).CrearUsuario(any(Usuario.class));

        FracasoOperacion ex = assertThrows(
                FracasoOperacion.class,
                () -> gestorUsuario.altaUsuario("juan", "1234"),
                "Si el DAO falla, el gestor debería propagar FracasoOperacion"
        );

        assertEquals("Error al crear usuario: X", ex.getMessage());
        verify(dao, times(1)).CrearUsuario(any(Usuario.class));
    }

    @Test
    void testAutenticar_true_siDaoDevuelveUsuario() throws FracasoOperacion {
        when(dao.ObtenerUsuario(any(UsuarioDTO.class))).thenReturn(new UsuarioDTO("juan", "1234"));

        boolean res = gestorUsuario.autenticar("juan", "1234");

        assertTrue(res, "Debería autenticar si el DAO devuelve un usuario");
        verify(dao, times(1)).ObtenerUsuario(any(UsuarioDTO.class));
        verify(dao, never()).CrearUsuario(any(Usuario.class));
        verify(dao, never()).ModificarUsuario(anyString(), any(Usuario.class));
        verify(dao, never()).EliminarUsuario(anyString());
    }

    @Test
    void testAutenticar_false_siDaoDevuelveNull() throws FracasoOperacion {
        when(dao.ObtenerUsuario(any(UsuarioDTO.class))).thenReturn(null);

        boolean res = gestorUsuario.autenticar("juan", "mal");

        assertFalse(res, "Debería devolver false si el DAO no encuentra usuario");
        verify(dao, times(1)).ObtenerUsuario(any(UsuarioDTO.class));
    }

    @Test
    void testAutenticar_error_FO() throws FracasoOperacion {
        when(dao.ObtenerUsuario(any(UsuarioDTO.class))).thenThrow(new FracasoOperacion("Error al obtener usuario: X"));

        FracasoOperacion ex = assertThrows(
                FracasoOperacion.class,
                () -> gestorUsuario.autenticar("juan", "1234"),
                "Si el DAO falla, el gestor debería propagar FracasoOperacion"
        );

        assertEquals("Error al obtener usuario: X", ex.getMessage());
        verify(dao, times(1)).ObtenerUsuario(any(UsuarioDTO.class));
    }

    @Test
    void testModificar_ok_llamaDaoConArgumentosCorrectos() throws FracasoOperacion {
        assertDoesNotThrow(() -> gestorUsuario.modificar("juan", "nueva"));

        ArgumentCaptor<Usuario> captor = ArgumentCaptor.forClass(Usuario.class);
        verify(dao, times(1)).ModificarUsuario(eq("juan"), captor.capture());

        Usuario enviado = captor.getValue();
        assertNotNull(enviado, "El usuario enviado al DAO no debería ser null");
        assertEquals("juan", enviado.getUsuario(), "El username debería coincidir");
        assertEquals("nueva", enviado.getContrasenna(), "La contraseña debería coincidir");

        verify(dao, never()).CrearUsuario(any(Usuario.class));
        verify(dao, never()).ObtenerUsuario(any(UsuarioDTO.class));
        verify(dao, never()).EliminarUsuario(anyString());
    }

    @Test
    void testModificar_error_FO() throws FracasoOperacion {
        doThrow(new FracasoOperacion("Usuario no encontrado: juan")).when(dao).ModificarUsuario(eq("juan"), any(Usuario.class));

        FracasoOperacion ex = assertThrows(
                FracasoOperacion.class,
                () -> gestorUsuario.modificar("juan", "nueva"),
                "Si el DAO falla, el gestor debería propagar FracasoOperacion"
        );

        assertEquals("Usuario no encontrado: juan", ex.getMessage());
        verify(dao, times(1)).ModificarUsuario(eq("juan"), any(Usuario.class));
    }

    @Test
    void testEliminar_ok_llamaDao() throws FracasoOperacion {
        assertDoesNotThrow(() -> gestorUsuario.eliminar("juan"));

        verify(dao, times(1)).EliminarUsuario("juan");
        verify(dao, never()).CrearUsuario(any(Usuario.class));
        verify(dao, never()).ObtenerUsuario(any(UsuarioDTO.class));
        verify(dao, never()).ModificarUsuario(anyString(), any(Usuario.class));
    }

    @Test
    void testEliminar_error_FO() throws FracasoOperacion {
        doThrow(new FracasoOperacion("Usuario no encontrado: juan")).when(dao).EliminarUsuario("juan");

        FracasoOperacion ex = assertThrows(
                FracasoOperacion.class,
                () -> gestorUsuario.eliminar("juan"),
                "Si el DAO falla, el gestor debería propagar FracasoOperacion"
        );

        assertEquals("Usuario no encontrado: juan", ex.getMessage());
        verify(dao, times(1)).EliminarUsuario("juan");
    }
}

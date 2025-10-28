package dao;

import domain.Usuario;
import dto.UsuarioDTO;

import java.io.IOException;


public interface UsuarioDAO {
    void CrearUsuario(Usuario usuario);
    UsuarioDTO ObtenerUsuario(UsuarioDTO usuario) throws IOException;
    void ModificarUsuario(String nombreUsuario, Usuario usuario);
    void EliminarUsuario(String usuario);
}

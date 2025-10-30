package dao;

import domain.Usuario;
import dto.UsuarioDTO;
import exceptions.FracasoOperacion;

import java.io.IOException;


public interface UsuarioDAO {
    void CrearUsuario(Usuario usuario) throws FracasoOperacion;
    UsuarioDTO ObtenerUsuario(UsuarioDTO usuario) throws FracasoOperacion;
    void ModificarUsuario(String nombreUsuario, Usuario usuario) throws FracasoOperacion;
    void EliminarUsuario(String usuario) throws FracasoOperacion;
}

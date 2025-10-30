package service;

import dao.UsuarioCSV;
import dao.UsuarioDAO;
import dto.UsuarioDTO;
import domain.Usuario;
import java.io.IOException;

public class GestorUsuario {

    UsuarioDAO dao;

    public GestorUsuario() throws IOException {
        super();
        dao = new UsuarioCSV();
    }

    public void altaUsuario(String usuario, String contrasenna) {
        Usuario u = new Usuario(usuario, contrasenna);
        dao.CrearUsuario(u);
    }

    public boolean autenticar(String usuario, String contrasenna) {
        UsuarioDTO dto = new UsuarioDTO(usuario, contrasenna);
        try {
            return dao.ObtenerUsuario(dto) != null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void modificar(String usuario, String contrasenna) {
        Usuario u = new Usuario(usuario, contrasenna);
        dao.ModificarUsuario(usuario, u);
    }

    public void eliminar(String usuario) {
        dao.EliminarUsuario(usuario);
    }

}

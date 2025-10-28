package service;

import dao.UsuarioCSV;
import dto.UsuarioDTO;
import domain.Usuario;
import java.io.IOException;

public class GestorUsuario {
    private UsuarioCSV dao;

    public GestorUsuario() throws IOException {
        dao = new UsuarioCSV();
    }

    public void altaUsuario(String usuario, String contrasenna) {
        Usuario u = new Usuario(usuario, contrasenna);
        dao.CrearUsuario(u);
    }

    public boolean autenticar(String usuario, String contrasenna) throws IOException {
        UsuarioDTO dto = new UsuarioDTO(usuario, contrasenna);
        return dao.ObtenerUsuario(dto) != null;
    }

    public void modificar(String usuario, String contrasenna) {
        Usuario u = new Usuario(usuario, contrasenna);
        dao.ModificarUsuario(usuario, u);
    }

    public void eliminar(String usuario) {
        dao.EliminarUsuario(usuario);
    }

}

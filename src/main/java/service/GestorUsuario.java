package service;

import dao.UsuarioCSV;
import dao.UsuarioDAO;
import dto.UsuarioDTO;
import domain.Usuario;
import exceptions.FracasoOperacion;

import java.io.IOException;

public class GestorUsuario {

    private static GestorUsuario singleton_instance;
    UsuarioDAO dao;

    public GestorUsuario() throws FracasoOperacion {
        super();
        dao = new UsuarioCSV();
    }

    private synchronized static GestorUsuario getInstance() throws FracasoOperacion {
        if(singleton_instance == null) singleton_instance = new GestorUsuario();
        return singleton_instance;
    }

    public void altaUsuario(String usuario, String contrasenna) throws FracasoOperacion {
        Usuario u = new Usuario(usuario, contrasenna);
        dao.CrearUsuario(u);
    }

    public boolean autenticar(String usuario, String contrasenna) throws FracasoOperacion {
        UsuarioDTO dto = new UsuarioDTO(usuario, contrasenna);

        return dao.ObtenerUsuario(dto) != null;

    }

    public void modificar(String usuario, String contrasenna) throws FracasoOperacion {
        Usuario u = new Usuario(usuario, contrasenna);
        dao.ModificarUsuario(usuario, u);
    }

    public void eliminar(String usuario) throws FracasoOperacion {
        dao.EliminarUsuario(usuario);
    }

}

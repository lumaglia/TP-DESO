package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.UsuarioCSV;
import org.example.TP_DESO.dao.UsuarioDAO;
import org.example.TP_DESO.dao.UsuarioDAOMySQL;
import org.example.TP_DESO.dto.UsuarioDTO;
import org.example.TP_DESO.domain.Usuario;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestorUsuario {

    private static GestorUsuario singleton_instance;
    UsuarioDAO dao;


    public GestorUsuario() throws FracasoOperacion {
        super();
        dao = new UsuarioDAOMySQL();
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

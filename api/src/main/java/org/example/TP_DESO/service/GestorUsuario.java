package org.example.TP_DESO.service;

import org.example.TP_DESO.dao.UsuarioDAO;
import org.example.TP_DESO.dao.UsuarioDAOMySQL;
import org.example.TP_DESO.dto.UsuarioDTO;
import org.example.TP_DESO.domain.Usuario;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class GestorUsuario implements UserDetailsService {
    private static GestorUsuario singleton_instance;

    @Autowired
    private UsuarioDAOMySQL dao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public GestorUsuario() throws FracasoOperacion {
    }

    private synchronized static GestorUsuario getInstance() throws FracasoOperacion {
        if(singleton_instance == null) singleton_instance = new GestorUsuario();
        return singleton_instance;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            UsuarioDTO usuario = dao.ObtenerUsuario(new UsuarioDTO(username, ""));
            if(usuario == null) {
                throw new UsernameNotFoundException("Usuario no encontrado: " + username);
            }
            return User.builder()
                    .username(usuario.getUsuario())
                    .password(usuario.getContrasenna())
                    .roles("USER")
                    .build();
        } catch (FracasoOperacion e) {
            throw new RuntimeException(e);
        }
    }

    public void altaUsuario(String usuario, String contrasenna) throws FracasoOperacion {
        Usuario u = new Usuario(usuario, passwordEncoder.encode(contrasenna));
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

package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Usuario;
import org.example.TP_DESO.dto.UsuarioDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.example.TP_DESO.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioDAOMySQL implements UsuarioDAO {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public void CrearUsuario(Usuario usuario) throws FracasoOperacion {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuario.getUsuario());
            if(usuarioOpt.isPresent()) throw new FracasoOperacion("Usuario ya existente");
            usuarioRepository.save(usuario);
        } catch (Exception e) {
            throw new FracasoOperacion("Error al crear usuario: " + e.getMessage());
        }
    }

    @Override
    public UsuarioDTO ObtenerUsuario(UsuarioDTO usuarioDTO) throws FracasoOperacion {
        try {
            Optional<Usuario> usuarioOpt = usuarioRepository.findById(usuarioDTO.getUsuario());
            if (usuarioOpt.isPresent()) {
                Usuario u = usuarioOpt.get();
                return new UsuarioDTO(u.getUsuario(), u.getContrasenna());
            }
            return null;
        } catch (Exception e) {
            throw new FracasoOperacion("Error al obtener usuario: " + e.getMessage());
        }
    }

    @Override
    public void ModificarUsuario(String nombreUsuario, Usuario usuario) throws FracasoOperacion {
        try {
            Optional<Usuario> existente = usuarioRepository.findById(nombreUsuario);
            if (existente.isPresent()) {
                Usuario u = existente.get();
                u.setUsuario(usuario.getUsuario());
                u.setContrasenna(usuario.getContrasenna());
                usuarioRepository.save(u);
            } else {
                throw new FracasoOperacion("Usuario no encontrado: " + nombreUsuario);
            }
        } catch (Exception e) {
            throw new FracasoOperacion("Error al modificar usuario: " + e.getMessage());
        }
    }

    @Override
    public void EliminarUsuario(String nombreUsuario) throws FracasoOperacion {
        try {
            if (usuarioRepository.existsById(nombreUsuario)) {
                usuarioRepository.deleteById(nombreUsuario);
            } else {
                throw new FracasoOperacion("Usuario no encontrado: " + nombreUsuario);
            }
        } catch (Exception e) {
            throw new FracasoOperacion("Error al eliminar usuario: " + e.getMessage());
        }
    }
}

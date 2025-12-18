package org.example.TP_DESO.patterns.mappers;

import org.example.TP_DESO.domain.Usuario;
import org.example.TP_DESO.dto.UsuarioDTO;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(usuario.getUsuario(), usuario.getContrasenna());
    }

    public static Usuario toDomain(UsuarioDTO usuarioDTO) {
        return new Usuario(usuarioDTO.getUsuario(), usuarioDTO.getContrasenna());
    }

}

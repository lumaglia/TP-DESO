package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Usuario;
import org.example.TP_DESO.dto.UsuarioDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsuarioDAO {
    void CrearUsuario(Usuario usuario) throws FracasoOperacion;
    UsuarioDTO ObtenerUsuario(UsuarioDTO usuario) throws FracasoOperacion;
    void ModificarUsuario(String nombreUsuario, Usuario usuario) throws FracasoOperacion;
    void EliminarUsuario(String usuario) throws FracasoOperacion;
}

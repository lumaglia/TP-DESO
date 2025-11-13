package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Usuario;
import org.example.TP_DESO.dto.UsuarioDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class UsuarioDAOMySQL implements UsuarioDAO {

    @Autowired
    private DataSource dataSource;

    @Override
    public void CrearUsuario(Usuario usuario) throws FracasoOperacion {
        try {

            if (ObtenerUsuario(new UsuarioDTO(usuario.getUsuario(), usuario.getContrasenna())) != null) {
                throw new FracasoOperacion("El usuario ya existe");
            }

            String sql = "INSERT INTO usuario (username, password) VALUES (?, ?)";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, usuario.getUsuario());
                stmt.setString(2, usuario.getContrasenna());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new FracasoOperacion("Error al crear usuario: " + e.getMessage());
        }
    }

    @Override
    public UsuarioDTO ObtenerUsuario(UsuarioDTO usuario) throws FracasoOperacion {
        try {
            String sql = "SELECT * FROM usuario WHERE username = ? AND password = ?";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, usuario.getUsuario());
                stmt.setString(2, usuario.getContrasenna());

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        // Devolvemos los datos desde la base
                        return new UsuarioDTO(
                                rs.getString("username"),
                                rs.getString("password")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new FracasoOperacion("Error al obtener usuario: " + e.getMessage());
        }

        return null;
    }

    @Override
    public void ModificarUsuario(String nombreUsuario, Usuario usuario) throws FracasoOperacion {
        try {
            String sql = "UPDATE usuario SET username = ?, password = ? WHERE username = ?";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, usuario.getUsuario());
                stmt.setString(2, usuario.getContrasenna());
                stmt.setString(3, nombreUsuario);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected == 0) {
                    throw new FracasoOperacion("Usuario no encontrado: " + nombreUsuario);
                }
            }
        } catch (SQLException e) {
            throw new FracasoOperacion("Error al modificar usuario: " + e.getMessage());
        }
    }

    @Override
    public void EliminarUsuario(String usuario) throws FracasoOperacion {
        try {
            String sql = "DELETE FROM usuario WHERE username = ?";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, usuario);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected == 0) {
                    throw new FracasoOperacion("Usuario no encontrado: " + usuario);
                }
            }
        } catch (SQLException e) {
            throw new FracasoOperacion("Error al eliminar usuario: " + e.getMessage());
        }
    }
}

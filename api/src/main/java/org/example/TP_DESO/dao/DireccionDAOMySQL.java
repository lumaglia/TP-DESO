package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Direccion;
import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

@Repository
public class DireccionDAOMySQL implements DireccionDAO {

    @Autowired
    private DataSource dataSource;

    @Override
    public void crearDireccion(Direccion direccion) throws FracasoOperacion {
        try {
            // Verificar si ya existe
            if (obtenerDireccion(
                    direccion.getPais(),
                    direccion.getCodigoPostal(),
                    direccion.getDomicilio(),
                    direccion.getDepto()
            ) == null) {

                String sql = "INSERT INTO direccion (pais, codigo_postal, domicilio, depto, localidad, provincia) VALUES (?, ?, ?, ?, ?, ?)";

                try (Connection conn = dataSource.getConnection();
                     PreparedStatement stmt = conn.prepareStatement(sql)) {

                    stmt.setString(1, direccion.getPais());
                    stmt.setString(2, direccion.getCodigoPostal());
                    stmt.setString(3, direccion.getDomicilio());
                    stmt.setString(4, direccion.getDepto());
                    stmt.setString(5, direccion.getLocalidad());
                    stmt.setString(6, direccion.getProvincia());

                    stmt.executeUpdate();
                }
            }
        } catch (SQLException e) {
            throw new FracasoOperacion("Error al crear dirección: " + e.getMessage());
        }
    }

    @Override
    public DireccionDTO obtenerDireccion(String pais, String codigoPostal, String domicilio, String depto)
            throws FracasoOperacion {
        try {
            String sql = "SELECT * FROM direccion WHERE pais = ? AND codigo_postal = ? AND domicilio = ? " +
                    "AND (depto = ? OR (? IS NULL AND depto IS NULL))";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, pais);
                stmt.setString(2, codigoPostal);
                stmt.setString(3, domicilio);
                stmt.setString(4, depto);
                stmt.setString(5, depto);

                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        return new DireccionDTO(
                                rs.getString("domicilio"),
                                rs.getString("depto"),
                                rs.getString("codigo_postal"),
                                rs.getString("localidad"),
                                rs.getString("provincia"),
                                rs.getString("pais")
                        );
                    }
                }
            }
        } catch (SQLException e) {
            throw new FracasoOperacion("Error al obtener dirección: " + e.getMessage());
        }

        return null;
    }
}

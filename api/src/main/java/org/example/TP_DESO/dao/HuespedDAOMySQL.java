
package org.example.TP_DESO.dao;

import org.example.TP_DESO.domain.Huesped;
import org.example.TP_DESO.dto.DireccionDTO;
import org.example.TP_DESO.dto.HuespedDTO;
import org.example.TP_DESO.dto.HuespedDTOBuilder;
import org.example.TP_DESO.exceptions.FracasoOperacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;

@Repository
public class HuespedDAOMySQL implements HuespedDAO {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private DireccionDAOMySQL direccionDAO;

    @Override
    public void crearHuesped(Huesped huesped) throws FracasoOperacion {
        try {
            // Primero crear la dirección
            direccionDAO.crearDireccion(huesped.getDireccion());

            String sql = "INSERT INTO huesped (tipo_doc, nro_doc, apellido, nombre, cuil, posicion_iva, " +
                    "fecha_nac, telefono, email, ocupacion, nacionalidad, direccion_pais, direccion_codigo_postal, " +
                    "direccion_domicilio, direccion_depto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, huesped.getTipoDoc());
                stmt.setString(2, huesped.getNroDoc());
                stmt.setString(3, huesped.getApellido());
                stmt.setString(4, huesped.getNombre());
                stmt.setString(5, huesped.getCuil());
                stmt.setString(6, huesped.getPosicionIva());
                stmt.setDate(7, Date.valueOf(huesped.getFechaNac()));
                stmt.setString(8, huesped.getTelefono());
                stmt.setString(9, huesped.getEmail());
                stmt.setString(10, huesped.getOcupacion());
                stmt.setString(11, huesped.getNacionalidad());
                stmt.setString(12, huesped.getDireccion().getPais());
                stmt.setString(13, huesped.getDireccion().getCodigoPostal());
                stmt.setString(14, huesped.getDireccion().getDomicilio());
                stmt.setString(15, huesped.getDireccion().getDepto());

                stmt.executeUpdate();
            }
        } catch (SQLException e) {
            throw new FracasoOperacion("Error al crear huésped: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<HuespedDTO> obtenerHuesped(HuespedDTO huespedDTO) throws FracasoOperacion {
        ArrayList<HuespedDTO> huespedDTOs = new ArrayList<>();

        try {
            StringBuilder sql = new StringBuilder("SELECT * FROM huesped WHERE 1=1");
            ArrayList<Object> parametros = new ArrayList<>();

            // Construir consulta dinámica según los filtros (como el método comparar de CSV)
            if (huespedDTO.getTipoDoc() != null && !huespedDTO.getTipoDoc().isEmpty()) {
                sql.append(" AND tipo_doc = ?");
                parametros.add(huespedDTO.getTipoDoc());
            }
            if (huespedDTO.getNroDoc() != null && !huespedDTO.getNroDoc().isEmpty()) {
                sql.append(" AND nro_doc = ?");
                parametros.add(huespedDTO.getNroDoc());
            }
            if (huespedDTO.getApellido() != null && !huespedDTO.getApellido().isEmpty()) {
                sql.append(" AND apellido = ?");
                parametros.add(huespedDTO.getApellido());
            }
            if (huespedDTO.getNombre() != null && !huespedDTO.getNombre().isEmpty()) {
                sql.append(" AND nombre = ?");
                parametros.add(huespedDTO.getNombre());
            }
            if (huespedDTO.getCuil() != null && !huespedDTO.getCuil().isEmpty()) {
                sql.append(" AND cuil = ?");
                parametros.add(huespedDTO.getCuil());
            }
            if (huespedDTO.getPosicionIva() != null && !huespedDTO.getPosicionIva().isEmpty()) {
                sql.append(" AND posicion_iva = ?");
                parametros.add(huespedDTO.getPosicionIva());
            }
            if (huespedDTO.getFechaNac() != null) {
                sql.append(" AND fecha_nac = ?");
                parametros.add(Date.valueOf(huespedDTO.getFechaNac()));
            }
            if (huespedDTO.getTelefono() != null && !huespedDTO.getTelefono().isEmpty()) {
                sql.append(" AND telefono = ?");
                parametros.add(huespedDTO.getTelefono());
            }
            if (huespedDTO.getEmail() != null && !huespedDTO.getEmail().isEmpty()) {
                sql.append(" AND email = ?");
                parametros.add(huespedDTO.getEmail());
            }
            if (huespedDTO.getOcupacion() != null && !huespedDTO.getOcupacion().isEmpty()) {
                sql.append(" AND ocupacion = ?");
                parametros.add(huespedDTO.getOcupacion());
            }
            if (huespedDTO.getNacionalidad() != null && !huespedDTO.getNacionalidad().isEmpty()) {
                sql.append(" AND nacionalidad = ?");
                parametros.add(huespedDTO.getNacionalidad());
            }

            // Filtros de dirección
            if (huespedDTO.getDireccion() != null) {
                if (huespedDTO.getDireccion().getPais() != null && !huespedDTO.getDireccion().getPais().isEmpty()) {
                    sql.append(" AND direccion_pais = ?");
                    parametros.add(huespedDTO.getDireccion().getPais());
                }
                if (huespedDTO.getDireccion().getCodigoPostal() != null &&
                        !huespedDTO.getDireccion().getCodigoPostal().isEmpty()) {
                    sql.append(" AND direccion_codigo_postal = ?");
                    parametros.add(huespedDTO.getDireccion().getCodigoPostal());
                }
                if (huespedDTO.getDireccion().getDomicilio() != null &&
                        !huespedDTO.getDireccion().getDomicilio().isEmpty()) {
                    sql.append(" AND direccion_domicilio = ?");
                    parametros.add(huespedDTO.getDireccion().getDomicilio());
                }
                if (huespedDTO.getDireccion().getDepto() != null &&
                        !huespedDTO.getDireccion().getDepto().isEmpty()) {
                    sql.append(" AND direccion_depto = ?");
                    parametros.add(huespedDTO.getDireccion().getDepto());
                }
            }

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql.toString())) {

                // Establecer parámetros
                for (int i = 0; i < parametros.size(); i++) {
                    stmt.setObject(i + 1, parametros.get(i));
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        // Obtener dirección completa desde la tabla direccion
                        DireccionDTO direccionDTO = direccionDAO.obtenerDireccion(
                                rs.getString("direccion_pais"),
                                rs.getString("direccion_codigo_postal"),
                                rs.getString("direccion_domicilio"),
                                rs.getString("direccion_depto")
                        );

                        HuespedDTO dto = new HuespedDTOBuilder()
                                .setTipoDoc(rs.getString("tipo_doc"))
                                .setNroDoc(rs.getString("nro_doc"))
                                .setApellido(rs.getString("apellido"))
                                .setNombre(rs.getString("nombre"))
                                .setCuil(rs.getString("cuil"))
                                .setPosicionIva(rs.getString("posicion_iva"))
                                .setFechaNac(rs.getDate("fecha_nac").toLocalDate())
                                .setTelefono(rs.getString("telefono"))
                                .setEmail(rs.getString("email"))
                                .setOcupacion(rs.getString("ocupacion"))
                                .setNacionalidad(rs.getString("nacionalidad"))
                                .setDireccion(direccionDTO)
                                .createHuespedDTO();

                        huespedDTOs.add(dto);
                    }
                }
            }
        } catch (SQLException e) {
            throw new FracasoOperacion("Error al obtener huéspedes: " + e.getMessage());
        }

        return huespedDTOs;
    }

    @Override
    public void modificarHuesped(String tipoDoc, String numeroDoc, Huesped huesped) throws FracasoOperacion {
        try {
            // Crear dirección si no existe
            direccionDAO.crearDireccion(huesped.getDireccion());

            String sql = "UPDATE huesped SET tipo_doc = ?, nro_doc = ?, apellido = ?, nombre = ?, cuil = ?, " +
                    "posicion_iva = ?, fecha_nac = ?, telefono = ?, email = ?, ocupacion = ?, nacionalidad = ?, " +
                    "direccion_pais = ?, direccion_codigo_postal = ?, direccion_domicilio = ?, direccion_depto = ? " +
                    "WHERE tipo_doc = ? AND nro_doc = ?";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, huesped.getTipoDoc());
                stmt.setString(2, huesped.getNroDoc());
                stmt.setString(3, huesped.getApellido());
                stmt.setString(4, huesped.getNombre());
                stmt.setString(5, huesped.getCuil());
                stmt.setString(6, huesped.getPosicionIva());
                stmt.setDate(7, Date.valueOf(huesped.getFechaNac()));
                stmt.setString(8, huesped.getTelefono());
                stmt.setString(9, huesped.getEmail());
                stmt.setString(10, huesped.getOcupacion());
                stmt.setString(11, huesped.getNacionalidad());
                stmt.setString(12, huesped.getDireccion().getPais());
                stmt.setString(13, huesped.getDireccion().getCodigoPostal());
                stmt.setString(14, huesped.getDireccion().getDomicilio());
                stmt.setString(15, huesped.getDireccion().getDepto());
                stmt.setString(16, tipoDoc);
                stmt.setString(17, numeroDoc);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected == 0) {
                    throw new FracasoOperacion("Huésped no encontrado con tipoDoc: " + tipoDoc + " y nroDoc: " + numeroDoc);
                }
            }
        } catch (SQLException e) {
            throw new FracasoOperacion("Error al modificar huésped: " + e.getMessage());
        }
    }

    @Override
    public void eliminarHuesped(String tipoDoc, String numeroDoc) throws FracasoOperacion {
        try {
            String sql = "DELETE FROM huesped WHERE tipo_doc = ? AND nro_doc = ?";

            try (Connection conn = dataSource.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, tipoDoc);
                stmt.setString(2, numeroDoc);

                int rowsAffected = stmt.executeUpdate();

                if (rowsAffected == 0) {
                    throw new FracasoOperacion("Huésped no encontrado con tipoDoc: " + tipoDoc + " y nroDoc: " + numeroDoc);
                }
            }
        } catch (SQLException e) {
            throw new FracasoOperacion("Error al eliminar huésped: " + e.getMessage());
        }
    }
}
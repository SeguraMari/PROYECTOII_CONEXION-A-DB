package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
//import model.DataBaseConnection;
import pojo.Beneficios;

/**
 *
 * @author Graciela
 */
public class BeneficiosModel {

    public DefaultTableModel mostrarBeneficios() {

        String[] titulos = {"ID", "Nombre", "Descripción", "Monto"};

        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return modelo;
        }

        try {

            String sql = "SELECT * FROM Beneficios";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {

                String[] registro = new String[4];

                registro[0] = resultado.getString("idBeneficio");
                registro[1] = resultado.getString("nomBeneficio");
                registro[2] = resultado.getString("descripcion");
                registro[3] = resultado.getString("montoBeneficio");

                modelo.addRow(registro);
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException e) {

            System.out.println("Error al mostrar beneficios: " + e.getMessage());

        }

        return modelo;
    }

    public boolean insertarBeneficio(Beneficios beneficio) {

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return false;
        }

        try {

            String sql = "INSERT INTO Beneficios (nomBeneficio, descripcion, montoBeneficio) VALUES (?, ?, ?)";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, beneficio.getNomBeneficio());
            consulta.setString(2, beneficio.getDescripcion());
            consulta.setDouble(3, beneficio.getMontoBeneficio());

            int resultado = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println("Error al insertar beneficio: " + e.getMessage());
            return false;

        }

    }

    public boolean modificarBeneficio(Beneficios beneficio) {

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return false;
        }

        try {

            String sql = "UPDATE Beneficios SET nomBeneficio = ?, descripcion = ?, montoBeneficio = ? WHERE idBeneficio = ?";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, beneficio.getNomBeneficio());
            consulta.setString(2, beneficio.getDescripcion());
            consulta.setDouble(3, beneficio.getMontoBeneficio());
            consulta.setInt(4, beneficio.getIdBeneficio());

            int resultado = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println("Error al modificar beneficio: " + e.getMessage());
            return false;

        }

    }

    public boolean eliminarBeneficio(int idBeneficio) {

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return false;
        }

        try {

            String sql = "DELETE FROM Beneficios WHERE idBeneficio = ?";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setInt(1, idBeneficio);

            int resultado = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println("Error al eliminar beneficio: " + e.getMessage());
            return false;

        }

    }

}

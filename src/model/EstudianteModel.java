package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import pojo.Estudiante;

/**
 *
 * @author Graciela
 */
public class EstudianteModel {

    public DefaultTableModel mostrarEstudiantes() {

        String[] titulos = {
            "Carnet", "Cédula", "Nombre", "Fecha Nacimiento",
            "Dirección", "Teléfono", "Email",
            "Fecha Ingreso", "Fecha Egreso", "ID Carrera"
        };

        DefaultTableModel modelo = new DefaultTableModel(null, titulos);

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return modelo;
        }

        try {

            String sql = "SELECT * FROM Estudiantes";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {

                String[] registro = new String[10];

                registro[0] = resultado.getString("carnet");
                registro[1] = resultado.getString("cedula");
                registro[2] = resultado.getString("nombre");
                registro[3] = resultado.getString("fechNac");
                registro[4] = resultado.getString("direccion");
                registro[5] = resultado.getString("telefono");
                registro[6] = resultado.getString("email");
                registro[7] = resultado.getString("fechIngreso");
                registro[8] = resultado.getString("fechEgreso");
                registro[9] = resultado.getString("idCarrera");

                modelo.addRow(registro);
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException e) {

            System.out.println("Error al mostrar estudiantes: " + e.getMessage());

        }

        return modelo;
    }

    public boolean insertarEstudiante(Estudiante estudiante) {

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return false;
        }

        try {

            String sql = "INSERT INTO Estudiantes (carnet, cedula, nombre, fechNac, direccion, telefono, email, fechIngreso, fechEgreso, idCarrera) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, estudiante.getCarnet());
            consulta.setString(2, estudiante.getCedula());
            consulta.setString(3, estudiante.getNombre());

            consulta.setDate(4, new java.sql.Date(estudiante.getFechNac().getTime()));

            consulta.setString(5, estudiante.getDireccion());
            consulta.setString(6, estudiante.getTelefono());
            consulta.setString(7, estudiante.getEmail());

            consulta.setDate(8, new java.sql.Date(estudiante.getFechIngreso().getTime()));

            if (estudiante.getFechEgreso() != null) {
                consulta.setDate(9, new java.sql.Date(estudiante.getFechEgreso().getTime()));
            } else {
                consulta.setDate(9, null);
            }

            consulta.setInt(10, estudiante.getCarrera());

            int resultado = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println("Error al insertar estudiante: " + e.getMessage());
            return false;

        }

    }

    public boolean modificarEstudiante(Estudiante estudiante) {

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return false;
        }

        try {

            String sql = "UPDATE Estudiantes SET cedula = ?, nombre = ?, fechNac = ?, direccion = ?, telefono = ?, email = ?, fechIngreso = ?, fechEgreso = ?, idCarrera = ? WHERE carnet = ?";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, estudiante.getCedula());
            consulta.setString(2, estudiante.getNombre());

            consulta.setDate(3, new java.sql.Date(estudiante.getFechNac().getTime()));

            consulta.setString(4, estudiante.getDireccion());
            consulta.setString(5, estudiante.getTelefono());
            consulta.setString(6, estudiante.getEmail());

            consulta.setDate(7, new java.sql.Date(estudiante.getFechIngreso().getTime()));

            if (estudiante.getFechEgreso() != null) {
                consulta.setDate(8, new java.sql.Date(estudiante.getFechEgreso().getTime()));
            } else {
                consulta.setDate(8, null);
            }

            consulta.setInt(9, estudiante.getCarrera());
            consulta.setString(10, estudiante.getCarnet());

            int resultado = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println("Error al modificar estudiante: " + e.getMessage());
            return false;

        }

    }

    public boolean eliminarEstudiante(String carnet) {

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return false;
        }

        try {

            String sql = "DELETE FROM Estudiantes WHERE carnet = ?";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, carnet);

            int resultado = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println("Error al eliminar estudiante: " + e.getMessage());
            return false;

        }
    }

}

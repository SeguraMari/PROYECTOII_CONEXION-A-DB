package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pojo.Carreras;

/**
 *
 * @author Graciela
 */
public class CarrerasModel {

    public ArrayList<Carreras> mostrarCarreras() {

        ArrayList<Carreras> lista = new ArrayList<>();

        Connection conexion = DataBaseConnection.getConnection();

        try {

            String sql = "SELECT * FROM Carreras";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {

                Carreras carrera = new Carreras();

                carrera.setIdCarrera(resultado.getInt("idCarrera"));
                carrera.setNomCarrera(resultado.getString("nomCarrera"));
                carrera.setGrado(resultado.getString("grado"));

                lista.add(carrera);
            }

            consulta.close();
            resultado.close();
            conexion.close();

        } catch (SQLException e) {

            System.out.println("Error al mostrar carreras: " + e.getMessage());

        }

        return lista;

    }

    public boolean insertarCarrera(Carreras carrera) {

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return false;
        }

        try {

            String sql = "INSERT INTO Carreras (nomCarrera, grado) VALUES (?, ?)";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, carrera.getNomCarrera());
            consulta.setString(2, carrera.getGrado());

            int resultado = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println("Error al insertar carrera: " + e.getMessage());

            return false;
        }

    }

    public boolean modifcarCarrera(Carreras carrera) {

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return false;
        }

        try {

            String sql = "UPDATE Carreras SET nomCarrera = ?, grado = ? WHERE idCarrera = ?";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, carrera.getNomCarrera());
            consulta.setString(2, carrera.getGrado());
            consulta.setInt(3, carrera.getIdCarrera());

            int resultado = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println("Error al modificar carrera: " + e.getMessage());

            return false;
        }

    }

    public boolean eliminarCarrera(int idCarrera) {

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return false;
        }

        try {
            String sql = "DELETE FROM Carreras WHERE idCarrera = ?";

            PreparedStatement consulta = conexion.prepareStatement(sql);
            consulta.setInt(1, idCarrera);

            int resultado = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return resultado > 0;

        } catch (SQLException e) {
            System.out.println("Error al eliminar carrera: " + e.getMessage());
            return false;
        }

    }
    
    

}

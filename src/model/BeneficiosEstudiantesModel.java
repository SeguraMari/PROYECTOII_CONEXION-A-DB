package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pojo.BeneficiosEstudiantes;

/**
 *
 * @author Graciela
 */
public class BeneficiosEstudiantesModel {

    public ArrayList<BeneficiosEstudiantes> mostrarBeneficiosEstudiante(String cedula) {

        ArrayList<BeneficiosEstudiantes> lista = new ArrayList<>();

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return lista;
        }

        try {

            String sql = "SELECT * FROM BeneficiosEstudiantes WHERE cedula = ?";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, cedula);

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {

                BeneficiosEstudiantes beneficioEstudiante = new BeneficiosEstudiantes();

                beneficioEstudiante.setIdBeneficioEst(resultado.getInt("idBeneficioEstud"));
                beneficioEstudiante.setCedula(resultado.getString("cedula"));
                beneficioEstudiante.setIdBeneficio(resultado.getInt("idBeneficio"));

                lista.add(beneficioEstudiante);
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException e) {

            System.out.println("Error al mostrar beneficios del estudiante: " + e.getMessage());

        }

        return lista;

    }

    public boolean insertarBeneficioEstudiante(BeneficiosEstudiantes benefitEst) {

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return false;
        }

        try {

            String sql = "INSERT INTO BeneficiosEstudiantes (cedula, idBeneficio) VALUES (?, ?)";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, benefitEst.getCedula());
            consulta.setInt(2, benefitEst.getIdBeneficio());

            int resultado = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println("Error al asignar beneficio al estudiante: " + e.getMessage());
            return false;

        }

    }

    public boolean eliminarBeneficioEstudiante(int idBeneficioEst) {

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return false;
        }

        try {

            String sql = "DELETE FROM BeneficiosEstudiantes WHERE idBeneficioEstud = ?";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setInt(1, idBeneficioEst);

            int resultado = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println("Error al eliminar beneficio del estudiante: " + e.getMessage());
            return false;

        }
    }

}

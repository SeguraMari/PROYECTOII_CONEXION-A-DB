package proyecto2_mvc;

import java.sql.Connection;
import static model.DataBaseConnection.getConnection;
import java.sql.SQLException;

/**
 *
 * @author Graciela
 */
public class ProyectoII_Fiorella_Mariana_DB {

    public static void main(String[] args) {

        Connection conexion = getConnection();

        if (conexion != null) {

            System.out.println("La prueba de conexión fue exitosa.");

            try {
                conexion.close();
                System.out.println("Conexión cerrada correctamente.");

            } catch (SQLException e) {
                System.out.println("Error al cerrar la conexión: " + e.getMessage());
            }

        } else {

            System.out.println("No se pudo conectar a Proyecto2.");

        }

    }

}

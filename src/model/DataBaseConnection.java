package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Graciela
 */
public class DataBaseConnection {

    public static Connection getConnection() {

        Connection conexion = null;

        String url = "jdbc:mysql://localhost:3306/Proyecto2";
        String usuario = "root";
        String password = "patitos1234";

        try {

            conexion = DriverManager.getConnection(url, usuario, password);

            System.out.println("Conexión exitosa a Proyecto2");

        } catch (SQLException e) {

            System.out.println("Error al conectar con la base de datos: " + e.getMessage());

        }

        return conexion;
    }
}

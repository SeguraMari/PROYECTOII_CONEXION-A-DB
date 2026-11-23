package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import pojo.PagosMensuales;

/**
 *
 * @author Graciela
 */
public class PagosMensualesModel {

    public ArrayList<PagosMensuales> mostrarPagosMensuales() {

        ArrayList<PagosMensuales> lista = new ArrayList<>();

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return lista;
        }

        try {

            String sql = "SELECT * FROM PagosMensuales";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {

                PagosMensuales pago = new PagosMensuales();

                pago.setIdPago(resultado.getInt("idPago"));
                pago.setFechCreacion(resultado.getDate("fechCreacion"));
                pago.setMes(resultado.getString("mes"));
                pago.setAnio(resultado.getInt("anio"));
                pago.setFechPago(resultado.getDate("fechaPago"));
                pago.setEstudiante(resultado.getString("estudiante"));
                pago.setTotalBenefits(resultado.getDouble("totalBeneficios"));
                pago.setDeducSeguro(resultado.getDouble("deducSeguro"));
                pago.setDeducRenta(resultado.getDouble("deducRenta"));
                pago.setPagoNeto(resultado.getDouble("pagoNeto"));

                lista.add(pago);
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException e) {

            System.out.println("Error al mostrar pagos mensuales: " + e.getMessage());

        }

        return lista;

    }

    public boolean insertarPagoMen(PagosMensuales pago) {

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return false;
        }

        try {

            String sql = "INSERT INTO PagosMensuales (fechCreacion, mes, anio, fechaPago, estudiante, totalBeneficios, deducSeguro, deducRenta, pagoNeto) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setDate(1, new java.sql.Date(pago.getFechCreacion().getTime()));
            consulta.setString(2, pago.getMes());
            consulta.setInt(3, pago.getAnio());

            if (pago.getFechPago() != null) {
                consulta.setDate(4, new java.sql.Date(pago.getFechPago().getTime()));
            } else {
                consulta.setDate(4, null);
            }

            consulta.setString(5, pago.getEstudiante());
            consulta.setDouble(6, pago.getTotalBenefits());
            consulta.setDouble(7, pago.getDeducSeguro());
            consulta.setDouble(8, pago.getDeducRenta());
            consulta.setDouble(9, pago.getPagoNeto());

            int resultado = consulta.executeUpdate();

            consulta.close();
            conexion.close();

            return resultado > 0;

        } catch (SQLException e) {

            System.out.println("Error al insertar pago mensual: " + e.getMessage());
            return false;

        }

    }

    public ArrayList<PagosMensuales> buscarPagosMensuales(String estudiante, String mes, int anio) {

        ArrayList<PagosMensuales> lista = new ArrayList<>();

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return lista;
        }

        try {

            String sql = "SELECT * FROM PagosMensuales "
                    + "WHERE (? = '' OR estudiante = ?) "
                    + "AND (? = '' OR mes = ?) "
                    + "AND (? = 0 OR anio = ?)";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, estudiante);
            consulta.setString(2, estudiante);

            consulta.setString(3, mes);
            consulta.setString(4, mes);

            consulta.setInt(5, anio);
            consulta.setInt(6, anio);

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {

                PagosMensuales pago = new PagosMensuales();

                pago.setIdPago(resultado.getInt("idPago"));
                pago.setFechCreacion(resultado.getDate("fechCreacion"));
                pago.setMes(resultado.getString("mes"));
                pago.setAnio(resultado.getInt("anio"));
                pago.setFechPago(resultado.getDate("fechaPago"));
                pago.setEstudiante(resultado.getString("estudiante"));
                pago.setTotalBenefits(resultado.getDouble("totalBeneficios"));
                pago.setDeducSeguro(resultado.getDouble("deducSeguro"));
                pago.setDeducRenta(resultado.getDouble("deducRenta"));
                pago.setPagoNeto(resultado.getDouble("pagoNeto"));

                lista.add(pago);
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException e) {

            System.out.println("Error al buscar pagos mensuales: " + e.getMessage());

        }

        return lista;

    }

    public double calcularTotalBenefits(String cedula) {

        double total = 0;

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return total;
        }

        try {

            String sql = "SELECT SUM(b.montoBeneficio) AS total "
                    + "FROM Beneficios b "
                    + "INNER JOIN BeneficiosEstudiantes be "
                    + "ON b.idBeneficio = be.idBeneficio "
                    + "WHERE be.cedula = ?";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, cedula);

            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                total = resultado.getDouble("total");
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException e) {

            System.out.println("Error al calcular los beneficios: " + e.getMessage());

        }

        return total;

    }

    public double calcularDeducSeguro(double totalBenefits) {

        double deduccion = totalBenefits * 0.10;

        return deduccion;
    }

    public double calcularDeducRenta(double totalBenefit) {

        double deduccion = totalBenefit * 0.05;

        return deduccion;
    }

    public double calcularPagoNeto(double totalBenefit, double deducSeguro, double deducRenta) {

        double pagoNeto = totalBenefit - deducSeguro - deducRenta;

        return pagoNeto;

    }

    public boolean existePagoMensual(String cedula, String mes, int anio) {

        boolean existe = false;

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return false;
        }

        try {

            String sql = "SELECT * FROM PagosMensuales WHERE estudiante = ? AND mes = ? AND anio = ?";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, cedula);
            consulta.setString(2, mes);
            consulta.setInt(3, anio);

            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                existe = true;
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException e) {

            System.out.println("Error al verificar el pago mensual: " + e.getMessage());

        }

        return existe;
    }

    public boolean generarPagoMensual(String cedula, String mes, int anio) {

        if (!validarPeriodo(mes, anio)) {
            System.out.println("No se pueden generar pagos para meses anteriores al actual.");
            return false;
        }

        if (existePagoMensual(cedula, mes, anio)) {
            System.out.println("El pago de este estudiante ya fue generado.");
            return false;
        }

        double totalBeneficios = calcularTotalBenefits(cedula);

        double deducSeguro = calcularDeducSeguro(totalBeneficios);

        double deducRenta = calcularDeducRenta(totalBeneficios);

        double pagoNeto = calcularPagoNeto(totalBeneficios, deducSeguro, deducRenta);

        PagosMensuales pago = new PagosMensuales();

        pago.setFechCreacion(new java.util.Date());
        pago.setMes(mes);
        pago.setAnio(anio);
        pago.setFechPago(null);
        pago.setEstudiante(cedula);
        pago.setTotalBenefits(totalBeneficios);
        pago.setDeducSeguro(deducSeguro);
        pago.setDeducRenta(deducRenta);
        pago.setPagoNeto(pagoNeto);

        return insertarPagoMen(pago);
    }

    public int generarPlanilla(String mes, int anio) {

        int cantidadGenerada = 0;

        if (!validarPeriodo(mes, anio)) {
            System.out.println("No se puede generar la planilla para este período.");
            return cantidadGenerada;
        }

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return cantidadGenerada;
        }

        try {

            String sql = "SELECT cedula FROM Estudiantes";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            ResultSet resultado = consulta.executeQuery();

            while (resultado.next()) {

                String cedula = resultado.getString("cedula");

                if (generarPagoMensual(cedula, mes, anio)) {
                    cantidadGenerada++;
                }
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException e) {

            System.out.println("Error al generar la planilla: " + e.getMessage());

        }

        return cantidadGenerada;

    }

    public double calcularTotalPlanilla(String mes, int anio) {

        double total = 0;

        Connection conexion = DataBaseConnection.getConnection();

        if (conexion == null) {
            return total;
        }

        try {

            String sql = "SELECT SUM(pagoNeto) AS total "
                    + "FROM PagosMensuales "
                    + "WHERE mes = ? AND anio = ?";

            PreparedStatement consulta = conexion.prepareStatement(sql);

            consulta.setString(1, mes);
            consulta.setInt(2, anio);

            ResultSet resultado = consulta.executeQuery();

            if (resultado.next()) {
                total = resultado.getDouble("total");
            }

            resultado.close();
            consulta.close();
            conexion.close();

        } catch (SQLException e) {

            System.out.println("Error al calcular el total de la planilla: " + e.getMessage());

        }

        return total;

    }

    public boolean validarPeriodo(String mes, int anio) {

        java.util.Calendar fechaActual = java.util.Calendar.getInstance();

        int anioActual = fechaActual.get(java.util.Calendar.YEAR);
        int mesActual = fechaActual.get(java.util.Calendar.MONTH) + 1;

        int numeroMes = 0;

        switch (mes) {
            case "Enero":
                numeroMes = 1;
                break;
            case "Febrero":
                numeroMes = 2;
                break;
            case "Marzo":
                numeroMes = 3;
                break;
            case "Abril":
                numeroMes = 4;
                break;
            case "Mayo":
                numeroMes = 5;
                break;
            case "Junio":
                numeroMes = 6;
                break;
            case "Julio":
                numeroMes = 7;
                break;
            case "Agosto":
                numeroMes = 8;
                break;
            case "Septiembre":
                numeroMes = 9;
                break;
            case "Octubre":
                numeroMes = 10;
                break;
            case "Noviembre":
                numeroMes = 11;
                break;
            case "Diciembre":
                numeroMes = 12;
                break;
        }

        if (anio < anioActual) {
            return false;
        }

        if (anio == anioActual && numeroMes < mesActual) {
            return false;
        }

        if (numeroMes == 0) {
            return false;
        }

        return true;

    }

}

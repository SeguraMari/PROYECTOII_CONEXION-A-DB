package pojo;

import java.util.Date;

/**
 *
 * @author Graciela
 */
public class PagosMensuales {

    private int idPago;
    private Date fechCreacion;
    private String mes;
    private int anio;
    private Date fechPago;
    private String estudiante;
    private double totalBenefits;
    private double deducSeguro;
    private double deducRenta;
    private double pagoNeto;

    public PagosMensuales() {
    }

    public PagosMensuales(int idPago, Date fechCreacion, String mes, int anio, Date fechPago, String estudiante, double totalBenefits, double deducSeguro, double deducRenta, double pagoNeto) {
        this.idPago = idPago;
        this.fechCreacion = fechCreacion;
        this.mes = mes;
        this.anio = anio;
        this.fechPago = fechPago;
        this.estudiante = estudiante;
        this.totalBenefits = totalBenefits;
        this.deducSeguro = deducSeguro;
        this.deducRenta = deducRenta;
        this.pagoNeto = pagoNeto;
    }

    public int getIdPago() {
        return idPago;
    }

    public void setIdPago(int idPago) {
        this.idPago = idPago;
    }

    public Date getFechCreacion() {
        return fechCreacion;
    }

    public void setFechCreacion(Date fechCreacion) {
        this.fechCreacion = fechCreacion;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    public Date getFechPago() {
        return fechPago;
    }

    public void setFechPago(Date fechPago) {
        this.fechPago = fechPago;
    }

    public String getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(String estudiante) {
        this.estudiante = estudiante;
    }

    public double getTotalBenefits() {
        return totalBenefits;
    }

    public void setTotalBenefits(double totalBenefits) {
        this.totalBenefits = totalBenefits;
    }

    public double getDeducSeguro() {
        return deducSeguro;
    }

    public void setDeducSeguro(double deducSeguro) {
        this.deducSeguro = deducSeguro;
    }

    public double getDeducRenta() {
        return deducRenta;
    }

    public void setDeducRenta(double deducRenta) {
        this.deducRenta = deducRenta;
    }

    public double getPagoNeto() {
        return pagoNeto;
    }

    public void setPagoNeto(double pagoNeto) {
        this.pagoNeto = pagoNeto;
    }

}

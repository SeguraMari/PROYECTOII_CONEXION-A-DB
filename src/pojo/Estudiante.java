
package pojo;

import java.util.Date;

/**
 *
 * @author Graciela
 */
public class Estudiante extends Persona{
    
    private String carnet;
    private Date fechIngreso;
    private Date fechEgreso;
    private int carrera;

    public Estudiante() {
    }

    public Estudiante(String carnet, Date fechIngreso, Date fechEgreso, int carrera) {
        this.carnet = carnet;
        this.fechIngreso = fechIngreso;
        this.fechEgreso = fechEgreso;
        this.carrera = carrera;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public Date getFechIngreso() {
        return fechIngreso;
    }

    public void setFechIngreso(Date fechIngreso) {
        this.fechIngreso = fechIngreso;
    }

    public Date getFechEgreso() {
        return fechEgreso;
    }

    public void setFechEgreso(Date fechEgreso) {
        this.fechEgreso = fechEgreso;
    }

    public int getCarrera() {
        return carrera;
    }

    public void setCarrera(int carrera) {
        this.carrera = carrera;
    }
    
    
  
}

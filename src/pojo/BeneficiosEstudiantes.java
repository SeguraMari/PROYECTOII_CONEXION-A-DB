
package pojo;

/**
 *
 * @author Graciela
 */
public class BeneficiosEstudiantes {
    
    private int idBeneficioEst;
    private String cedula;
    private int idBeneficio;

    public BeneficiosEstudiantes() {
    }

    public BeneficiosEstudiantes(int idBeneficioEst, String cedula, int idBeneficio) {
        this.idBeneficioEst = idBeneficioEst;
        this.cedula = cedula;
        this.idBeneficio = idBeneficio;
    }

    public int getIdBeneficioEst() {
        return idBeneficioEst;
    }

    public void setIdBeneficioEst(int idBeneficioEst) {
        this.idBeneficioEst = idBeneficioEst;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public int getIdBeneficio() {
        return idBeneficio;
    }

    public void setIdBeneficio(int idBeneficio) {
        this.idBeneficio = idBeneficio;
    }
    
    
    
    
}

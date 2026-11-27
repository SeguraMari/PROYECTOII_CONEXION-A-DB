package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.BeneficiosEstudiantesModel;
import model.BeneficiosModel;
import model.EstudianteModel;
import pojo.BeneficiosEstudiantes;
import view.DlgAsignarBeneficio;
import pojo.Estudiante;

/**
 *
 * @author Graciela
 */
public class AsignarBeneficioController implements ActionListener {

    private Estudiante estudiante;
    private EstudianteModel estModel;
    private BeneficiosEstudiantesModel beneEstModel;
    private DlgAsignarBeneficio dlgA;

    private DefaultListModel<String> modeloDisponibles;
    private DefaultListModel<String> modeloAsignados;

    public AsignarBeneficioController(Estudiante estudiante,
            EstudianteModel estModel,
            BeneficiosEstudiantesModel beneEstModel,
            DlgAsignarBeneficio dlgA) {

        this.estudiante = estudiante;
        this.estModel = estModel;
        this.beneEstModel = beneEstModel;
        this.dlgA = dlgA;

        this.dlgA.btnBuscar.addActionListener(this);
        this.dlgA.btnAgregar.addActionListener(this);
        this.dlgA.btnQuitar.addActionListener(this);

        cargarBeneficiosDisponibles();

    }

    /**
     * Este metodo busca el nombre completo y obtiene la cedula del estudiante
     * encontrado.
     */
    public void buscarEstudiante() {

        //Cada busqueda comienza sin una ced anterior
        estudiante.setCedula(null);

        DefaultTableModel modelo = estModel.mostrarEstudiantes();

        String nombreBuscar = dlgA.txtEstudiante.getText().trim();

        boolean encontrado = false;

        for (int i = 0; i < modelo.getRowCount(); i++) {

            String nombre = modelo.getValueAt(i, 2).toString();

            if (nombre.equalsIgnoreCase(nombreBuscar)) {

                estudiante.setCedula(modelo.getValueAt(i, 1).toString());

                encontrado = true;
                break;
            }
        }

        if (encontrado) {
            JOptionPane.showMessageDialog(dlgA, "Estudiante encontrado.");
        } else {
            JOptionPane.showMessageDialog(dlgA, "No se encontró el estudiante.");
        }

    }

    /**
     * Muestra los beneficios asignados al Est encontrado.
     */
    public void cargarBeneficios() {

        modeloAsignados = new DefaultListModel<>();

        dlgA.lstBenefitAsig.setModel(modeloAsignados);

        ArrayList<BeneficiosEstudiantes> listaAsignados
                = beneEstModel.mostrarBeneficiosEstudiante(estudiante.getCedula());

        BeneficiosModel beneModel = new BeneficiosModel();

        DefaultTableModel modeloBeneficios = beneModel.mostrarBeneficios();

        for (int i = 0; i < listaAsignados.size(); i++) {

            int idBeneficio = listaAsignados.get(i).getIdBeneficio();

            for (int j = 0; j < modeloBeneficios.getRowCount(); j++) {

                int id = Integer.parseInt(
                        modeloBeneficios.getValueAt(j, 0).toString());

                if (id == idBeneficio) {

                    String nombre = modeloBeneficios.getValueAt(j, 1).toString();

                    modeloAsignados.addElement(nombre);

                    break;
                }
            }
        }

    }

    /**
     * Este metodo muestra los beneficios disponibles, aun cuando no se busque
     * un Est.
     */
    public void cargarBeneficiosDisponibles() {

        modeloDisponibles = new DefaultListModel<>();

        dlgA.lstBenefitDisp.setModel(modeloDisponibles);

        BeneficiosModel beneModel = new BeneficiosModel();

        DefaultTableModel modeloBeneficios = beneModel.mostrarBeneficios();

        for (int i = 0; i < modeloBeneficios.getRowCount(); i++) {

            String nombreBeneficio = modeloBeneficios.getValueAt(i, 1).toString();

            modeloDisponibles.addElement(nombreBeneficio);
        }

    }

    /**
     * Este metodo toma el beneficio seleccionado busca su ID y lo guarda en la
     * tabla BeneficiosEstudiantes
     */
    public void agregarBeneficio() {

        if (estudiante.getCedula() == null) {
            JOptionPane.showMessageDialog(dlgA, "Primero debes buscar un estudiante.");
            return;
        }

        String nombreBeneficio = dlgA.lstBenefitDisp.getSelectedValue();

        if (nombreBeneficio == null) {
            JOptionPane.showMessageDialog(dlgA, "Selecciona un beneficio disponible.");
            return;
        }

        BeneficiosModel beneModel = new BeneficiosModel();

        DefaultTableModel modelo = beneModel.mostrarBeneficios();

        for (int i = 0; i < modelo.getRowCount(); i++) {

            String nombre = modelo.getValueAt(i, 1).toString();

            if (nombre.equals(nombreBeneficio)) {

                int idBeneficio = Integer.parseInt(
                        modelo.getValueAt(i, 0).toString());

                BeneficiosEstudiantes beneficioEstudiante
                        = new BeneficiosEstudiantes();

                beneficioEstudiante.setCedula(estudiante.getCedula());
                beneficioEstudiante.setIdBeneficio(idBeneficio);

                if (beneEstModel.insertarBeneficioEstudiante(beneficioEstudiante)) {

                    JOptionPane.showMessageDialog(dlgA, "Beneficio asignado correctamente.");

                    cargarBeneficios();
                    cargarBeneficiosDisponibles();

                } else {

                    JOptionPane.showMessageDialog(dlgA, "No se pudo asignar el beneficio.");

                }

                break;
            }
        }

    }

    public void quitarBeneficio() {

        if (estudiante.getCedula() == null) {
            JOptionPane.showMessageDialog(dlgA, "Primero debes buscar un estudiante.");
            return;
        }

        String nombreBeneficio = dlgA.lstBenefitAsig.getSelectedValue();

        if (nombreBeneficio == null) {
            JOptionPane.showMessageDialog(dlgA, "Selecciona un beneficio asignado.");
            return;
        }

        ArrayList<BeneficiosEstudiantes> listaAsignados
                = beneEstModel.mostrarBeneficiosEstudiante(estudiante.getCedula());

        BeneficiosModel beneModel = new BeneficiosModel();

        DefaultTableModel modelo = beneModel.mostrarBeneficios();

        for (int i = 0; i < listaAsignados.size(); i++) {

            int idBeneficio = listaAsignados.get(i).getIdBeneficio();

            for (int j = 0; j < modelo.getRowCount(); j++) {

                int id = Integer.parseInt(modelo.getValueAt(j, 0).toString());

                String nombre = modelo.getValueAt(j, 1).toString();

                if (id == idBeneficio && nombre.equals(nombreBeneficio)) {

                    int idBeneficioEst = listaAsignados.get(i).getIdBeneficioEst();

                    if (beneEstModel.eliminarBeneficioEstudiante(idBeneficioEst)) {

                        JOptionPane.showMessageDialog(dlgA, "Beneficio eliminado correctamente.");

                        cargarBeneficios();
                        cargarBeneficiosDisponibles();

                    } else {

                        JOptionPane.showMessageDialog(dlgA, "No se pudo eliminar el beneficio.");

                    }

                    return;
                }
            }
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Botón Buscar
        if (e.getSource() == dlgA.btnBuscar) {

            buscarEstudiante();

            if (estudiante.getCedula() != null) {
                cargarBeneficios();
            }

        }

        //boton Agregar
        if (e.getSource() == dlgA.btnAgregar) {

            agregarBeneficio();

        }

        //Boton Quitar
        if (e.getSource() == dlgA.btnQuitar) {

            quitarBeneficio();

        }

    }
    
}

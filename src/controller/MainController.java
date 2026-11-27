package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import model.CarrerasModel;
import pojo.Carreras;
import view.DlgCarrerasView;
import view.DlgNuevaCarrera;
import view.FrmGestionPrincipal;
import model.BeneficiosModel;
import model.EstudianteModel;
import pojo.Beneficios;
import pojo.Estudiante;
import view.DlgBeneficiosView;
import view.DlgEstudiantesView;
import view.DlgNuevoBeneficio;
import view.DlgNuevoEstudiante;

/**
 *
 * @author Graciela
 */
public class MainController implements ActionListener {

    private FrmGestionPrincipal frmPrincipal;

    private DlgCarrerasView dlgCarreras;
    private DlgNuevaCarrera dlgNuevaCarrera;

    private DlgBeneficiosView dlgBeneficios;
    private DlgNuevoBeneficio dlgNuevoBeneficio;

    private DlgEstudiantesView dlgEstudiantes;
    private DlgNuevoEstudiante dlgNuevoEstudiante;

    //Controladores
    private CarrerasController carrerasController;
    private BeneficiosController beneficiosController;
    private EstudiantesController estudiantesController;

    public MainController(FrmGestionPrincipal frmPrincipal) {

        this.frmPrincipal = frmPrincipal;

        // Crear las ventanas de Carreras
        dlgCarreras = new DlgCarrerasView(frmPrincipal, true);
        dlgNuevaCarrera = new DlgNuevaCarrera(frmPrincipal, true);

        //Crea las ventanas de Beneficio
        dlgBeneficios = new DlgBeneficiosView(frmPrincipal, true);
        dlgNuevoBeneficio = new DlgNuevoBeneficio(frmPrincipal, true);

        //Dlg De Estudiantes.
        dlgEstudiantes = new DlgEstudiantesView(frmPrincipal, false);
        dlgNuevoEstudiante = new DlgNuevoEstudiante(frmPrincipal, true);
        

        // --------Crear el controlador de Carreras-----------
        Carreras carrera = new Carreras();
        CarrerasModel carrModel = new CarrerasModel();

        DefaultTableModel modelo = (DefaultTableModel) dlgCarreras.tblCarreras.getModel();

        carrerasController = new CarrerasController(
                carrera, carrModel, dlgCarreras, dlgNuevaCarrera, modelo, 0);
        
        

        // ---------Crear Controlador de Beneficios------------
        Beneficios beneficio = new Beneficios();
        BeneficiosModel beneModel = new BeneficiosModel();

        DefaultTableModel modeloBeneficios
                = (DefaultTableModel) dlgBeneficios.tblBenefit.getModel();

        beneficiosController = new BeneficiosController(
                beneficio, beneModel, dlgBeneficios,
                dlgNuevoBeneficio, modeloBeneficios, 0);
        
   

        // ---------Crear Controlador de Estudiantes----------- 
        Estudiante estudiante = new Estudiante();
        EstudianteModel estModel = new EstudianteModel();

        DefaultTableModel modeloEstudiantes
                = (DefaultTableModel) dlgEstudiantes.tblEstudiantes.getModel();

        estudiantesController = new EstudiantesController(
                estudiante, estModel, dlgEstudiantes,
                dlgNuevoEstudiante, modeloEstudiantes, 0);
        
        

        // ------Escucha el botón de Gestión de Carreras--------
        this.frmPrincipal.btnGestionCarrera.addActionListener(this);

        //-------Escucha el boton de Gestión de beneficios-------
        this.frmPrincipal.btnGestionBenefit.addActionListener(this);

        //-------Escucha el boton de Gestión de Estudiantes--------
        this.frmPrincipal.btnGestionEst.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmPrincipal.btnGestionCarrera) {

            dlgCarreras.setLocationRelativeTo(frmPrincipal);
            dlgCarreras.setVisible(true);

        }

        if (e.getSource() == frmPrincipal.btnGestionBenefit) {

            dlgBeneficios.setLocationRelativeTo(frmPrincipal);
            dlgBeneficios.setVisible(true);

        }

        if (e.getSource() == frmPrincipal.btnGestionEst) {

            dlgEstudiantes.setLocationRelativeTo(frmPrincipal);
            dlgEstudiantes.setVisible(true);
        }

    }

}

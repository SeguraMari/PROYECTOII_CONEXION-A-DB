package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import model.BeneficiosEstudiantesModel;
import model.CarrerasModel;
import pojo.Carreras;
import view.DlgCarrerasView;
import view.DlgNuevaCarrera;
import view.FrmGestionPrincipal;
import model.BeneficiosModel;
import model.EstudianteModel;
import model.PagosMensualesModel;
import pojo.Beneficios;
import pojo.Estudiante;
import pojo.PagosMensuales;
import view.DlgAsignarBeneficio;
import view.DlgBeneficiosView;
import view.DlgEstudiantesView;
import view.DlgMostrarPlanilla;
import view.DlgNuevoBeneficio;
import view.DlgNuevoEstudiante;
import view.DlgPagoMensual;

/**
 *
 * @author Graciela
 */
public class MainController implements ActionListener {

    private FrmGestionPrincipal frmPrincipal;

    //Dlg Carreras
    private DlgCarrerasView dlgCarreras;
    private DlgNuevaCarrera dlgNuevaCarrera;

    //Dlg Beneficios
    private DlgBeneficiosView dlgBeneficios;
    private DlgNuevoBeneficio dlgNuevoBeneficio;
    private DlgAsignarBeneficio dlgAsignarBenefit;

    //Dlg Estudiantes
    private DlgEstudiantesView dlgEstudiantes;
    private DlgNuevoEstudiante dlgNuevoEstudiante;

    //Dlg Pago Mensual
    private DlgPagoMensual dlgPagoMensual;
    private DlgMostrarPlanilla dlgMostrarPlanilla;

    //Controladores
    private CarrerasController carrerasController;
    private BeneficiosController beneficiosController;
    private EstudiantesController estudiantesController;
    private AsignarBeneficioController asigBeneficioController;
    private PagoMensualController pagoMensualController;

    public MainController(FrmGestionPrincipal frmPrincipal) {

        this.frmPrincipal = frmPrincipal;

        // Crear las ventanas de Carreras
        dlgCarreras = new DlgCarrerasView(frmPrincipal, false);
        dlgNuevaCarrera = new DlgNuevaCarrera(frmPrincipal, true);

        //Crea las ventanas de Beneficio
        dlgBeneficios = new DlgBeneficiosView(frmPrincipal, false);
        dlgNuevoBeneficio = new DlgNuevoBeneficio(frmPrincipal, true);
        dlgAsignarBenefit = new DlgAsignarBeneficio(frmPrincipal, false);

        //Dlg De Estudiantes.
        dlgEstudiantes = new DlgEstudiantesView(frmPrincipal, false);
        dlgNuevoEstudiante = new DlgNuevoEstudiante(frmPrincipal, true);

        dlgPagoMensual = new DlgPagoMensual(frmPrincipal, false);

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

        // ---------Crear Controlador de Asignar Beneficio----------
        Estudiante estudianteAsignar = new Estudiante();

        EstudianteModel estModelAsignar = new EstudianteModel();

        BeneficiosEstudiantesModel beneEstModel = new BeneficiosEstudiantesModel();

        asigBeneficioController = new AsignarBeneficioController(
                estudianteAsignar,
                estModelAsignar,
                beneEstModel,
                dlgAsignarBenefit);

        //---------Crear Controlador de Pagos Mensuales---------------
        PagosMensuales pago = new PagosMensuales();

        PagosMensualesModel pagoModel = new PagosMensualesModel();

        EstudianteModel estModelPagos = new EstudianteModel();

        DefaultTableModel modeloPagos
                = (DefaultTableModel) dlgPagoMensual.tblMensualidades.getModel();

        pagoMensualController = new PagoMensualController(
                pago,
                pagoModel,
                estModelPagos,
                dlgPagoMensual,
                modeloPagos);

        // ------Escucha el botón de Gestión de Carreras--------
        this.frmPrincipal.btnGestionCarrera.addActionListener(this);

        //-------Escucha el boton de Gestión de beneficios-------
        this.frmPrincipal.btnGestionBenefit.addActionListener(this);

        //-------Escucha el boton de Asignar Beneficio-----------
        this.frmPrincipal.btnAsignarBenefit.addActionListener(this);

        //-------Escucha el boton de Gestión de Estudiantes--------
        this.frmPrincipal.btnGestionEst.addActionListener(this);

        //-------Escucha el boton de Pagos Mensuales-------------
        this.frmPrincipal.btnPagoMensual.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == frmPrincipal.btnGestionCarrera) {

            dlgCarreras.setLocationRelativeTo(frmPrincipal);
            dlgCarreras.setVisible(true);

        }

        //Boton GestionBeneficio
        if (e.getSource() == frmPrincipal.btnGestionBenefit) {

            dlgBeneficios.setLocationRelativeTo(frmPrincipal);
            dlgBeneficios.setVisible(true);

        }

        //Boton Estudiantes
        if (e.getSource() == frmPrincipal.btnGestionEst) {

            dlgEstudiantes.setLocationRelativeTo(frmPrincipal);
            dlgEstudiantes.setVisible(true);
        }

        if (e.getSource() == frmPrincipal.btnAsignarBenefit) {

            dlgAsignarBenefit.setLocationRelativeTo(frmPrincipal);
            dlgAsignarBenefit.setVisible(true);

        }

        if (e.getSource() == frmPrincipal.btnPagoMensual) {
            dlgPagoMensual.setLocationRelativeTo(frmPrincipal);
            dlgPagoMensual.setVisible(true);
        }

    }

}

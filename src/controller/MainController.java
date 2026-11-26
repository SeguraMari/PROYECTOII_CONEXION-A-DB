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
import pojo.Beneficios;
import view.DlgBeneficiosView;
import view.DlgNuevoBeneficio;

/**
 *
 * @author Graciela
 */
public class MainController implements ActionListener {

    private FrmGestionPrincipal frmPrincipal;
    private DlgCarrerasView dlgCarreras;
    private DlgNuevaCarrera dlgNuevaCarrera;

    private CarrerasController carrerasController;

    private DlgBeneficiosView dlgBeneficios;
    private DlgNuevoBeneficio dlgNuevoBeneficio;

    private BeneficiosController beneficiosController;

    public MainController(FrmGestionPrincipal frmPrincipal) {

        this.frmPrincipal = frmPrincipal;

        // Crear las ventanas de Carreras
        dlgCarreras = new DlgCarrerasView(frmPrincipal, true);
        dlgNuevaCarrera = new DlgNuevaCarrera(frmPrincipal, true);

        //Crea las ventanas de Beneficio
        dlgBeneficios = new DlgBeneficiosView(frmPrincipal, true);
        dlgNuevoBeneficio = new DlgNuevoBeneficio(frmPrincipal, true);

        // Crear el controlador de Carreras
        Carreras carrera = new Carreras();
        CarrerasModel carrModel = new CarrerasModel();

        DefaultTableModel modelo = (DefaultTableModel) dlgCarreras.tblCarreras.getModel();

        carrerasController = new CarrerasController(
                carrera, carrModel, dlgCarreras, dlgNuevaCarrera, modelo, 0);

        Beneficios beneficio = new Beneficios();
        BeneficiosModel beneModel = new BeneficiosModel();

        DefaultTableModel modeloBeneficios
                = (DefaultTableModel) dlgBeneficios.tblBenefit.getModel();

        beneficiosController = new BeneficiosController(
                beneficio, beneModel, dlgBeneficios,
                dlgNuevoBeneficio, modeloBeneficios, 0);

        // Escucha el botón de Gestión de Carreras
        this.frmPrincipal.btnGestionCarrera.addActionListener(this);

        //Escucha el boton de Gestion de beneficios
        this.frmPrincipal.btnGestionBenefit.addActionListener(this);
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

    }
    
}

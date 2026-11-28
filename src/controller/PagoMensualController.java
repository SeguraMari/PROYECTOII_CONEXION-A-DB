package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.PagosMensualesModel;
import model.EstudianteModel;
import pojo.PagosMensuales;
import view.DlgMostrarPlanilla;
import view.DlgPagoMensual;

/**
 *
 * @author Graciela
 */
public class PagoMensualController implements ActionListener {

    private PagosMensuales pago;
    private PagosMensualesModel pagoModel;
    private EstudianteModel estModel;
    private DlgPagoMensual dlgA;
    private DefaultTableModel modeloTabla;

    private DlgMostrarPlanilla dlgPlanilla;
    private MostrarPlanillaController planillaController;

    public PagoMensualController(PagosMensuales pago,
            PagosMensualesModel pagoModel,
            EstudianteModel estModel,
            DlgPagoMensual dlgA,
            DefaultTableModel modeloTabla) {

        dlgPlanilla = new DlgMostrarPlanilla(
                (java.awt.Frame) dlgA.getOwner(), true);
        planillaController = new MostrarPlanillaController(
                pagoModel, dlgPlanilla);

        planillaController.configurarTabla();

        this.pago = pago;
        this.pagoModel = pagoModel;
        this.estModel = estModel;
        this.dlgA = dlgA;
        this.modeloTabla = modeloTabla;

        this.dlgA.btnGenerarPago.addActionListener(this);
        this.dlgA.btnMostrarMensualidades.addActionListener(this);

        this.dlgA.chkEst.addActionListener(this);
        this.dlgA.chkMes.addActionListener(this);
        this.dlgA.chkAnio.addActionListener(this);

        cargarEstudiantes();
        cargarMeses();
        configurarTabla();
        cargarTabla();

        //Mostrará la fecha actual
        dlgA.txtFechActual.setText(
                new java.text.SimpleDateFormat("dd/MM/yyyy").format(new java.util.Date())
        );

    }

    /**
     * Este método obtiene los estudiantes de la BD y muestra sus nombres en el
     * ComboBox de estudiantes.
     */
    public void cargarEstudiantes() {

        DefaultTableModel modelo = estModel.mostrarEstudiantes();

        dlgA.cmbEstudiante.removeAllItems();

        for (int i = 0; i < modelo.getRowCount(); i++) {

            String nombre = modelo.getValueAt(i, 2).toString();

            dlgA.cmbEstudiante.addItem(nombre);
        }
    }

    public void cargarMeses() {

        String[] meses = {
            "Enero", "Febrero", "Marzo", "Abril",
            "Mayo", "Junio", "Julio", "Agosto",
            "Septiembre", "Octubre", "Noviembre", "Diciembre"
        };

        dlgA.cmbMess.removeAllItems();
        dlgA.cmbMes.removeAllItems();

        for (int i = 0; i < meses.length; i++) {
            dlgA.cmbMess.addItem(meses[i]);
            dlgA.cmbMes.addItem(meses[i]);
        }
    }

    /**
     * Este método establece las 10 columnas correspondientes a los campos.
     */
    public void configurarTabla() {

        modeloTabla = new DefaultTableModel();

        modeloTabla.addColumn("ID Pago");
        modeloTabla.addColumn("Fecha Creación");
        modeloTabla.addColumn("Mes");
        modeloTabla.addColumn("Año");
        modeloTabla.addColumn("Fecha Pago");
        modeloTabla.addColumn("Estudiante");
        modeloTabla.addColumn("Total Beneficios");
        modeloTabla.addColumn("Deducción Seguro");
        modeloTabla.addColumn("Deducción Renta");
        modeloTabla.addColumn("Pago Neto");

        dlgA.tblMensualidades.setModel(modeloTabla);
    }

    /**
     * Este método obtiene los pagos desde el modelo y agrega cada registro como
     * una fila de la tabla.
     */
    public void cargarTabla() {

        modeloTabla.setRowCount(0);

        ArrayList<PagosMensuales> lista = pagoModel.mostrarPagosMensuales();

        for (int i = 0; i < lista.size(); i++) {

            PagosMensuales pago = lista.get(i);

            Object[] fila = {
                pago.getIdPago(),
                pago.getFechCreacion(),
                pago.getMes(),
                pago.getAnio(),
                pago.getFechPago(),
                pago.getEstudiante(),
                pago.getTotalBenefits(),
                pago.getDeducSeguro(),
                pago.getDeducRenta(),
                pago.getPagoNeto()
            };

            modeloTabla.addRow(fila);
        }
    }

    /**
     * Filtro habilite o deshabilite su campo.
     */
    public void habilitarFiltros() {

        dlgA.cmbEstudiante.setEnabled(dlgA.chkEst.isSelected());

        dlgA.cmbMes.setEnabled(dlgA.chkMes.isSelected());

        dlgA.txtAnio.setEnabled(dlgA.chkAnio.isSelected());
    }

    /**
     * Este método utiliza la cédula del estudiante para consultar la base de
     * datos aunque en el combo se muestre su nombre
     */
    public void mostrarMensualidades() {

        String estudiante = "";
        String mes = "";
        int anio = 0;

        if (dlgA.chkEst.isSelected()) {

            if (dlgA.cmbEstudiante.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(dlgA, "Seleccione un estudiante.");
                return;
            }

            String nombre = dlgA.cmbEstudiante.getSelectedItem().toString();

            DefaultTableModel modelo = estModel.mostrarEstudiantes();

            for (int i = 0; i < modelo.getRowCount(); i++) {

                if (modelo.getValueAt(i, 2).toString().equals(nombre)) {
                    estudiante = modelo.getValueAt(i, 1).toString();
                    break;
                }
            }
        }

        if (dlgA.chkMes.isSelected()) {
            mes = dlgA.cmbMes.getSelectedItem().toString();
        }

        if (dlgA.chkAnio.isSelected()) {

            try {
                anio = Integer.parseInt(dlgA.txtAnio.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(dlgA, "Ingrese un año válido.");
                return;
            }
        }

        ArrayList<PagosMensuales> lista
                = pagoModel.buscarPagosMensuales(estudiante, mes, anio);

        modeloTabla.setRowCount(0);

        for (int i = 0; i < lista.size(); i++) {

            PagosMensuales pago = lista.get(i);

            Object[] fila = {
                pago.getIdPago(),
                pago.getFechCreacion(),
                pago.getMes(),
                pago.getAnio(),
                pago.getFechPago(),
                pago.getEstudiante(),
                pago.getTotalBenefits(),
                pago.getDeducSeguro(),
                pago.getDeducRenta(),
                pago.getPagoNeto()
            };

            modeloTabla.addRow(fila);
        }
    }

    /**
     * Este método toma el mes y año seleccionados y abre la ventana con los
     * registros de esa planilla.
     */
    public void abrirPlanilla() {

        String mes = dlgA.cmbMes.getSelectedItem().toString();

        int anio;

        try {
            anio = Integer.parseInt(dlgA.txtAnio.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dlgA, "Ingrese un año válido.");
            return;
        }

        planillaController.cargarPlanilla(mes, anio);

        dlgPlanilla.setLocationRelativeTo(dlgA);
        dlgPlanilla.setVisible(true);
    }

    public void generarPago() {

        String mes = dlgA.cmbMess.getSelectedItem().toString();

        int anio;

        try {
            anio = Integer.parseInt(dlgA.txtAnioGen.getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(dlgA, "Ingrese un año válido.");
            return;
        }

        int cantidad = pagoModel.generarPlanilla(mes, anio);

        if (cantidad > 0) {

            JOptionPane.showMessageDialog(dlgA,
                    "Se generaron " + cantidad + " pagos correctamente.");

            cargarTabla();

        } else {

            JOptionPane.showMessageDialog(dlgA,
                    "No se generaron pagos. Verifique el período o si la planilla ya existe.");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == dlgA.chkEst
                || e.getSource() == dlgA.chkMes
                || e.getSource() == dlgA.chkAnio) {

            habilitarFiltros();

        }

        if (e.getSource() == dlgA.btnMostrarMensualidades) {
            abrirPlanilla();
        }

        if (e.getSource() == dlgA.btnGenerarPago) {
            generarPago();
        }

    }

}

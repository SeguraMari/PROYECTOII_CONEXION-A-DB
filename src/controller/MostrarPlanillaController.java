package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import model.PagosMensualesModel;
import pojo.PagosMensuales;
import view.DlgMostrarPlanilla;

/**
 *
 * @author Graciela
 */
public class MostrarPlanillaController implements ActionListener {

    private PagosMensualesModel pagoModel;
    private DlgMostrarPlanilla dlgA;
    private DefaultTableModel modeloTabla;

    public MostrarPlanillaController(PagosMensualesModel pagoModel,
            DlgMostrarPlanilla dlgA) {

        this.pagoModel = pagoModel;
        this.dlgA = dlgA;

        this.dlgA.btnCerrar.addActionListener(this);

    }

    /**
     * Estas columnas corresponden a los campos de la tabla PagosMensuales de la
     * BD.
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

        dlgA.tblPlanilla.setModel(modeloTabla);
    }

    public void cargarPlanilla(String mes, int anio) {

        ArrayList<PagosMensuales> lista
                = pagoModel.buscarPagosMensuales("", mes, anio);

        modeloTabla.setRowCount(0);

        double totalGeneral = 0;

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

            totalGeneral = totalGeneral + pago.getTotalBenefits();
        }

        dlgA.lblMes.setText(mes);
        dlgA.lblAnio.setText(String.valueOf(anio));

        dlgA.lblTotalGeneral.setText(String.valueOf(totalGeneral));
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == dlgA.btnCerrar) {
            dlgA.dispose();
        }

    }

}

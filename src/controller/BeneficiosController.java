package controller;

/**
 *
 * @author Graciela
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import model.BeneficiosModel;
import pojo.Beneficios;
import view.DlgBeneficiosView;
import view.DlgNuevoBeneficio;

public class BeneficiosController implements ActionListener, KeyListener {

    private Beneficios beneficio;
    private BeneficiosModel beneModel;
    private DlgBeneficiosView dlgA;
    private DlgNuevoBeneficio dlgB;
    private DefaultTableModel modelT;
    private int operacion;

    public BeneficiosController(Beneficios beneficio,
            BeneficiosModel beneModel,
            DlgBeneficiosView dlgA,
            DlgNuevoBeneficio dlgB,
            DefaultTableModel modelT,
            int operacion) {

        this.beneficio = beneficio;
        this.beneModel = beneModel;
        this.dlgA = dlgA;
        this.dlgB = dlgB;
        this.modelT = modelT;
        this.operacion = operacion;

        this.dlgA.btnNuevo.addActionListener(this);
        this.dlgA.btnEditar.addActionListener(this);
        this.dlgA.btnEliminar.addActionListener(this);
        this.dlgA.txtBuscar.addKeyListener(this);

        this.dlgB.btnGuardar.addActionListener(this);
        this.dlgB.btnCancelar.addActionListener(this);
        this.dlgB.btnLimpiar.addActionListener(this);

        cargarTabla();
    }

    public void cargarTabla() {

        modelT = beneModel.mostrarBeneficios();

        dlgA.tblBenefit.setModel(modelT);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        // Botón Nuevo
        if (e.getSource() == dlgA.btnNuevo) {

            operacion = 1;

            dlgB.txtNomBeneficio.setText("");
            dlgB.txtDescrip.setText("");
            dlgB.txtMontBenefit.setText("");

            dlgB.setLocationRelativeTo(dlgA);
            dlgB.setVisible(true);
        }

        // Botón Cancelar
        if (e.getSource() == dlgB.btnCancelar) {

            dlgB.dispose();
        }

        // Botón Limpiar
        if (e.getSource() == dlgB.btnLimpiar) {

            dlgB.txtNomBeneficio.setText("");
            dlgB.txtDescrip.setText("");
            dlgB.txtMontBenefit.setText("");
        }

        if (e.getSource() == dlgB.btnGuardar) {

            if (dlgB.txtNomBeneficio.getText().isEmpty()
                    || dlgB.txtDescrip.getText().isEmpty()
                    || dlgB.txtMontBenefit.getText().isEmpty()) {

                JOptionPane.showMessageDialog(dlgB,
                        "Debe completar todos los campos.");

            } else {

                if (operacion == 1) {

                    try {

                        beneficio = new Beneficios();

                        beneficio.setNomBeneficio(
                                dlgB.txtNomBeneficio.getText());

                        beneficio.setDescripcion(
                                dlgB.txtDescrip.getText());

                        beneficio.setMontoBeneficio(
                                Double.parseDouble(
                                        dlgB.txtMontBenefit.getText()));

                        if (beneModel.insertarBeneficio(beneficio)) {

                            JOptionPane.showMessageDialog(dlgB,
                                    "Beneficio registrado correctamente.");

                            dlgB.dispose();
                            cargarTabla();

                        } else {

                            JOptionPane.showMessageDialog(dlgB,
                                    "Error al registrar el beneficio.");
                        }

                    } catch (NumberFormatException ex) {

                        JOptionPane.showMessageDialog(dlgB,
                                "El monto debe ser un número válido.");
                    }
                } else if (operacion == 2) {

                    try {

                        beneficio.setNomBeneficio(
                                dlgB.txtNomBeneficio.getText());

                        beneficio.setDescripcion(
                                dlgB.txtDescrip.getText());

                        beneficio.setMontoBeneficio(
                                Double.parseDouble(
                                        dlgB.txtMontBenefit.getText()));

                        if (beneModel.modificarBeneficio(beneficio)) {

                            JOptionPane.showMessageDialog(dlgB,
                                    "Beneficio modificado correctamente.");

                            dlgB.dispose();
                            cargarTabla();

                        } else {

                            JOptionPane.showMessageDialog(dlgB,
                                    "Error al modificar el beneficio.");
                        }

                    } catch (NumberFormatException ex) {

                        JOptionPane.showMessageDialog(dlgB,
                                "El monto debe ser un número válido.");
                    }

                }

            }

            if (e.getSource() == dlgA.btnEditar) {

                if (dlgA.tblBenefit.getSelectedRowCount() == 1) {

                    int fila = dlgA.tblBenefit.getSelectedRow();

                    operacion = 2;

                    beneficio = new Beneficios();

                    beneficio.setIdBeneficio(Integer.parseInt(
                            dlgA.tblBenefit.getValueAt(fila, 0).toString()));

                    dlgB.txtNomBeneficio.setText(
                            dlgA.tblBenefit.getValueAt(fila, 1).toString());

                    dlgB.txtDescrip.setText(
                            dlgA.tblBenefit.getValueAt(fila, 2).toString());

                    dlgB.txtMontBenefit.setText(
                            dlgA.tblBenefit.getValueAt(fila, 3).toString());

                    dlgB.setLocationRelativeTo(dlgA);
                    dlgB.setVisible(true);

                } else {

                    JOptionPane.showMessageDialog(dlgA,
                            "Debe seleccionar un beneficio.");
                }

            }

        }

        if (e.getSource() == dlgA.btnEliminar) {

            if (dlgA.tblBenefit.getSelectedRowCount() == 1) {

                int fila = dlgA.tblBenefit.getSelectedRow();

                int idBeneficio = Integer.parseInt(
                        dlgA.tblBenefit.getValueAt(fila, 0).toString());

                int respuesta = JOptionPane.showConfirmDialog(dlgA,
                        "¿Está seguro de eliminar este beneficio?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {

                    if (beneModel.eliminarBeneficio(idBeneficio)) {

                        JOptionPane.showMessageDialog(dlgA,
                                "Beneficio eliminado correctamente.");

                        cargarTabla();

                    } else {

                        JOptionPane.showMessageDialog(dlgA,
                                "No se pudo eliminar el beneficio.");
                    }
                }

            } else {

                JOptionPane.showMessageDialog(dlgA,
                        "Debe seleccionar un beneficio.");
            }

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }
        

    @Override
    public void keyPressed(KeyEvent e) {
        
    }
        

    @Override
    public void keyReleased(KeyEvent e) {

        DefaultTableModel modeloCompleto = beneModel.mostrarBeneficios();

        DefaultTableModel modeloFiltrado = new DefaultTableModel(
                new String[]{"ID", "Nombre", "Descripción", "Monto"}, 0);
        String buscar = dlgA.txtBuscar.getText().toLowerCase();

       System.out.println("Texto buscado: " + buscar);

        for (int i = 0; i < modeloCompleto.getRowCount(); i++) {

            String nombre = modeloCompleto.getValueAt(i, 1).toString();

            if (nombre.toLowerCase().contains(buscar)) {

                Object fila[] = {
                    modeloCompleto.getValueAt(i, 0),
                    modeloCompleto.getValueAt(i, 1),
                    modeloCompleto.getValueAt(i, 2),
                    modeloCompleto.getValueAt(i, 3)
                };

                modeloFiltrado.addRow(fila);
            }
        }

        dlgA.tblBenefit.setModel(modeloFiltrado);

    }
    
}

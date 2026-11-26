package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.CarrerasModel;
import pojo.Carreras;
import view.DlgCarrerasView;
import view.DlgNuevaCarrera;

/**
 *
 * @author Graciela
 */
public class CarrerasController implements ActionListener, KeyListener {

    private Carreras carrera;
    private CarrerasModel carrModel;
    private DlgCarrerasView dlgA;
    private DlgNuevaCarrera dlgB;
    private DefaultTableModel modelT;
    private int operacion;

    public CarrerasController(Carreras carrera, CarrerasModel carrModel, DlgCarrerasView dlgA, DlgNuevaCarrera dlgB, DefaultTableModel modelT, int operacion) {
        this.carrera = carrera;
        this.carrModel = carrModel;
        this.dlgA = dlgA;
        this.dlgB = dlgB;
        this.modelT = modelT;
        this.operacion = operacion;

        //Eventos de la ventana Carrera
        this.dlgA.btnNuevo.addActionListener(this);
        this.dlgA.btnEditar.addActionListener(this);
        this.dlgA.btnEliminar.addActionListener(this);

        this.dlgA.txtBuscar.addKeyListener(this);

        //Eventos de la ventana NuevaCarrera
        this.dlgB.btnGuardar.addActionListener(this);
        this.dlgB.btnCancelar.addActionListener(this);
        this.dlgB.btnLimpiar.addActionListener(this);

        cargarTabla();

    }

    //Carga las carreras en la tabla
    public void cargarTabla() {

        modelT = (DefaultTableModel) dlgA.tblCarreras.getModel();

        modelT.setRowCount(0);

        ArrayList<Carreras> lista = carrModel.mostrarCarreras();

        for (int i = 0; i < lista.size(); i++) {

            Object fila[] = {
                lista.get(i).getIdCarrera(),
                lista.get(i).getNomCarrera(),
                lista.get(i).getGrado()
            };

            modelT.addRow(fila);
        }

    }

    //Metodo para controlar los botones
    @Override
    public void actionPerformed(ActionEvent e) {

        //Boton Nuevo
        if (e.getSource() == dlgA.btnNuevo) {

            operacion = 1;

            dlgB.txtNomCarrera.setText("");
            dlgB.cmbGrado.setSelectedIndex(0);

            dlgB.setLocationRelativeTo(dlgA);
            dlgB.setVisible(true);
        }

        //Boton Cancelar
        if (e.getSource() == dlgB.btnCancelar) {

            dlgB.dispose();
        }

        //Boton Limpiar
        if (e.getSource() == dlgB.btnLimpiar) {

            dlgB.txtNomCarrera.setText("");
            dlgB.cmbGrado.setSelectedIndex(0);
        }

        if (e.getSource() == dlgB.btnGuardar) {

            if (dlgB.txtNomCarrera.getText().isEmpty()) {

                JOptionPane.showMessageDialog(dlgB,
                        "Debe ingresar el nombre de la carrera.");

            } else {

                // Registrar carrera
                if (operacion == 1) {

                    carrera = new Carreras();

                    carrera.setNomCarrera(
                            dlgB.txtNomCarrera.getText());

                    carrera.setGrado(
                            dlgB.cmbGrado.getSelectedItem().toString());

                    if (carrModel.insertarCarrera(carrera)) {

                        JOptionPane.showMessageDialog(dlgB,
                                "Carrera registrada correctamente.");

                        dlgB.dispose();
                        cargarTabla();

                    } else {

                        JOptionPane.showMessageDialog(dlgB,
                                "Error al registrar la carrera.");
                    }
                } // Modificar carrera
                else if (operacion == 2) {

                    carrera.setNomCarrera(
                            dlgB.txtNomCarrera.getText());

                    carrera.setGrado(
                            dlgB.cmbGrado.getSelectedItem().toString());

                    if (carrModel.modificarCarrera(carrera)) {

                        JOptionPane.showMessageDialog(dlgB,
                                "Carrera modificada correctamente.");

                        dlgB.dispose();
                        cargarTabla();

                    } else {

                        JOptionPane.showMessageDialog(dlgB,
                                "Error al modificar la carrera.");
                    }
                }
            }
        }

        //Boton Editar
        if (e.getSource() == dlgA.btnEditar) {

            if (dlgA.tblCarreras.getSelectedRowCount() == 1) {

                int fila = dlgA.tblCarreras.getSelectedRow();

                operacion = 2;

                carrera = new Carreras();

                carrera.setIdCarrera(Integer.parseInt(
                        dlgA.tblCarreras.getValueAt(fila, 0).toString()));

                dlgB.txtNomCarrera.setText(
                        dlgA.tblCarreras.getValueAt(fila, 1).toString());

                dlgB.cmbGrado.setSelectedItem(
                        dlgA.tblCarreras.getValueAt(fila, 2).toString());

                dlgB.setLocationRelativeTo(dlgA);
                dlgB.setVisible(true);

            } else {

                JOptionPane.showMessageDialog(dlgA,
                        "Debe seleccionar una carrera.");
            }
        }

        //Botón Eliminar
        if (e.getSource() == dlgA.btnEliminar) {

            if (dlgA.tblCarreras.getSelectedRowCount() == 1) {

                int fila = dlgA.tblCarreras.getSelectedRow();

                int idCarrera = Integer.parseInt(
                        dlgA.tblCarreras.getValueAt(fila, 0).toString());

                int respuesta = JOptionPane.showConfirmDialog(dlgA,
                        "¿Está seguro de eliminar esta carrera?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {

                    if (carrModel.eliminarCarrera(idCarrera)) {

                        JOptionPane.showMessageDialog(dlgA,
                                "Carrera eliminada correctamente.");

                        cargarTabla();

                    } else {

                        JOptionPane.showMessageDialog(dlgA,
                                "No se pudo eliminar la carrera.");
                    }
                }

            } else {

                JOptionPane.showMessageDialog(dlgA,
                        "Debe seleccionar una carrera.");
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

        modelT = (DefaultTableModel) dlgA.tblCarreras.getModel();

        modelT.setRowCount(0);

        ArrayList<Carreras> lista = carrModel.mostrarCarreras();

        for (int i = 0; i < lista.size(); i++) {

            if (lista.get(i).getNomCarrera().toLowerCase().contains(
                    dlgA.txtBuscar.getText().toLowerCase())) {

                Object fila[] = {
                    lista.get(i).getIdCarrera(),
                    lista.get(i).getNomCarrera(),
                    lista.get(i).getGrado()
                };

                modelT.addRow(fila);
            }
        }

    }

}

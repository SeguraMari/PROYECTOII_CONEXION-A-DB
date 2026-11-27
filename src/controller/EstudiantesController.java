package controller;

/**
 *
 * @author Graciela
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.CarrerasModel;
import model.EstudianteModel;
import pojo.Estudiante;
import view.DlgEstudiantesView;
import view.DlgNuevoEstudiante;

public class EstudiantesController implements ActionListener, KeyListener {

    private Estudiante estudiante;
    private EstudianteModel estModel;
    private DlgEstudiantesView dlgA;
    private DlgNuevoEstudiante dlgB;
    private DefaultTableModel modelT;
    private int operacion;

    public EstudiantesController(Estudiante estudiante,
            EstudianteModel estModel,
            DlgEstudiantesView dlgA,
            DlgNuevoEstudiante dlgB,
            DefaultTableModel modelT,
            int operacion) {

        this.estudiante = estudiante;
        this.estModel = estModel;
        this.dlgA = dlgA;
        this.dlgB = dlgB;
        this.modelT = modelT;
        this.operacion = operacion;

        //Dlg A.
        this.dlgA.btnNuevo.addActionListener(this);
        this.dlgA.btnEditar.addActionListener(this);
        this.dlgA.btnEliminar.addActionListener(this);
        this.dlgA.txtBuscar.addKeyListener(this);

        //Dlg B.
        this.dlgB.btnGuardar.addActionListener(this);
        this.dlgB.btnCancelar.addActionListener(this);
        this.dlgB.btnLimpiar.addActionListener(this);

        this.dlgA.btnNuevo.addActionListener(this);

        cargarTabla();
    }

    /**
     * Se utiliza para que el controlador solicite los Estudiantes a
     * EstudiantesModel y coloque los datos en la tblEstudiantes.
     */
    public void cargarTabla() {

        modelT = estModel.mostrarEstudiantes();
        dlgA.tblEstudiantes.setModel(modelT);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        //Boton Nuevo
        if (e.getSource() == dlgA.btnNuevo) {

            operacion = 1;

            dlgB.txtCarnet.setText("");
            dlgB.txtCedula.setText("");
            dlgB.txtNombre.setText("");
            dlgB.dtpFechaNac.setText("");
            dlgB.txtDireccion.setText("");
            dlgB.txtTel.setText("");
            dlgB.txtEmail.setText("");
            dlgB.dtpFechIngreso.setText("");
            dlgB.dtpFechEgreso.setText("");
            dlgB.cmbCarrera.setSelectedIndex(-1);

            cargarCarreras();

            dlgB.setLocationRelativeTo(dlgA);
            dlgB.setVisible(true);
            dlgB.txtCarnet.setEnabled(true);

        }

        // Botón Cancelar
        if (e.getSource() == dlgB.btnCancelar) {

            dlgB.dispose();
        }

        //Boton Limpiar
        if (e.getSource() == dlgB.btnLimpiar) {

            dlgB.txtCarnet.setText("");
            dlgB.txtCedula.setText("");
            dlgB.txtNombre.setText("");
            dlgB.dtpFechaNac.setText("");
            dlgB.txtDireccion.setText("");
            dlgB.txtTel.setText("");
            dlgB.txtEmail.setText("");
            dlgB.dtpFechIngreso.setText("");
            dlgB.dtpFechEgreso.setText("");
            dlgB.cmbCarrera.setSelectedIndex(-1);

        }

        //Boton Guardar
        if (e.getSource() == dlgB.btnGuardar) {

            if (dlgB.txtCarnet.getText().isEmpty()
                    || dlgB.txtCedula.getText().isEmpty()
                    || dlgB.txtNombre.getText().isEmpty()
                    || dlgB.dtpFechaNac.getDate() == null
                    || dlgB.txtDireccion.getText().isEmpty()
                    || dlgB.txtTel.getText().isEmpty()
                    || dlgB.txtEmail.getText().isEmpty()
                    || dlgB.dtpFechIngreso.getDate() == null
                    || dlgB.cmbCarrera.getSelectedIndex() == -1) {

                JOptionPane.showMessageDialog(dlgB,
                        "Debe completar todos los campos obligatorios.");

            } else {

                try {

                    estudiante = new Estudiante();

                    estudiante.setCarnet(dlgB.txtCarnet.getText());
                    estudiante.setCedula(dlgB.txtCedula.getText());
                    estudiante.setNombre(dlgB.txtNombre.getText());
                    estudiante.setFechNac(java.sql.Date.valueOf(dlgB.dtpFechaNac.getDate()));
                    estudiante.setDireccion(dlgB.txtDireccion.getText());
                    estudiante.setTelefono(dlgB.txtTel.getText());
                    estudiante.setEmail(dlgB.txtEmail.getText());
                    estudiante.setFechIngreso(java.sql.Date.valueOf(dlgB.dtpFechIngreso.getDate()));

                    if (dlgB.dtpFechEgreso.getDate() == null) {
                        estudiante.setFechEgreso(null);
                    } else {
                        estudiante.setFechEgreso(java.sql.Date.valueOf(dlgB.dtpFechEgreso.getDate()));
                    }

                    int idCarrera = obtenerIdCarrera(
                            dlgB.cmbCarrera.getSelectedItem().toString());

                    estudiante.setCarrera(idCarrera);

                    // Registrar estudiante
                    if (operacion == 1) {

                        if (estModel.insertarEstudiante(estudiante)) {

                            JOptionPane.showMessageDialog(dlgB,
                                    "Estudiante registrado correctamente.");

                            dlgB.dispose();
                            cargarTabla();

                        } else {

                            JOptionPane.showMessageDialog(dlgB,
                                    "Error al registrar el estudiante.");
                        }

                    } // Modificar estudiante
                    else if (operacion == 2) {

                        if (estModel.modificarEstudiante(estudiante)) {

                            JOptionPane.showMessageDialog(dlgB,
                                    "Estudiante modificado correctamente.");

                            dlgB.dispose();
                            cargarTabla();

                        } else {

                            JOptionPane.showMessageDialog(dlgB,
                                    "Error al modificar el estudiante.");
                        }
                    }

                } catch (IllegalArgumentException ex) {

                    JOptionPane.showMessageDialog(dlgB,
                            "Las fechas deben tener el formato yyyy-MM-dd.");
                }
            }
        }

        //Boton Editar
        if (e.getSource() == dlgA.btnEditar) {

            if (dlgA.tblEstudiantes.getSelectedRowCount() == 1) {

                int fila = dlgA.tblEstudiantes.getSelectedRow();

                operacion = 2;

                estudiante = new Estudiante();

                estudiante.setCarnet(
                        dlgA.tblEstudiantes.getValueAt(fila, 0).toString());

                dlgB.txtCarnet.setText(
                        dlgA.tblEstudiantes.getValueAt(fila, 0).toString());

                dlgB.txtCedula.setText(
                        dlgA.tblEstudiantes.getValueAt(fila, 1).toString());

                dlgB.txtNombre.setText(
                        dlgA.tblEstudiantes.getValueAt(fila, 2).toString());

                dlgB.dtpFechaNac.setText(
                        dlgA.tblEstudiantes.getValueAt(fila, 3).toString());

                dlgB.txtDireccion.setText(
                        dlgA.tblEstudiantes.getValueAt(fila, 4).toString());

                dlgB.txtTel.setText(
                        dlgA.tblEstudiantes.getValueAt(fila, 5).toString());

                dlgB.txtEmail.setText(
                        dlgA.tblEstudiantes.getValueAt(fila, 6).toString());

                dlgB.dtpFechIngreso.setText(
                        dlgA.tblEstudiantes.getValueAt(fila, 7).toString());

                //Modifiqué esto, por un NullPointerException
                Object fechaEgreso = dlgA.tblEstudiantes.getValueAt(fila, 8);

                if (fechaEgreso == null) {
                    dlgB.dtpFechEgreso.setDate(null);
                } else {
                    dlgB.dtpFechEgreso.setDate(
                            java.time.LocalDate.parse(fechaEgreso.toString()));
                }

                cargarCarreras();

                dlgB.cmbCarrera.setSelectedItem(
                        obtenerNombreCarrera(Integer.parseInt(
                                dlgA.tblEstudiantes.getValueAt(fila, 9).toString())));

                dlgB.txtCarnet.setEnabled(false);

                dlgB.setLocationRelativeTo(dlgA);
                dlgB.setVisible(true);
                
            } else {

                JOptionPane.showMessageDialog(dlgA,
                        "Debe seleccionar un estudiante.");
            }

        }

        //Boton Eliminar
        if (e.getSource() == dlgA.btnEliminar) {

            if (dlgA.tblEstudiantes.getSelectedRowCount() == 1) {

                int fila = dlgA.tblEstudiantes.getSelectedRow();

                String carnet = dlgA.tblEstudiantes.getValueAt(fila, 0).toString();

                int respuesta = JOptionPane.showConfirmDialog(dlgA,
                        "¿Está seguro de eliminar este estudiante?",
                        "Confirmar eliminación",
                        JOptionPane.YES_NO_OPTION);

                if (respuesta == JOptionPane.YES_OPTION) {

                    if (estModel.eliminarEstudiante(carnet)) {

                        JOptionPane.showMessageDialog(dlgA,
                                "Estudiante eliminado correctamente.");

                        cargarTabla();

                    } else {

                        JOptionPane.showMessageDialog(dlgA,
                                "No se pudo eliminar el estudiante.");
                    }
                }

            } else {

                JOptionPane.showMessageDialog(dlgA,
                        "Debe seleccionar un estudiante.");
            }

        }

    }

    public void cargarCarreras() {

        CarrerasModel carrerasModel = new CarrerasModel();

        DefaultTableModel modelo = carrerasModel.mostrarCarreras();

        dlgB.cmbCarrera.removeAllItems();

        for (int i = 0; i < modelo.getRowCount(); i++) {

            String nombre = modelo.getValueAt(i, 1).toString();

            dlgB.cmbCarrera.addItem(nombre);
        }
    }

    /**
     * Busca el nombre seleccionado en la lista de carreras y devuelve su ID.
     *
     * @param nombreCarrera
     * @return Id de carrera.
     */
    public int obtenerIdCarrera(String nombreCarrera) {

        CarrerasModel carrerasModel = new CarrerasModel();

        DefaultTableModel modelo = carrerasModel.mostrarCarreras();

        for (int i = 0; i < modelo.getRowCount(); i++) {

            String nombre = modelo.getValueAt(i, 1).toString();

            if (nombre.equals(nombreCarrera)) {

                return Integer.parseInt(
                        modelo.getValueAt(i, 0).toString());
            }
        }

        return 0;
    }

    public String obtenerNombreCarrera(int idCarrera) {

        CarrerasModel carrerasModel = new CarrerasModel();

        DefaultTableModel modelo = carrerasModel.mostrarCarreras();

        for (int i = 0; i < modelo.getRowCount(); i++) {

            int id = Integer.parseInt(
                    modelo.getValueAt(i, 0).toString());

            if (id == idCarrera) {

                return modelo.getValueAt(i, 1).toString();
            }
        }

        return "";

    }

    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

        DefaultTableModel modeloCompleto = estModel.mostrarEstudiantes();

        DefaultTableModel modeloFiltrado = new DefaultTableModel(
                new String[]{"Carnet", "Cédula", "Nombre",
                    "Fecha Nacimiento", "Dirección", "Teléfono",
                    "Email", "Fecha Ingreso", "Fecha Egreso",
                    "ID Carrera"}, 0);

        String buscar = dlgA.txtBuscar.getText().toLowerCase();

        for (int i = 0; i < modeloCompleto.getRowCount(); i++) {

            String nombre = modeloCompleto.getValueAt(i, 2).toString();

            if (nombre.toLowerCase().contains(buscar)) {

                Object fila[] = {
                    modeloCompleto.getValueAt(i, 0),
                    modeloCompleto.getValueAt(i, 1),
                    modeloCompleto.getValueAt(i, 2),
                    modeloCompleto.getValueAt(i, 3),
                    modeloCompleto.getValueAt(i, 4),
                    modeloCompleto.getValueAt(i, 5),
                    modeloCompleto.getValueAt(i, 6),
                    modeloCompleto.getValueAt(i, 7),
                    modeloCompleto.getValueAt(i, 8),
                    modeloCompleto.getValueAt(i, 9)
                };

                modeloFiltrado.addRow(fila);
            }
        }

        dlgA.tblEstudiantes.setModel(modeloFiltrado);

    }

}

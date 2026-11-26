package proyecto2_mvc;

import controller.MainController;
import view.FrmGestionPrincipal;

/**
 *
 * @author Graciela
 */
public class Main {

    public static void main(String[] args) {

        FrmGestionPrincipal principal = new FrmGestionPrincipal();
//        frm.setLocationRelativeTo(null);
//        frm.setVisible(true);

        MainController controlador = new MainController(principal);

        principal.setLocationRelativeTo(null);
        principal.setVisible(true);

    }

}

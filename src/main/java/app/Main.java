package app;

import javax.swing.SwingUtilities;
import view.LoginFrame;

/** Titik masuk Pertemuan 11: aplikasi selalu dimulai dari LoginFrame. */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginFrame().setVisible(true));
    }
}

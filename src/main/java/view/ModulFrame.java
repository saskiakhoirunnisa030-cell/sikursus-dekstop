package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * Kerangka form master untuk uji navigasi Pertemuan 11.
 * Ganti isi form dengan CRUD (DAO + JTable) mengikuti pola FormPeserta.
 */
public abstract class ModulFrame extends JFrame {

    protected ModulFrame(String judulModul) {
        setTitle("SIKURSUS Desktop - " + judulModul);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        JLabel lbl = new JLabel(judulModul, SwingConstants.CENTER);
        lbl.setFont(new Font("SansSerif", Font.BOLD, 20));
        lbl.setForeground(new Color(65, 75, 95));
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(new Color(230, 230, 250));
        p.add(lbl, BorderLayout.CENTER);
        setContentPane(p);
        setSize(520, 340);
        setLocationRelativeTo(null);
    }
}

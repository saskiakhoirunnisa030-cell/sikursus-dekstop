package view;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

/** Jendela Master Data Peserta (dibuka dari menu Master > Peserta). */
public class PesertaFrame extends JFrame {

    private final PesertaPanel panel = new PesertaPanel();

    public PesertaFrame() {
        setTitle("SIKURSUS Desktop - Master Data Peserta");
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setContentPane(panel);

        // ukuran mengikuti gambar acuan, tetapi tidak melebihi layar
        Dimension layar = Toolkit.getDefaultToolkit().getScreenSize();
        int w = Math.min(920, layar.width - 40);
        int h = Math.min(830, layar.height - 70);
        setSize(w, h);
        setMinimumSize(new Dimension(Math.min(860, w), Math.min(620, h)));
        setLocationRelativeTo(null);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                panel.muatData();
            }
        });
    }
}

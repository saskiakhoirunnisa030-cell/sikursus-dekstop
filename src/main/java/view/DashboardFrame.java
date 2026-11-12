package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.net.URL;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.SwingConstants;
import model.User;
import util.UserSession;
import util.WindowManager;

public class DashboardFrame extends JFrame {

    // Menu bar (nama variabel mengikuti panduan)
    private final JMenuBar menuBar = new JMenuBar();
    private final JMenu menuMaster = new JMenu("Master");
    private final JMenuItem miPeserta = new JMenuItem("Peserta");
    private final JMenuItem miInstruktur = new JMenuItem("Instruktur");
    private final JMenuItem miKursus = new JMenuItem("Kursus");
    private final JMenuItem miKelas = new JMenuItem("Kelas");
    private final JMenu menuTransaksi = new JMenu("Transaksi");
    private final JMenuItem miPendaftaran = new JMenuItem("Pendaftaran");
    private final JMenuItem miPembayaran = new JMenuItem("Pembayaran");
    private final JMenu menuLaporan = new JMenu("Laporan");
    private final JMenuItem miLaporanPeserta = new JMenuItem("Laporan Peserta");
    private final JMenuItem miLaporanPendaftaran = new JMenuItem("Laporan Pendaftaran");
    private final JMenu menuKeluar = new JMenu("Keluar");
    private final JMenuItem miLogout = new JMenuItem("Logout");
    private final JMenuItem miTutup = new JMenuItem("Tutup Aplikasi");

    // Popup menu (latihan RPS)
    private final JPopupMenu popupDashboard = new JPopupMenu();
    private final JMenuItem miTentang = new JMenuItem("Tentang Aplikasi");
    private final JMenuItem miRefresh = new JMenuItem("Refresh");

    private final JLabel lblUser = new JLabel("-");
    private final JLabel lblRole = new JLabel("-");

    public DashboardFrame() {
        setTitle("Dashboard - SIKURSUS Desktop");
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Dashboard = jendela utama
        buildUI();
        setIcons();
        setMinimumSize(new Dimension(760, 480));
        setSize(860, 540);
        setLocationRelativeTo(null);
        applySession();
    }

    private void buildUI() {
        Color header = new Color(173, 216, 230);
        Color latar = new Color(230, 230, 250);
        Color teks = new Color(65, 75, 95);

        // ----- susunan menu -----
        menuMaster.add(miPeserta);
        menuMaster.add(miInstruktur);
        menuMaster.add(miKursus);
        menuMaster.add(miKelas);
        menuTransaksi.add(miPendaftaran);
        menuTransaksi.add(miPembayaran);
        menuLaporan.add(miLaporanPeserta);
        menuLaporan.add(miLaporanPendaftaran);
        menuKeluar.add(miLogout);
        menuKeluar.addSeparator();
        menuKeluar.add(miTutup);
        menuBar.add(menuMaster);
        menuBar.add(menuTransaksi);
        menuBar.add(menuLaporan);
        menuBar.add(menuKeluar);
        setJMenuBar(menuBar);

        // ----- header -----
        JLabel lblJudul = new JLabel("SIKURSUS Desktop");
        lblJudul.setFont(new Font("SansSerif", Font.BOLD, 20));
        lblJudul.setForeground(teks);
        JPanel status = new JPanel();
        status.setOpaque(false);
        status.add(new JLabel("User:"));
        status.add(lblUser);
        status.add(new JLabel("| Role:"));
        status.add(lblRole);
        lblUser.setFont(lblUser.getFont().deriveFont(Font.BOLD));
        lblRole.setFont(lblRole.getFont().deriveFont(Font.BOLD));

        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(header);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
        panelHeader.add(lblJudul, BorderLayout.WEST);
        panelHeader.add(status, BorderLayout.EAST);

        // ----- area tengah + popup -----
        JLabel lblArea = new JLabel(
                "<html><div style='text-align:center'>Selamat datang di SIKURSUS Desktop"
                + "<br><br>Gunakan menu di atas untuk membuka modul."
                + "<br>Klik kanan di area ini untuk popup menu.</div></html>",
                SwingConstants.CENTER);
        lblArea.setForeground(teks);
        JPanel panelTengah = new JPanel(new BorderLayout());
        panelTengah.setBackground(latar);
        panelTengah.add(lblArea, BorderLayout.CENTER);

        popupDashboard.add(miTentang);
        popupDashboard.add(miRefresh);
        panelTengah.setComponentPopupMenu(popupDashboard);
        lblArea.setComponentPopupMenu(popupDashboard); // label menutupi panel
        lblArea.setInheritsPopupMenu(true);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelHeader, BorderLayout.NORTH);
        getContentPane().add(panelTengah, BorderLayout.CENTER);

        // ----- event -----
        miPeserta.addActionListener(e -> miPesertaActionPerformed());
        miInstruktur.addActionListener(e -> miInstrukturActionPerformed());
        miKursus.addActionListener(e -> miKursusActionPerformed());
        miKelas.addActionListener(e -> miKelasActionPerformed());
        miPendaftaran.addActionListener(e -> info(
                "Modul Pendaftaran sedang dikembangkan pada tahap project berikutnya."));
        miPembayaran.addActionListener(e -> info(
                "Modul Pembayaran sedang dikembangkan pada tahap project berikutnya."));
        miLaporanPeserta.addActionListener(e -> info(
                "Modul laporan diaktifkan pada Pertemuan 12 dengan Jaspersoft Studio."));
        miLaporanPendaftaran.addActionListener(e -> info(
                "Modul laporan diaktifkan pada Pertemuan 12 dengan Jaspersoft Studio."));
        miLogout.addActionListener(e -> miLogoutActionPerformed());
        miTutup.addActionListener(e -> miTutupActionPerformed());
        miTentang.addActionListener(e -> info(
                "SIKURSUS Desktop\nProject PBO - Program Studi Informatika"));
        miRefresh.addActionListener(e -> {
            applySession();
            repaint();
        });
    }

    private void info(String pesan) {
        JOptionPane.showMessageDialog(this, pesan);
    }

    // ----- session & role -----
    private void applySession() {
        User user = UserSession.getCurrentUser();
        if (user == null) {
            JOptionPane.showMessageDialog(this,
                    "Session tidak ditemukan. Silakan login ulang.");
            new LoginFrame().setVisible(true);
            dispose();
            return;
        }
        lblUser.setText(user.getUsername());
        lblRole.setText(user.getRole());

        boolean admin = user.isAdmin();
        miInstruktur.setEnabled(admin);
        miKursus.setEnabled(admin);
        miKelas.setEnabled(admin);
        miPeserta.setEnabled(true);
        menuTransaksi.setEnabled(true);
        menuLaporan.setEnabled(true);
    }

    // ----- icon dari resource project (bukan path komputer) -----
    private ImageIcon loadIcon(String path) {
        URL url = getClass().getResource(path);
        return url == null ? null : new ImageIcon(url);
    }

    private void setIcons() {
        miPeserta.setIcon(loadIcon("/icons/peserta.png"));
        miInstruktur.setIcon(loadIcon("/icons/instruktur.png"));
        miKursus.setIcon(loadIcon("/icons/kursus.png"));
        miKelas.setIcon(loadIcon("/icons/kelas.png"));
    }

    // ----- navigasi master -----
    private void miPesertaActionPerformed() {
        WindowManager.open(PesertaFrame.class, PesertaFrame::new);
    }

    private void miInstrukturActionPerformed() {
        WindowManager.open(InstrukturFrame.class, InstrukturFrame::new);
    }

    private void miKursusActionPerformed() {
        WindowManager.open(KursusFrame.class, KursusFrame::new);
    }

    private void miKelasActionPerformed() {
        WindowManager.open(KelasFrame.class, KelasFrame::new);
    }

    // ----- logout & tutup -----
    private void miLogoutActionPerformed() {
        int pilihan = JOptionPane.showConfirmDialog(this,
                "Logout dari aplikasi?", "Konfirmasi Logout",
                JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            WindowManager.closeAll();
            UserSession.clear();
            new LoginFrame().setVisible(true);
            dispose();
        }
    }

    private void miTutupActionPerformed() {
        int pilihan = JOptionPane.showConfirmDialog(this,
                "Tutup aplikasi SIKURSUS?", "Konfirmasi",
                JOptionPane.YES_NO_OPTION);
        if (pilihan == JOptionPane.YES_OPTION) {
            System.exit(0);
        }
    }
}

package view;

import dao.PesertaDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
import model.Peserta;
import report.ReportService;

public class MasterPeserta extends JFrame {

    private final Color warnaLatar = new Color(245, 238, 245);
    private final Color warnaHeader = new Color(136, 14, 79);
    private final Color warnaSimpan = new Color(136, 14, 79);
    private final Color warnaUbah = new Color(30, 136, 229);
    private final Color warnaHapus = new Color(229, 57, 53);
    private final Color warnaReset = new Color(230, 81, 0);
    private final Color warnaCetak = new Color(46, 125, 50);

    private JPanel panelUtama, panelHeader, panelForm, panelJenis, panelFasilitas, panelTombol, panelBawah, panelCari;
    private JLabel lblJudul, lblNim, lblNama, lblJenisKelamin, lblProdi, lblNoHp, lblEmail;
    private JLabel lblKodeAkses, lblKursus, lblFasilitas, lblRingkasan, lblCari;
    private JTextField txtNim, txtNama, txtProdi, txtNoHp, txtEmail, txtCari;
    private JRadioButton rbLaki, rbPerempuan;
    private ButtonGroup buttonGroupJenisKelamin;
    private JPasswordField txtKodeAkses;
    private JComboBox<String> cmbKursus;
    private JCheckBox cbModul, cbSertifikat, cbRekaman;
    private JTextArea txtRingkasan;
    private JScrollPane scrollRingkasan, scrollTable;
    private JButton btnSimpan, btnUbah, btnHapus, btnReset, btnCetakSemua, btnCari;
    private JTable tablePeserta;
    private DefaultTableModel tableModel;

    public MasterPeserta() {
        initComponents();
        buttonGroupJenisKelamin.add(rbLaki);
        buttonGroupJenisKelamin.add(rbPerempuan);
        txtRingkasan.setEditable(false);
        getRootPane().setDefaultButton(btnSimpan);

        setMinimumSize(new Dimension(800, 680));
        setSize(new Dimension(850, 720));
        setLocationRelativeTo(null);

        loadDataTabel("");
    }

    private void initComponents() {
        panelUtama = new JPanel(new BorderLayout());
        panelHeader = new JPanel(new BorderLayout());
        panelForm = new JPanel(new GridBagLayout());
        panelJenis = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelFasilitas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        panelTombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        panelBawah = new JPanel(new BorderLayout(0, 5));
        panelCari = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));

        lblJudul = new JLabel("Master Data Peserta", SwingConstants.CENTER);
        lblNim = new JLabel("Nim");
        lblNama = new JLabel("Nama Peserta");
        lblJenisKelamin = new JLabel("Jenis Kelamin");
        lblProdi = new JLabel("Program Studi");
        lblNoHp = new JLabel("Nomor Hp");
        lblEmail = new JLabel("Email");
        lblKodeAkses = new JLabel("Kode Akses");
        lblKursus = new JLabel("Kursus");
        lblFasilitas = new JLabel("Fasilitas");
        lblRingkasan = new JLabel("Ringkasan");
        lblCari = new JLabel("Pencarian");

        txtNim = new JTextField();
        txtNama = new JTextField();
        rbLaki = new JRadioButton("Laki-Laki");
        rbPerempuan = new JRadioButton("Perempuan");
        buttonGroupJenisKelamin = new ButtonGroup();
        txtProdi = new JTextField();
        txtNoHp = new JTextField();
        txtEmail = new JTextField();
        txtCari = new JTextField(20);

        txtKodeAkses = new JPasswordField();
        cmbKursus = new JComboBox<>(new String[]{
            "-Pilih Kursus-",
            "Java Desktop",
            "Data Science",
            "Web Programming",
            "UI/UX Design"
        });

        cbModul = new JCheckBox("Modul Cetak");
        cbSertifikat = new JCheckBox("Sertifikat");
        cbRekaman = new JCheckBox("Akses Rekaman");

        txtRingkasan = new JTextArea(2, 20);
        scrollRingkasan = new JScrollPane(txtRingkasan);

        btnSimpan = new JButton("Simpan");
        btnUbah = new JButton("Ubah");
        btnHapus = new JButton("Hapus");
        btnReset = new JButton("Reset");
        btnCetakSemua = new JButton("Cetak Semua");
        btnCari = new JButton("Cari");

        tableModel = new DefaultTableModel(new String[]{"NIM", "Nama", "JK", "Prodi", "No HP", "Email"}, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tablePeserta = new JTable(tableModel);
        scrollTable = new JScrollPane(tablePeserta);
        scrollTable.setPreferredSize(new Dimension(780, 110));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("SIKURSUS Desktop - Master Data Peserta");

        panelUtama.setBackground(warnaLatar);
        panelHeader.setBackground(warnaHeader);
        panelForm.setBackground(warnaLatar);
        panelJenis.setBackground(warnaLatar);
        panelFasilitas.setBackground(warnaLatar);
        panelTombol.setBackground(warnaLatar);
        panelBawah.setBackground(warnaLatar);
        panelCari.setBackground(warnaLatar);

        lblJudul.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblJudul.setForeground(Color.WHITE);
        lblJudul.setPreferredSize(new Dimension(100, 45));
        panelHeader.add(lblJudul, BorderLayout.CENTER);

        Font fontLabel = new Font("Segoe UI", Font.BOLD, 12);
        Color warnaTeksLabel = new Color(74, 20, 140);

        JLabel[] allLabels = {lblNim, lblNama, lblJenisKelamin, lblProdi, lblNoHp, lblEmail, lblKodeAkses, lblKursus, lblFasilitas, lblRingkasan, lblCari};
        for (JLabel lbl : allLabels) {
            lbl.setFont(fontLabel);
            lbl.setForeground(warnaTeksLabel);
        }

        rbLaki.setBackground(warnaLatar);
        rbPerempuan.setBackground(warnaLatar);
        cbModul.setBackground(warnaLatar);
        cbSertifikat.setBackground(warnaLatar);
        cbRekaman.setBackground(warnaLatar);

        panelJenis.add(rbLaki);
        panelJenis.add(rbPerempuan);
        panelFasilitas.add(cbModul);
        panelFasilitas.add(cbSertifikat);
        panelFasilitas.add(cbRekaman);

        setupButton(btnSimpan, warnaSimpan, Color.WHITE, 90, 30);
        setupButton(btnUbah, warnaUbah, Color.WHITE, 80, 30);
        setupButton(btnHapus, warnaHapus, Color.WHITE, 80, 30);
        setupButton(btnReset, warnaReset, Color.WHITE, 80, 30);
        setupButton(btnCetakSemua, warnaCetak, Color.WHITE, 110, 30);
        setupButton(btnCari, warnaUbah, Color.WHITE, 80, 28);

        panelTombol.add(btnSimpan);
        panelTombol.add(btnUbah);
        panelTombol.add(btnHapus);
        panelTombol.add(btnReset);
        panelTombol.add(btnCetakSemua);

        Dimension dimField = new Dimension(480, 26);
        txtNim.setPreferredSize(dimField);
        txtNama.setPreferredSize(dimField);
        txtProdi.setPreferredSize(dimField);
        txtNoHp.setPreferredSize(dimField);
        txtEmail.setPreferredSize(dimField);
        txtKodeAkses.setPreferredSize(dimField);
        cmbKursus.setPreferredSize(dimField);
        scrollRingkasan.setPreferredSize(new Dimension(480, 50));

        panelForm.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15));

        addRow(0, lblNim, txtNim);
        addRow(1, lblNama, txtNama);
        addRow(2, lblJenisKelamin, panelJenis);
        addRow(3, lblProdi, txtProdi);
        addRow(4, lblNoHp, txtNoHp);
        addRow(5, lblEmail, txtEmail);
        addRow(6, lblKodeAkses, txtKodeAkses);
        addRow(7, lblKursus, cmbKursus);
        addRow(8, lblFasilitas, panelFasilitas);
        addRow(9, lblRingkasan, scrollRingkasan);

        GridBagConstraints gbcTombol = new GridBagConstraints();
        gbcTombol.gridx = 0;
        gbcTombol.gridy = 10;
        gbcTombol.gridwidth = 2;
        gbcTombol.anchor = GridBagConstraints.CENTER;
        gbcTombol.insets = new Insets(8, 0, 5, 0);
        panelForm.add(panelTombol, gbcTombol);

        panelCari.add(lblCari);
        panelCari.add(txtCari);
        panelCari.add(btnCari);
        panelCari.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        panelBawah.add(panelCari, BorderLayout.NORTH);
        panelBawah.add(scrollTable, BorderLayout.CENTER);
        panelBawah.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(warnaHeader), " Data Peserta ",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                fontLabel, warnaHeader
        ));

        btnSimpan.addActionListener(evt -> btnSimpanActionPerformed());
        btnUbah.addActionListener(evt -> btnUbahActionPerformed());
        btnHapus.addActionListener(evt -> btnHapusActionPerformed());
        btnReset.addActionListener(evt -> btnResetActionPerformed());
        btnCetakSemua.addActionListener(evt -> btnCetakSemuaActionPerformed());
        btnCari.addActionListener(evt -> prosesCariData());
        txtCari.addActionListener(evt -> prosesCariData());

        tablePeserta.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tablePesertaMouseClicked();
            }
        });

        panelUtama.add(panelHeader, BorderLayout.NORTH);
        panelUtama.add(panelForm, BorderLayout.CENTER);
        panelUtama.add(panelBawah, BorderLayout.SOUTH);

        setContentPane(panelUtama);
    }

    private void setupButton(JButton btn, Color bg, Color fg, int width, int height) {
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 11));
        btn.setFocusPainted(false);
        btn.setPreferredSize(new Dimension(width, height));
    }

    private void addRow(int row, JLabel label, java.awt.Component component) {
        GridBagConstraints cLabel = new GridBagConstraints();
        cLabel.gridx = 0;
        cLabel.gridy = row;
        cLabel.anchor = GridBagConstraints.NORTHWEST;
        cLabel.insets = new Insets(3, 0, 3, 15);
        panelForm.add(label, cLabel);

        GridBagConstraints cField = new GridBagConstraints();
        cField.gridx = 1;
        cField.gridy = row;
        cField.weightx = 1.0;
        cField.fill = GridBagConstraints.HORIZONTAL;
        cField.anchor = GridBagConstraints.NORTHWEST;
        cField.insets = new Insets(2, 0, 2, 0);
        panelForm.add(component, cField);
    }

    private String getJenisKelamin() {
        if (rbLaki.isSelected()) return "L";
        if (rbPerempuan.isSelected()) return "P";
        return "";
    }

    private void clearForm() {
        txtNim.setText("");
        txtNim.setEditable(true);
        txtNama.setText("");
        buttonGroupJenisKelamin.clearSelection();
        txtProdi.setText("");
        txtNoHp.setText("");
        txtEmail.setText("");
        txtKodeAkses.setText("");
        cmbKursus.setSelectedIndex(0);
        cbModul.setSelected(false);
        cbSertifikat.setSelected(false);
        cbRekaman.setSelected(false);
        txtRingkasan.setText("");
        txtNim.requestFocus();
    }

    private void loadDataTabel(String keyword) {
        tableModel.setRowCount(0);
        // Aktifkan blok ini jika method getAll() / search() sudah ditambahkan di PesertaDAO
        /*
        PesertaDAO dao = new PesertaDAO();
        try {
            List<Peserta> listPeserta = (keyword == null || keyword.trim().isEmpty())
                    ? dao.getAll()
                    : dao.search(keyword);
            if (listPeserta != null) {
                for (Peserta p : listPeserta) {
                    tableModel.addRow(new Object[]{
                        p.getNim(),
                        p.getNama(),
                        p.getJenisKelamin(),
                        p.getProdi(),
                        p.getNoHp(),
                        p.getEmail()
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("Info Load Tabel: " + e.getMessage());
        }
        */
    }

    private void prosesCariData() {
        loadDataTabel(txtCari.getText().trim());
    }

    private void tablePesertaMouseClicked() {
        int row = tablePeserta.getSelectedRow();
        if (row != -1) {
            txtNim.setText(tableModel.getValueAt(row, 0).toString());
            txtNim.setEditable(false);
            txtNama.setText(tableModel.getValueAt(row, 1).toString());

            String jk = tableModel.getValueAt(row, 2).toString();
            if (jk.equalsIgnoreCase("L")) {
                rbLaki.setSelected(true);
            } else {
                rbPerempuan.setSelected(true);
            }

            txtProdi.setText(tableModel.getValueAt(row, 3).toString());
            txtNoHp.setText(tableModel.getValueAt(row, 4).toString());
            txtEmail.setText(tableModel.getValueAt(row, 5).toString());
        }
    }

    private void btnSimpanActionPerformed() {
        String nim = txtNim.getText().trim();
        String nama = txtNama.getText().trim();
        String prodi = txtProdi.getText().trim();
        String noHp = txtNoHp.getText().trim();
        String email = txtEmail.getText().trim();
        String jk = getJenisKelamin();

        if (nim.isEmpty() || nama.isEmpty() || prodi.isEmpty() || jk.isEmpty()) {
            JOptionPane.showMessageDialog(this, "NIM, Nama, Jenis Kelamin, dan Prodi wajib diisi!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (cmbKursus.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Silakan pilih kursus terlebih dahulu.", "Validasi Kursus", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Sesuai konstruktor Peserta: (int id, String nama, String noHp, String nim, String prodi, String jenisKelamin, String email)
        Peserta peserta = new Peserta(0, nama, noHp, nim, prodi, jk, email);
        PesertaDAO dao = new PesertaDAO();
        try {
            boolean berhasil = dao.insert(peserta);
            if (berhasil) {
                tampilkanRingkasan(nim, nama, jk, prodi, noHp, email);
                JOptionPane.showMessageDialog(this, "Data peserta berhasil disimpan.", "Sukses", JOptionPane.INFORMATION_MESSAGE);
                clearForm();
                loadDataTabel("");
            } else {
                JOptionPane.showMessageDialog(this, "Data gagal disimpan.", "Informasi", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Gagal menyimpan data:\n" + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void btnUbahActionPerformed() {
        String nim = txtNim.getText().trim();
        if (nim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data dari tabel terlebih dahulu untuk diubah!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Tambahkan method update() di PesertaDAO jika ingin mengaktifkan ubah.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
    }

    private void btnHapusActionPerformed() {
        String nim = txtNim.getText().trim();
        if (nim.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Pilih data peserta yang ingin dihapus dari tabel!", "Validasi", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int konfirmasi = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus peserta dengan NIM: " + nim + "?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
        if (konfirmasi == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, "Tambahkan method delete() di PesertaDAO jika ingin mengaktifkan hapus.", "Informasi", JOptionPane.INFORMATION_MESSAGE);
            clearForm();
            loadDataTabel("");
        }
    }

    private void btnResetActionPerformed() {
        int jawab = JOptionPane.showConfirmDialog(this, "Kosongkan seluruh data form?", "Konfirmasi Reset", JOptionPane.YES_NO_OPTION);
        if (jawab == JOptionPane.YES_OPTION) {
            clearForm();
        }
    }

    private void btnCetakSemuaActionPerformed() {
        ReportService.tampilkanLaporanPeserta();
    }

    private void tampilkanRingkasan(String nim, String nama, String jk, String prodi, String noHp, String email) {
        String kursusPilihan = cmbKursus.getSelectedItem().toString();
        String jkLengkap = jk.equals("L") ? "Laki-Laki" : "Perempuan";
        StringBuilder sb = new StringBuilder();
        sb.append("===== RINGKASAN DATA TERAKHIR =====\n");
        sb.append("NIM             : ").append(nim).append("\n");
        sb.append("Nama Peserta    : ").append(nama).append("\n");
        sb.append("Jenis Kelamin   : ").append(jkLengkap).append("\n");
        sb.append("Program Studi   : ").append(prodi).append("\n");
        sb.append("Nomor HP        : ").append(noHp).append("\n");
        sb.append("Email           : ").append(email).append("\n");
        sb.append("Kursus Diikuti  : ").append(kursusPilihan).append("\n");
        sb.append("Fasilitas Tambahan:\n");
        if (cbModul.isSelected()) sb.append(" - Modul Cetak\n");
        if (cbSertifikat.isSelected()) sb.append(" - Sertifikat\n");
        if (cbRekaman.isSelected()) sb.append(" - Akses Rekaman\n");
        if (!cbModul.isSelected() && !cbSertifikat.isSelected() && !cbRekaman.isSelected()) {
            sb.append(" - Tanpa Fasilitas Tambahan\n");
        }
        sb.append("=====================================\n");
        txtRingkasan.setText(sb.toString());
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            java.util.logging.Logger.getLogger(MasterPeserta.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        java.awt.EventQueue.invokeLater(() -> new MasterPeserta().setVisible(true));
    }
}
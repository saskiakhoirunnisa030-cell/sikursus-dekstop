
package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;

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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.UIManager;

import model.Peserta;
import dao.PesertaDAO;

public class FormPeserta extends JFrame {

    // =================================
    // TEMA WARNA FORM
    // =================================

    // Header: Baby Blue
    private final Color warnaHeader =
            new Color(173, 216, 230);

    // Bagian tengah: Lilac Mist
    private final Color warnaLatar =
            new Color(230, 230, 250);

    // Tombol Simpan: Powder Blue
    private final Color warnaSimpan =
            new Color(176, 224, 230);

    // Tombol Reset: Baby Pink
    private final Color warnaReset =
            new Color(255, 214, 231);

    // Warna teks dan border
    private final Color warnaTeks =
            new Color(65, 75, 95);

    private final Color warnaBorder =
            new Color(190, 185, 220);

    // =================================
    // KOMPONEN FORM
    // =================================

    private JPanel panelUtama;
    private JPanel panelHeader;
    private JPanel panelForm;
    private JPanel panelJenis;
    private JPanel panelFasilitas;
    private JPanel panelTombol;

    private JLabel lblJudul;
    private JLabel lblNim;
    private JLabel lblNama;
    private JLabel lblJenisKelamin;
    private JLabel lblProdi;
    private JLabel lblNoHp;
    private JLabel lblEmail;
    private JLabel lblKodeAkses;
    private JLabel lblKursus;
    private JLabel lblFasilitas;
    private JLabel lblRingkasan;

    private JTextField txtNim;
    private JTextField txtNama;
    private JRadioButton rbLaki;
    private JRadioButton rbPerempuan;
    private ButtonGroup buttonGroupJenisKelamin;
    private JTextField txtProdi;
    private JTextField txtNoHp;
    private JTextField txtEmail;

    private JPasswordField txtKodeAkses;

    private JComboBox<String> cmbKursus;

    private JCheckBox cbModul;
    private JCheckBox cbSertifikat;
    private JCheckBox cbRekaman;

    private JTextArea txtRingkasan;
    private JScrollPane scrollRingkasan;

    private JButton btnSimpan;
    private JButton btnReset;

    // =================================
    // KONSTRUKTOR
    // =================================

    public FormPeserta() {

        initComponents();

        buttonGroupJenisKelamin.add(rbLaki);
        buttonGroupJenisKelamin.add(rbPerempuan);

        txtRingkasan.setEditable(false);

        getRootPane().setDefaultButton(btnSimpan);

        setMinimumSize(new Dimension(800, 750));
        setSize(new Dimension(850, 800));
        setLocationRelativeTo(null);
    }

    // =================================
    // MEMBUAT KOMPONEN FORM
    // =================================

    private void initComponents() {

        panelUtama = new JPanel(new BorderLayout());
        panelHeader = new JPanel(new BorderLayout());
        panelForm = new JPanel(new GridBagLayout());

        panelJenis = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 14, 0));

        panelFasilitas = new JPanel(
                new FlowLayout(FlowLayout.LEFT, 14, 0));

        panelTombol = new JPanel(
                new FlowLayout(FlowLayout.CENTER, 24, 0));

        // LABEL
        lblJudul = new JLabel(
                "Form Data Peserta Kursus",
                SwingConstants.CENTER);

        lblNim = new JLabel("NIM");
        lblNama = new JLabel("Nama Peserta");
        lblJenisKelamin = new JLabel("Jenis Kelamin");
        lblProdi = new JLabel("Program Studi");
        lblNoHp = new JLabel("Nomor HP");
        lblEmail = new JLabel("Email");
        lblKodeAkses = new JLabel("Kode Akses");
        lblKursus = new JLabel("Kursus");
        lblFasilitas = new JLabel("Fasilitas");
        lblRingkasan = new JLabel("Ringkasan");

        // INPUT FIELD
        txtNim = new JTextField();
        txtNama = new JTextField();

        rbLaki = new JRadioButton("Laki-Laki");
        rbPerempuan = new JRadioButton("Perempuan");

        buttonGroupJenisKelamin = new ButtonGroup();

        txtProdi = new JTextField();
        txtNoHp = new JTextField();
        txtEmail = new JTextField();

        txtKodeAkses = new JPasswordField();

        // PILIHAN KURSUS
        cmbKursus = new JComboBox<>(new String[]{
            "-Pilih Kursus-",
            "Java Desktop",
            "Data Science",
            "Web Programming",
            "UI/UX Design"
        });

        // PILIHAN FASILITAS
        cbModul = new JCheckBox("Modul Cetak");
        cbSertifikat = new JCheckBox("Sertifikat");
        cbRekaman = new JCheckBox("Akses Rekaman");

        // RINGKASAN
        txtRingkasan = new JTextArea(8, 20);
        scrollRingkasan = new JScrollPane(txtRingkasan);

        // TOMBOL
        btnSimpan = new JButton("Simpan ke Database");
        btnReset = new JButton("RESET");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("SIKURSUS Desktop - Form Data Peserta");

        // =================================
        // WARNA PANEL
        // =================================

        panelUtama.setBackground(warnaLatar);

        // Header Baby Blue
        panelHeader.setBackground(warnaHeader);

        // Bagian tengah Lilac Mist
        panelForm.setBackground(warnaLatar);
        panelJenis.setBackground(warnaLatar);
        panelFasilitas.setBackground(warnaLatar);

        // Panel tombol mengikuti warna tengah
        panelTombol.setBackground(warnaLatar);

        // =================================
        // GAYA HEADER
        // =================================

        lblJudul.setFont(
                new Font("Segoe UI", Font.BOLD, 24));

        lblJudul.setForeground(
                new Color(45, 75, 95));

        lblJudul.setPreferredSize(
                new Dimension(100, 75));

        panelHeader.add(lblJudul, BorderLayout.CENTER);

        // =================================
        // GAYA LABEL
        // =================================

        Font fontLabel =
                new Font("Segoe UI", Font.BOLD, 14);

        JLabel[] allLabels = {
            lblNim, lblNama, lblJenisKelamin,
            lblProdi, lblNoHp, lblEmail,
            lblKodeAkses, lblKursus,
            lblFasilitas, lblRingkasan
        };

        for (JLabel lbl : allLabels) {
            lbl.setFont(fontLabel);
            lbl.setForeground(warnaTeks);
        }

        // =================================
        // GAYA RADIO BUTTON
        // =================================

        rbLaki.setBackground(warnaLatar);
        rbPerempuan.setBackground(warnaLatar);

        rbLaki.setForeground(warnaTeks);
        rbPerempuan.setForeground(warnaTeks);

        panelJenis.add(rbLaki);
        panelJenis.add(rbPerempuan);

        // =================================
        // GAYA CHECKBOX
        // =================================

        cbModul.setBackground(warnaLatar);
        cbSertifikat.setBackground(warnaLatar);
        cbRekaman.setBackground(warnaLatar);

        cbModul.setForeground(warnaTeks);
        cbSertifikat.setForeground(warnaTeks);
        cbRekaman.setForeground(warnaTeks);

        panelFasilitas.add(cbModul);
        panelFasilitas.add(cbSertifikat);
        panelFasilitas.add(cbRekaman);

        // =================================
        // GAYA INPUT FIELD
        // =================================

        Font fontInput =
                new Font("Segoe UI", Font.PLAIN, 14);

        JTextField[] fieldInput = {
            txtNim, txtNama, txtProdi,
            txtNoHp, txtEmail
        };

        for (JTextField field : fieldInput) {

            field.setFont(fontInput);
            field.setBackground(Color.WHITE);
            field.setForeground(warnaTeks);

            field.setBorder(
                BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(
                            warnaBorder, 1),
                    BorderFactory.createEmptyBorder(
                            5, 9, 5, 9)
                )
            );
        }

        // GAYA KODE AKSES
        txtKodeAkses.setFont(fontInput);
        txtKodeAkses.setBackground(Color.WHITE);
        txtKodeAkses.setForeground(warnaTeks);

        txtKodeAkses.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        warnaBorder, 1),
                BorderFactory.createEmptyBorder(
                        5, 9, 5, 9)
            )
        );

        // GAYA COMBOBOX
        cmbKursus.setFont(fontInput);
        cmbKursus.setBackground(Color.WHITE);
        cmbKursus.setForeground(warnaTeks);

        // =================================
        // GAYA RINGKASAN
        // =================================

        txtRingkasan.setFont(
                new Font("Consolas", Font.PLAIN, 13));

        txtRingkasan.setBackground(Color.WHITE);
        txtRingkasan.setForeground(warnaTeks);

        txtRingkasan.setBorder(
            BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        warnaBorder, 1),
                BorderFactory.createEmptyBorder(
                        8, 8, 8, 8)
            )
        );

        // =================================
        // GAYA TOMBOL SIMPAN
        // =================================

        btnSimpan.setBackground(warnaSimpan);
        btnSimpan.setForeground(
                new Color(38, 76, 105));

        btnSimpan.setFont(
                new Font("Segoe UI", Font.BOLD, 13));

        btnSimpan.setFocusPainted(false);
        btnSimpan.setOpaque(true);

        btnSimpan.setPreferredSize(
                new Dimension(190, 40));

        btnSimpan.setBorder(
                BorderFactory.createLineBorder(
                        new Color(140, 195, 205), 1));

        btnSimpan.addActionListener(
                evt -> btnSimpanActionPerformed());

        // =================================
        // GAYA TOMBOL RESET
        // =================================

        btnReset.setBackground(warnaReset);
        btnReset.setForeground(
                new Color(120, 65, 88));

        btnReset.setFont(
                new Font("Segoe UI", Font.BOLD, 13));

        btnReset.setFocusPainted(false);
        btnReset.setOpaque(true);

        btnReset.setPreferredSize(
                new Dimension(130, 40));

        btnReset.setBorder(
                BorderFactory.createLineBorder(
                        new Color(225, 160, 185), 1));

        btnReset.addActionListener(
                evt -> btnResetActionPerformed());

        panelTombol.add(btnSimpan);
        panelTombol.add(btnReset);

        // =================================
        // UKURAN KOMPONEN
        // =================================

        Dimension dimField =
                new Dimension(540, 34);

        txtNim.setPreferredSize(dimField);
        txtNama.setPreferredSize(dimField);
        txtProdi.setPreferredSize(dimField);
        txtNoHp.setPreferredSize(dimField);
        txtEmail.setPreferredSize(dimField);
        txtKodeAkses.setPreferredSize(dimField);
        cmbKursus.setPreferredSize(dimField);

        scrollRingkasan.setPreferredSize(
                new Dimension(540, 180));

        panelForm.setBorder(
                BorderFactory.createEmptyBorder(
                        20, 24, 20, 24));

        // =================================
        // MENYUSUN FORM
        // =================================

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

        GridBagConstraints tombolConstraints =
                new GridBagConstraints();

        tombolConstraints.gridx = 1;
        tombolConstraints.gridy = 10;
        tombolConstraints.weightx = 1.0;
        tombolConstraints.fill =
                GridBagConstraints.HORIZONTAL;

        tombolConstraints.anchor =
                GridBagConstraints.CENTER;

        tombolConstraints.insets =
                new Insets(24, 8, 0, 0);

        panelForm.add(panelTombol, tombolConstraints);

        panelUtama.add(panelHeader, BorderLayout.NORTH);
        panelUtama.add(panelForm, BorderLayout.CENTER);

        setContentPane(panelUtama);
    }

    // =================================
    // MENAMBAHKAN BARIS FORM
    // =================================

    private void addRow(
            int row,
            JLabel label,
            java.awt.Component component) {

        GridBagConstraints cLabel =
                new GridBagConstraints();

        cLabel.gridx = 0;
        cLabel.gridy = row;
        cLabel.anchor =
                GridBagConstraints.NORTHWEST;

        cLabel.insets =
                new Insets(10, 0, 10, 20);

        panelForm.add(label, cLabel);

        GridBagConstraints cField =
                new GridBagConstraints();

        cField.gridx = 1;
        cField.gridy = row;
        cField.weightx = 1.0;
        cField.fill =
                GridBagConstraints.HORIZONTAL;

        cField.anchor =
                GridBagConstraints.NORTHWEST;

        cField.insets =
                new Insets(6, 0, 6, 0);

        panelForm.add(component, cField);
    }

    // =================================
    // MENGAMBIL JENIS KELAMIN
    // =================================

    private String getJenisKelamin() {

        if (rbLaki.isSelected()) {
            return "L";
        }

        if (rbPerempuan.isSelected()) {
            return "P";
        }

        return "";
    }

    // =================================
    // MENGOSONGKAN FORM
    // =================================

    private void clearForm() {

        txtNim.setText("");
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

        txtNim.requestFocusInWindow();
    }

    // =================================
    // PROSES SIMPAN KE DATABASE
    // =================================

    private void btnSimpanActionPerformed() {

        String nim = txtNim.getText().trim();
        String nama = txtNama.getText().trim();
        String prodi = txtProdi.getText().trim();
        String noHp = txtNoHp.getText().trim();
        String email = txtEmail.getText().trim();

        String jk = getJenisKelamin();

        // VALIDASI INPUT DASAR
        if (nim.isEmpty()
                || nama.isEmpty()
                || prodi.isEmpty()
                || jk.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "NIM, nama, jenis kelamin, dan prodi wajib diisi.",
                    "Validasi",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        // VALIDASI KURSUS
        if (cmbKursus.getSelectedIndex() == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Silakan pilih kursus terlebih dahulu.",
                    "Validasi Kursus",
                    JOptionPane.WARNING_MESSAGE);

            return;
        }

        // MEMBUAT OBJEK PESERTA
        Peserta peserta = new Peserta(
                0, nama, noHp, nim, prodi, jk, email);

        PesertaDAO dao = new PesertaDAO();

        try {

            boolean berhasilSimpan = dao.insert(peserta);

            if (berhasilSimpan) {

                String kursusPilihan =
                        cmbKursus.getSelectedItem().toString();

                String jkLengkap =
                        jk.equals("L")
                        ? "Laki-Laki"
                        : "Perempuan";

                StringBuilder sb = new StringBuilder();

                sb.append(
                        "===== DATA PESERTA BERHASIL DISIMPAN =====\n");

                sb.append("NIM             : ")
                        .append(nim).append("\n");

                sb.append("Nama Peserta    : ")
                        .append(nama).append("\n");

                sb.append("Jenis Kelamin   : ")
                        .append(jkLengkap).append("\n");

                sb.append("Program Studi   : ")
                        .append(prodi).append("\n");

                sb.append("Nomor HP        : ")
                        .append(noHp).append("\n");

                sb.append("Email           : ")
                        .append(email).append("\n");

                sb.append("Kursus Diikuti  : ")
                        .append(kursusPilihan).append("\n");

                sb.append("Fasilitas Tambahan:\n");

                if (cbModul.isSelected()) {
                    sb.append(" - Modul Cetak\n");
                }

                if (cbSertifikat.isSelected()) {
                    sb.append(" - Sertifikat\n");
                }

                if (cbRekaman.isSelected()) {
                    sb.append(" - Akses Rekaman\n");
                }

                if (!cbModul.isSelected()
                        && !cbSertifikat.isSelected()
                        && !cbRekaman.isSelected()) {

                    sb.append(
                            " - Tanpa Fasilitas Tambahan\n");
                }

                sb.append(
                        "==============================================\n");

                txtRingkasan.setText(sb.toString());

                JOptionPane.showMessageDialog(
                        this,
                        "Data peserta berhasil disimpan.",
                        "Sukses",
                        JOptionPane.INFORMATION_MESSAGE);

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "Data tidak tersimpan.",
                        "Informasi",
                        JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Gagal menyimpan data:\n" + e.getMessage(),
                    "Database Error",
                    JOptionPane.ERROR_MESSAGE);

            e.printStackTrace();
        }
    }

    // =================================
    // TOMBOL RESET
    // =================================

    private void btnResetActionPerformed() {

        int jawab = JOptionPane.showConfirmDialog(
                this,
                "Kosongkan seluruh data form?",
                "Konfirmasi Reset",
                JOptionPane.YES_NO_OPTION);

        if (jawab == JOptionPane.YES_OPTION) {
            clearForm();
        }
    }

    // =================================
    // MAIN PROGRAM
    // =================================

    public static void main(String[] args) {

        try {

            for (UIManager.LookAndFeelInfo info
                    : UIManager.getInstalledLookAndFeels()) {

                if ("Nimbus".equals(info.getName())) {

                    UIManager.setLookAndFeel(
                            info.getClassName());

                    break;
                }
            }

        } catch (Exception ex) {

            java.util.logging.Logger.getLogger(
                    FormPeserta.class.getName()).log(
                    java.util.logging.Level.SEVERE,
                    null,
                    ex);
        }

        java.awt.EventQueue.invokeLater(() -> {
            new FormPeserta().setVisible(true);
        });
    }
}

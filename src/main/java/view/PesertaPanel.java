package view;

import dao.PesertaCrudDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.print.PrinterException;
import java.sql.SQLException;
import java.text.MessageFormat;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import model.Peserta;

/** Isi form Master Data Peserta: form input + tabel data + CRUD. */
public class PesertaPanel extends JPanel {

    private static final Color MAROON = new Color(128, 0, 64);
    private static final String[] KOLOM = {"NIM", "Nama", "JK", "Prodi", "No HP", "Email"};

    private final PesertaCrudDAO dao = new PesertaCrudDAO();

    private final JTextField txtNim = new JTextField();
    private final JTextField txtNama = new JTextField();
    private final JRadioButton rbLaki = new JRadioButton("Laki-Laki");
    private final JRadioButton rbPerempuan = new JRadioButton("Perempuan");
    private final ButtonGroup groupJk = new ButtonGroup();
    private final JTextField txtProdi = new JTextField();
    private final JTextField txtNoHp = new JTextField();
    private final JTextField txtEmail = new JTextField();
    private final JPasswordField txtKodeAkses = new JPasswordField();
    private final JComboBox<String> cmbKursus = new JComboBox<>(new String[]{
        "-Pilih Kursus-", "Java Desktop", "Data Science",
        "Web Programming", "UI/UX Design"});
    private final JCheckBox cbModul = new JCheckBox("Modul Cetak");
    private final JCheckBox cbSertifikat = new JCheckBox("Sertifikat");
    private final JCheckBox cbRekaman = new JCheckBox("Akses Rekaman");
    private final JTextArea txtRingkasan = new JTextArea(4, 20);

    private final JButton btnSimpan = new JButton("Simpan");
    private final JButton btnUbah = new JButton("Ubah");
    private final JButton btnHapus = new JButton("Hapus");
    private final JButton btnReset = new JButton("Reset");
    private final JButton btnCetak = new JButton("Cetak Semua");

    private final JTextField txtCari = new JTextField(32);
    private final JButton btnCari = new JButton("Cari");

    private final DefaultTableModel tableModel = new DefaultTableModel(KOLOM, 0) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JTable tblPeserta = new JTable(tableModel);

    /** NIM baris yang sedang dipilih di tabel (kunci untuk Ubah/Hapus). */
    private String nimTerpilih = null;

    public PesertaPanel() {
        super(new BorderLayout());
        add(buildHeader(), BorderLayout.NORTH);

        JPanel tengah = new JPanel(new BorderLayout());
        tengah.add(buildForm(), BorderLayout.NORTH);
        tengah.add(buildTabel(), BorderLayout.CENTER);
        add(tengah, BorderLayout.CENTER);

        groupJk.add(rbLaki);
        groupJk.add(rbPerempuan);
        txtRingkasan.setEditable(false);

        btnSimpan.addActionListener(e -> simpan());
        btnUbah.addActionListener(e -> ubah());
        btnHapus.addActionListener(e -> hapus());
        btnReset.addActionListener(e -> reset());
        btnCetak.addActionListener(e -> cetak());
        btnCari.addActionListener(e -> muatData());
        txtCari.addActionListener(e -> muatData());
        tblPeserta.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                isiFormDariTabel();
            }
        });
    }

    // ============================== TAMPILAN ==============================

    private Font tebal() {
        return UIManager.getFont("Label.font").deriveFont(Font.BOLD);
    }

    private JLabel label(String teks) {
        JLabel l = new JLabel(teks);
        l.setFont(tebal());
        l.setPreferredSize(new Dimension(123, 20));
        return l;
    }

    private JPanel buildHeader() {
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(MAROON);
        p.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        JLabel judul = new JLabel("Master Data Peserta", SwingConstants.CENTER);
        judul.setForeground(Color.WHITE);
        judul.setFont(tebal().deriveFont(18f));
        p.add(judul, BorderLayout.CENTER);
        return p;
    }

    private JPanel buildForm() {
        JPanel form = new JPanel(new GridBagLayout());
        form.setBorder(BorderFactory.createEmptyBorder(26, 159, 8, 153));

        rbLaki.setFont(tebal());
        rbPerempuan.setFont(tebal());
        cbModul.setFont(tebal());
        cbSertifikat.setFont(tebal());
        cbRekaman.setFont(tebal());

        JPanel panelJk = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 0));
        panelJk.setOpaque(false);
        panelJk.add(rbLaki);
        panelJk.add(rbPerempuan);

        JPanel panelFasilitas = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 0));
        panelFasilitas.setOpaque(false);
        panelFasilitas.add(cbModul);
        panelFasilitas.add(cbSertifikat);
        panelFasilitas.add(cbRekaman);

        int r = 0;
        tambahBaris(form, r++, "Nim", txtNim);
        tambahBaris(form, r++, "Nama Peserta", txtNama);
        tambahBaris(form, r++, "Jenis Kelamin", panelJk);
        tambahBaris(form, r++, "Program Studi", txtProdi);
        tambahBaris(form, r++, "Nomor Hp", txtNoHp);
        tambahBaris(form, r++, "Email", txtEmail);
        tambahBaris(form, r++, "Kode Akses", txtKodeAkses);
        tambahBaris(form, r++, "Kursus", cmbKursus);
        tambahBaris(form, r++, "Fasilitas", panelFasilitas);
        tambahBaris(form, r++, "Ringkasan", new JScrollPane(txtRingkasan));

        JPanel tombol = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        for (JButton b : new JButton[]{btnSimpan, btnUbah, btnHapus, btnReset, btnCetak}) {
            b.setFont(tebal());
            b.setMargin(new Insets(6, 22, 6, 22));
            tombol.add(b);
        }
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = r;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(22, 0, 24, 0);
        form.add(tombol, c);
        return form;
    }

    private void tambahBaris(JPanel p, int baris, String teks, java.awt.Component isi) {
        GridBagConstraints c = new GridBagConstraints();
        c.gridy = baris;
        c.insets = new Insets(7, 0, 7, 0);
        c.anchor = GridBagConstraints.WEST;

        c.gridx = 0;
        p.add(label(teks), c);

        c.gridx = 1;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        p.add(isi, c);
    }

    private JPanel buildTabel() {
        JPanel luar = new JPanel(new BorderLayout());
        TitledBorder judul = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(MAROON, 1), "Data Peserta");
        judul.setTitleColor(MAROON);
        judul.setTitleFont(tebal());
        luar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(0, 12, 12, 12), judul));

        JLabel lblCari = new JLabel("Pencarian");
        lblCari.setFont(tebal());
        btnCari.setFont(tebal());
        btnCari.setMargin(new Insets(6, 22, 6, 22));
        JPanel atas = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        atas.add(lblCari);
        atas.add(txtCari);
        atas.add(btnCari);

        tblPeserta.setRowHeight(20);
        tblPeserta.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        luar.add(atas, BorderLayout.NORTH);
        JScrollPane scrollTabel = new JScrollPane(tblPeserta);
        scrollTabel.setColumnHeaderView(tblPeserta.getTableHeader());
        luar.add(scrollTabel, BorderLayout.CENTER);
        return luar;
    }

    // ============================== DATA TABEL ==============================

    /** Mengisi tabel dengan baris [nim, nama, jk, prodi, no_hp, email]. */
    public void isiTabel(List<String[]> data) {
        tableModel.setRowCount(0);
        for (String[] baris : data) {
            tableModel.addRow(baris);
        }
    }

    /** Memuat data dari database (memakai kata kunci pencarian bila ada). */
    public void muatData() {
        try {
            String kunci = txtCari.getText().trim();
            isiTabel(kunci.isEmpty() ? dao.findAll() : dao.search(kunci));
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal memuat data peserta:\n" + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void isiFormDariTabel() {
        int baris = tblPeserta.getSelectedRow();
        if (baris < 0) {
            nimTerpilih = null;
            return;
        }
        nimTerpilih = String.valueOf(tableModel.getValueAt(baris, 0));
        txtNim.setText(nimTerpilih);
        txtNama.setText(String.valueOf(tableModel.getValueAt(baris, 1)));
        String jk = String.valueOf(tableModel.getValueAt(baris, 2));
        if ("L".equalsIgnoreCase(jk)) {
            rbLaki.setSelected(true);
        } else if ("P".equalsIgnoreCase(jk)) {
            rbPerempuan.setSelected(true);
        } else {
            groupJk.clearSelection();
        }
        txtProdi.setText(String.valueOf(tableModel.getValueAt(baris, 3)));
        txtNoHp.setText(String.valueOf(tableModel.getValueAt(baris, 4)));
        txtEmail.setText(String.valueOf(tableModel.getValueAt(baris, 5)));
        txtRingkasan.setText("");
    }

    // ============================== AKSI TOMBOL ==============================

    private String jenisKelamin() {
        if (rbLaki.isSelected()) {
            return "L";
        }
        if (rbPerempuan.isSelected()) {
            return "P";
        }
        return "";
    }

    /** Membaca & memvalidasi form. Mengembalikan null (dengan pesan) bila tidak valid. */
    private Peserta bacaForm(boolean wajibKursus) {
        String nim = txtNim.getText().trim();
        String nama = txtNama.getText().trim();
        String prodi = txtProdi.getText().trim();
        String noHp = txtNoHp.getText().trim();
        String email = txtEmail.getText().trim();
        String jk = jenisKelamin();

        if (nim.isEmpty() || nama.isEmpty() || prodi.isEmpty() || jk.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "NIM, nama, jenis kelamin, dan prodi wajib diisi.",
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        if (wajibKursus && cmbKursus.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this,
                    "Silakan pilih kursus terlebih dahulu.",
                    "Validasi Kursus", JOptionPane.WARNING_MESSAGE);
            return null;
        }
        try {
            return new Peserta(0, nama, noHp, nim, prodi, jk, email);
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(),
                    "Validasi", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    private void simpan() {
        Peserta p = bacaForm(true);
        if (p == null) {
            return;
        }
        try {
            if (dao.existsNim(p.getNim())) {
                JOptionPane.showMessageDialog(this,
                        "NIM " + p.getNim() + " sudah terdaftar.",
                        "Duplikat", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (dao.insert(p)) {
                String ringkasan = susunRingkasan(p);
                muatData();
                kosongkanInput();
                txtRingkasan.setText(ringkasan);
                JOptionPane.showMessageDialog(this,
                        "Data peserta berhasil disimpan.",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Data tidak tersimpan.",
                        "Informasi", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menyimpan data:\n" + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ubah() {
        if (nimTerpilih == null) {
            JOptionPane.showMessageDialog(this,
                    "Pilih data pada tabel terlebih dahulu.");
            return;
        }
        Peserta p = bacaForm(false);
        if (p == null) {
            return;
        }
        int jawab = JOptionPane.showConfirmDialog(this,
                "Ubah data peserta dengan NIM " + nimTerpilih + "?",
                "Konfirmasi Ubah", JOptionPane.YES_NO_OPTION);
        if (jawab != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            if (!p.getNim().equals(nimTerpilih) && dao.existsNim(p.getNim())) {
                JOptionPane.showMessageDialog(this,
                        "NIM " + p.getNim() + " sudah dipakai peserta lain.",
                        "Duplikat", JOptionPane.WARNING_MESSAGE);
                return;
            }
            if (dao.update(nimTerpilih, p)) {
                muatData();
                reset(false);
                JOptionPane.showMessageDialog(this,
                        "Data peserta berhasil diubah.",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Data tidak ditemukan atau tidak berubah.",
                        "Informasi", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal mengubah data:\n" + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void hapus() {
        if (nimTerpilih == null) {
            JOptionPane.showMessageDialog(this,
                    "Pilih data pada tabel terlebih dahulu.");
            return;
        }
        int jawab = JOptionPane.showConfirmDialog(this,
                "Hapus peserta dengan NIM " + nimTerpilih + "?",
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (jawab != JOptionPane.YES_OPTION) {
            return;
        }
        try {
            if (dao.deleteByNim(nimTerpilih)) {
                muatData();
                reset(false);
                JOptionPane.showMessageDialog(this,
                        "Data peserta berhasil dihapus.",
                        "Sukses", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this,
                        "Data tidak ditemukan.",
                        "Informasi", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal menghapus data:\n" + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void reset() {
        int jawab = JOptionPane.showConfirmDialog(this,
                "Kosongkan seluruh data form?", "Konfirmasi Reset",
                JOptionPane.YES_NO_OPTION);
        if (jawab == JOptionPane.YES_OPTION) {
            reset(true);
        }
    }

    /** Mengosongkan form dan ringkasan, lalu melepas pilihan baris tabel. */
    private void reset(boolean fokus) {
        kosongkanInput();
        txtRingkasan.setText("");
        tblPeserta.clearSelection();
        nimTerpilih = null;
        if (fokus) {
            txtNim.requestFocusInWindow();
        }
    }

    private void kosongkanInput() {
        txtNim.setText("");
        txtNama.setText("");
        groupJk.clearSelection();
        txtProdi.setText("");
        txtNoHp.setText("");
        txtEmail.setText("");
        txtKodeAkses.setText("");
        cmbKursus.setSelectedIndex(0);
        cbModul.setSelected(false);
        cbSertifikat.setSelected(false);
        cbRekaman.setSelected(false);
        tblPeserta.clearSelection();
        nimTerpilih = null;
    }

    private void cetak() {
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Tidak ada data untuk dicetak.");
            return;
        }
        try {
            boolean selesai = tblPeserta.print(JTable.PrintMode.FIT_WIDTH,
                    new MessageFormat("Data Peserta - SIKURSUS Desktop"),
                    new MessageFormat("Halaman {0}"));
            if (!selesai) {
                JOptionPane.showMessageDialog(this, "Pencetakan dibatalkan.");
            }
        } catch (PrinterException ex) {
            JOptionPane.showMessageDialog(this,
                    "Gagal mencetak:\n" + ex.getMessage(),
                    "Cetak", JOptionPane.ERROR_MESSAGE);
        }
    }

    /** Ringkasan seperti pada FormPeserta lama (kursus & fasilitas hanya tampil di form). */
    private String susunRingkasan(Peserta p) {
        StringBuilder sb = new StringBuilder();
        sb.append("===== DATA PESERTA BERHASIL DISIMPAN =====\n");
        sb.append("NIM             : ").append(p.getNim()).append("\n");
        sb.append("Nama Peserta    : ").append(p.getNama()).append("\n");
        sb.append("Jenis Kelamin   : ")
                .append("L".equals(p.getJenisKelamin()) ? "Laki-Laki" : "Perempuan")
                .append("\n");
        sb.append("Program Studi   : ").append(p.getProdi()).append("\n");
        sb.append("Nomor HP        : ").append(p.getNoHp()).append("\n");
        sb.append("Email           : ").append(p.getEmail()).append("\n");
        sb.append("Kursus Diikuti  : ").append(cmbKursus.getSelectedItem()).append("\n");
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
        if (!cbModul.isSelected() && !cbSertifikat.isSelected() && !cbRekaman.isSelected()) {
            sb.append(" - Tanpa Fasilitas Tambahan\n");
        }
        return sb.toString();
    }
}

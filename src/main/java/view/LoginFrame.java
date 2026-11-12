package view;

import dao.UserDAO;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import model.User;
import util.UserSession;

public class LoginFrame extends JFrame {

    private final UserDAO userDAO = new UserDAO();

    private final JLabel lblTitle = new JLabel("SIKURSUS Desktop", SwingConstants.CENTER);
    private final JLabel lblSub = new JLabel(
            "Sistem Informasi Manajemen Kursus & Pelatihan", SwingConstants.CENTER);
    private final JLabel lblInfo = new JLabel(
            "Masukkan akun SIKURSUS", SwingConstants.CENTER);
    private final JTextField txtUsername = new JTextField(18);
    private final JPasswordField pwdPassword = new JPasswordField(18);
    private final JButton btnLogin = new JButton("Login");
    private final JButton btnKeluar = new JButton("Keluar");

    public LoginFrame() {
        setTitle("Login - SIKURSUS Desktop");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        buildUI();
        pack();
        setResizable(false);
        setLocationRelativeTo(null);
        getRootPane().setDefaultButton(btnLogin); // tekan Enter = Login
    }

    private void buildUI() {
        Color header = new Color(173, 216, 230);
        Color latar = new Color(230, 230, 250);
        Color teks = new Color(65, 75, 95);

        JPanel panelHeader = new JPanel(new BorderLayout());
        panelHeader.setBackground(header);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(14, 20, 14, 20));
        lblTitle.setFont(new Font("SansSerif", Font.BOLD, 22));
        lblTitle.setForeground(teks);
        lblSub.setFont(new Font("SansSerif", Font.PLAIN, 12));
        lblSub.setForeground(teks);
        panelHeader.add(lblTitle, BorderLayout.CENTER);
        panelHeader.add(lblSub, BorderLayout.SOUTH);

        JPanel form = new JPanel(new GridBagLayout());
        form.setBackground(latar);
        form.setBorder(BorderFactory.createEmptyBorder(16, 24, 16, 24));
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0; c.gridy = 0; c.gridwidth = 2;
        lblInfo.setForeground(teks);
        form.add(lblInfo, c);

        c.gridwidth = 1; c.gridy = 1; c.gridx = 0;
        form.add(new JLabel("Username"), c);
        c.gridx = 1;
        form.add(txtUsername, c);

        c.gridy = 2; c.gridx = 0;
        form.add(new JLabel("Password"), c);
        c.gridx = 1;
        form.add(pwdPassword, c);

        JPanel tombol = new JPanel();
        tombol.setBackground(latar);
        btnLogin.setBackground(new Color(176, 224, 230));
        btnKeluar.setBackground(new Color(255, 214, 231));
        tombol.add(btnLogin);
        tombol.add(btnKeluar);
        c.gridy = 3; c.gridx = 0; c.gridwidth = 2;
        form.add(tombol, c);

        btnLogin.addActionListener(e -> btnLoginActionPerformed());
        btnKeluar.addActionListener(e -> System.exit(0));

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panelHeader, BorderLayout.NORTH);
        getContentPane().add(form, BorderLayout.CENTER);
    }

    private void btnLoginActionPerformed() {
        String username = txtUsername.getText().trim();
        char[] password = pwdPassword.getPassword();

        if (username.isEmpty() || password.length == 0) {
            JOptionPane.showMessageDialog(this,
                    "Username dan password wajib diisi.");
            Arrays.fill(password, '\0');
            return;
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        btnLogin.setEnabled(false);
        try {
            User user = userDAO.authenticate(username, password);
            if (user == null) {
                JOptionPane.showMessageDialog(this,
                        "Login gagal. Periksa username, password, atau status user.",
                        "Login Gagal", JOptionPane.WARNING_MESSAGE);
                pwdPassword.setText("");
                pwdPassword.requestFocus();
                return;
            }
            UserSession.setCurrentUser(user);
            new DashboardFrame().setVisible(true);
            dispose(); // LoginFrame tidak diperlukan lagi
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Tidak dapat mengakses database: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        } finally {
            Arrays.fill(password, '\0');
            btnLogin.setEnabled(true);
            setCursor(Cursor.getDefaultCursor());
        }
    }
}

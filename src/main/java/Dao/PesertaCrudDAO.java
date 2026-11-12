package dao;

import config.Database;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Peserta;

/**
 * Melengkapi PesertaDAO (yang sudah punya insert) dengan baca, cari, ubah, hapus.
 * PesertaDAO lama tidak diubah; class ini mewarisinya.
 */
public class PesertaCrudDAO extends PesertaDAO {

    private static final String KOLOM = "nim, nama, jenis_kelamin, prodi, no_hp, email";

    /** Baris tabel: [nim, nama, jk, prodi, no_hp, email] */
    public List<String[]> findAll() throws SQLException {
        return query(null);
    }

    public List<String[]> search(String keyword) throws SQLException {
        return query(keyword);
    }

    private List<String[]> query(String keyword) throws SQLException {
        boolean cari = keyword != null && !keyword.trim().isEmpty();
        String sql = "SELECT " + KOLOM + " FROM peserta";
        if (cari) {
            sql += " WHERE nim LIKE ? OR nama LIKE ? OR prodi LIKE ?"
                    + " OR no_hp LIKE ? OR email LIKE ?";
        }
        sql += " ORDER BY nim";

        List<String[]> hasil = new ArrayList<>();
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            if (cari) {
                String pola = "%" + keyword.trim() + "%";
                for (int i = 1; i <= 5; i++) {
                    ps.setString(i, pola);
                }
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    hasil.add(new String[]{
                        nvl(rs.getString("nim")),
                        nvl(rs.getString("nama")),
                        nvl(rs.getString("jenis_kelamin")),
                        nvl(rs.getString("prodi")),
                        nvl(rs.getString("no_hp")),
                        nvl(rs.getString("email"))
                    });
                }
            }
        }
        return hasil;
    }

    public boolean existsNim(String nim) throws SQLException {
        String sql = "SELECT 1 FROM peserta WHERE nim=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nim);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    /** Mengubah data peserta yang NIM-nya = nimLama. */
    public boolean update(String nimLama, Peserta p) throws SQLException {
        String sql = "UPDATE peserta SET nim=?, nama=?, jenis_kelamin=?, "
                + "prodi=?, no_hp=?, email=? WHERE nim=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, p.getNim());
            ps.setString(2, p.getNama());
            ps.setString(3, p.getJenisKelamin());
            ps.setString(4, p.getProdi());
            ps.setString(5, p.getNoHp());
            ps.setString(6, p.getEmail());
            ps.setString(7, nimLama);
            return ps.executeUpdate() == 1;
        }
    }

    public boolean deleteByNim(String nim) throws SQLException {
        String sql = "DELETE FROM peserta WHERE nim=?";
        try (Connection conn = Database.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, nim);
            return ps.executeUpdate() == 1;
        }
    }

    private static String nvl(String s) {
        return s == null ? "" : s;
    }
}

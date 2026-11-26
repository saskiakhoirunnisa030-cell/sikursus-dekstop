package report;

import config.Database;
import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class ReportService {

    public static void tampilkanLaporanPeserta() {
        String reportPath = "/report/laporan_peserta.jasper";

        try (Connection conn = Database.getConnection();
             InputStream reportStream = ReportService.class.getResourceAsStream(reportPath)) {

            if (conn == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Koneksi database gagal. Pastikan MySQL/XAMPP sudah aktif dan database db_sikursus tersedia.",
                        "Error Koneksi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (reportStream == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "File laporan tidak ditemukan di classpath: " + reportPath
                                + "\nPastikan laporan_peserta.jasper ada di src/main/resources/report/ lalu Clean and Build.",
                        "Error Report",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    reportStream,
                    new HashMap<>(),
                    conn
            );

            // Parameter kedua 'false' agar menutup JasperViewer tidak mematikan aplikasi utama
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Gagal menampilkan laporan: " + ex.getMessage(),
                    "Error Cetak",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void cetakPendaftaran(Integer idPendaftaran) {
        String reportPath = "/report/laporan_pendaftaran.jasper";

        try (Connection conn = Database.getConnection();
             InputStream reportStream = ReportService.class.getResourceAsStream(reportPath)) {

            if (conn == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Koneksi database gagal. Pastikan MySQL/XAMPP sudah aktif dan database db_sikursus tersedia.",
                        "Error Koneksi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (reportStream == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "File laporan tidak ditemukan di classpath: " + reportPath
                                + "\nPastikan laporan_pendaftaran.jasper ada di src/main/resources/report/ lalu Clean and Build.",
                        "Error Report",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Map<String, Object> params = new HashMap<>();
            params.put("P_ID_PENDAFTARAN", idPendaftaran);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    reportStream,
                    params,
                    conn
            );

            if (jasperPrint.getPages().isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Tidak ada data pendaftaran untuk ID tersebut.",
                        "Informasi",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            // Parameter kedua 'false' agar menutup JasperViewer tidak mematikan aplikasi utama
            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Gagal menampilkan laporan pendaftaran: " + ex.getMessage(),
                    "Error Cetak",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public static void cetakPembayaran(java.sql.Date tanggalAwal, java.sql.Date tanggalAkhir) {
        String reportPath = "/report/laporan_pembayaran.jasper";

        try (Connection conn = Database.getConnection();
             InputStream reportStream = ReportService.class.getResourceAsStream(reportPath)) {

            if (conn == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "Koneksi database gagal. Pastikan MySQL/XAMPP sudah aktif dan database db_sikursus tersedia.",
                        "Error Koneksi",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            if (reportStream == null) {
                JOptionPane.showMessageDialog(
                        null,
                        "File laporan tidak ditemukan di classpath: " + reportPath
                                + "\nPastikan laporan_pembayaran.jasper ada di src/main/resources/report/ lalu Clean and Build.",
                        "Error Report",
                        JOptionPane.ERROR_MESSAGE
                );
                return;
            }

            Map<String, Object> params = new HashMap<>();
            params.put("P_TANGGAL_AWAL", tanggalAwal);
            params.put("P_TANGGAL_AKHIR", tanggalAkhir);

            JasperPrint jasperPrint = JasperFillManager.fillReport(
                    reportStream,
                    params,
                    conn
            );

            if (jasperPrint.getPages().isEmpty()) {
                JOptionPane.showMessageDialog(
                        null,
                        "Tidak ada data pembayaran pada rentang tanggal tersebut.",
                        "Informasi",
                        JOptionPane.INFORMATION_MESSAGE
                );
                return;
            }

            JasperViewer.viewReport(jasperPrint, false);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(
                    null,
                    "Gagal menampilkan laporan pembayaran: " + ex.getMessage(),
                    "Error Cetak",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
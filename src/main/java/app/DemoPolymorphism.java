package app;

import model.Orang;
import model.Peserta;
import model.Instruktur;
import java.util.ArrayList; 
import java.util.List;

public class DemoPolymorphism {

    // Polymorphic argument
    public static void tampilkanInfo(Orang orang) {
        System.out.println(orang.getInfo());
    }

    public static void main(String[] args) {

        // 1. Membuat objek Peserta
        Peserta peserta = new Peserta(
                1,
                "Ahmad Fauzan",
                "081234567801",
                "252001",
                "Informatika"
        );

        // 2. Membuat objek Instruktur
        Instruktur instruktur = new Instruktur(
                2,
                "Dina Pratama",
                "081211110001",
                "Java Desktop"
        );

        // 3. Menguji overriding getInfo()
        System.out.println("=== OVERRIDING getInfo() ===");
        System.out.println(peserta.getInfo());
        System.out.println(instruktur.getInfo());

        // 4. Menguji polymorphism menggunakan List<Orang>
        List<Orang> daftarOrang = new ArrayList<>();
        daftarOrang.add(peserta);
        daftarOrang.add(instruktur);

        System.out.println("\n=== DAFTAR ORANG ===");

        for (Orang o : daftarOrang) {
            System.out.println(o.getInfo());
        }

        // 5. Menguji polymorphic argument
        System.out.println("\n=== POLYMORPHIC ARGUMENT ===");
        tampilkanInfo(peserta);
        tampilkanInfo(instruktur);

        // 6. Menguji instanceof dan casting aman
        System.out.println("\n=== UJI INSTANCEOF ===");

        for (Orang o : daftarOrang) {
            if (o instanceof Peserta) {
                Peserta p = (Peserta) o;
                System.out.println(
                        "NIM khusus peserta: " + p.getNim()
                );
            } else {
                System.out.println(
                        "Objek bukan Peserta: " + o.getNama()
                );
            }
        }

        // 7. Menguji validasi nama kosong
        System.out.println("\n=== UJI VALIDASI ===");

        try {
            new Peserta(
                    3,
                    "",
                    "081234567890",
                    "252002",
                    "Informatika"
            );
        } catch (IllegalArgumentException ex) {
            System.out.println(
                    "DITOLAK: " + ex.getMessage()
            );
        }

        // 8. Menguji validasi keahlian kosong
        try {
            new Instruktur(
                    4,
                    "Budi",
                    "081234567891",
                    ""
            );
        } catch (IllegalArgumentException ex) {
            System.out.println(
                    "DITOLAK: " + ex.getMessage()
            );
        }

        // 9. Menguji ID negatif
        try {
            new Orang(
                    -1,
                    "Andi",
                    "081234567892"
            );
        } catch (IllegalArgumentException ex) {
            System.out.println(
                    "DITOLAK: " + ex.getMessage()
            );
        }
    }
}
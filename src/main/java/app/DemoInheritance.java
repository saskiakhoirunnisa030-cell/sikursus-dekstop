package app;

import java.util.ArrayList;
import model.Orang;
import model.Peserta;
import model.Instruktur;

public class DemoInheritance {
    public static void main(String[] args) {
        ArrayList<Orang> daftarOrang = new ArrayList<>();

        daftarOrang.add(new Peserta(1, "Alya Rahma", "081234567890", "252001", "Informatika"));
        daftarOrang.add(new Peserta(2, "Rafi Akbar", "081298765432", "252002", "Informatika"));
        daftarOrang.add(new Instruktur(101, "Dina Pratama", "081211110001", "Java Desktop"));
        daftarOrang.add(new Instruktur(102, "Rizal Maulana", "081211110002", "Data Science"));

        System.out.println("=== DATA SIKURSUS ===");
        System.out.println("Jumlah Object: " + daftarOrang.size());
        System.out.println("--------------------------------------------------");
        
        for (Orang orang : daftarOrang) {
            System.out.println(orang.getInfo());
        }
    }
}
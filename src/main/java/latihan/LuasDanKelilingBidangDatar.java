package latihan;

public class LuasDanKelilingBidangDatar {

    public static void main(String[] args) {

        // Variabel
        String namaBidang = "Persegi Panjang";
        double panjang = 10;
        double lebar = 5;

        // Menghitung luas
        double luas = panjang * lebar;

        // Menghitung keliling
        double keliling = 2 * (panjang + lebar);

        // Output
        System.out.println("Nama Bidang : " + namaBidang);
        System.out.println("Panjang     : " + panjang + " cm");
        System.out.println("Lebar       : " + lebar + " cm");
        System.out.println("---------------------------");
        System.out.println("Luas        : " + luas + " cm²");
        System.out.println("Keliling    : " + keliling + " cm");
    }
}
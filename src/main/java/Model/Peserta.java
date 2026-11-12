
package model;

public class Peserta extends Orang {
    private String nim;
    private String prodi;
    private String jenisKelamin;
    private String email;

    // Constructor lama tetap dipertahankan
    public Peserta(int id, String nama, String noHp, String nim, String prodi) {
        super(id, nama, noHp);
        setNim(nim);
        setProdi(prodi);
        setJenisKelamin("");
        setEmail("");
    }

    // Constructor baru
    public Peserta(int id, String nama, String noHp,
                   String nim, String prodi,
                   String jenisKelamin, String email) {
        super(id, nama, noHp);
        setNim(nim);
        setProdi(prodi);
        setJenisKelamin(jenisKelamin);
        setEmail(email);
    }

    public String getNim() {
        return nim;
    }

    public final void setNim(String nim) {
        if (nim == null || nim.trim().isEmpty()) {
            throw new IllegalArgumentException("NIM wajib diisi");
        }
        this.nim = nim.trim();
    }

    public String getProdi() {
        return prodi;
    }

    public final void setProdi(String prodi) {
        if (prodi == null || prodi.trim().isEmpty()) {
            throw new IllegalArgumentException("Prodi wajib diisi");
        }
        this.prodi = prodi.trim();
    }

    public String getJenisKelamin() {
        return jenisKelamin;
    }

    public final void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin == null ? "" : jenisKelamin.trim();
    }

    public String getEmail() {
        return email;
    }

    public final void setEmail(String email) {
        this.email = email == null ? "" : email.trim();
    }

    @Override
    public String getInfo() {
        return "PESERTA | " + super.getInfo()
                + " | NIM: " + getNim()
                + " | Prodi: " + getProdi()
                + " | Jenis Kelamin: " + getJenisKelamin()
                + " | Email: " + getEmail();
    }
}

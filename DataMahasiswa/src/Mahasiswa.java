public class Mahasiswa {
    private String nim;
    private String nama;
    private String jenisKelamin;
    private int usia;
    private String kelas;

    public Mahasiswa(String nim, String nama, String jenisKelamin, int usia, String kelas) {
        this.nim = nim;
        this.nama = nama;
        this.jenisKelamin = jenisKelamin;
        this.usia = usia;
        this.kelas = kelas;
    }

    public void setNim(String nim) {
        this.nim = nim;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public void setJenisKelamin(String jenisKelamin) {
        this.jenisKelamin = jenisKelamin;
    }

    public void setUsia(int usia) {
        this.usia = usia;
    }

    public void setKelas(String kelas) {
        this.kelas = kelas;
    }

    public String getNim() {
        return this.nim;
    }

    public String getNama() {
        return this.nama;
    }

    public String getJenisKelamin() {
        return this.jenisKelamin;
    }

    public int getUsia() {
        return this.usia;
    }

    public String getKelas() {
        return this.kelas;
    }
}

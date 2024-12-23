import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class Penduduk {
    private String nik;  // Nomor Induk Kependudukan (NIK)
    private String nama; // Nama lengkap
    private String tempatTanggalLahir; // Tempat dan tanggal lahir
    private String jenisKelamin; // Jenis kelamin (L atau P)
    private String alamat; // Alamat tempat tinggal
    private String lastUpdated; // Waktu terakhir data diperbarui
    
    // Konstruktor untuk inisialisasi data penduduk
    public Penduduk(String nik, String nama, String ttl, String jenisKelamin, String alamat) {
        validateNik(nik); // Validasi NIK sebelum menyimpan
        this.nik = nik;
        this.nama = nama;
        this.tempatTanggalLahir = ttl;
        setJenisKelamin(jenisKelamin); // Set jenis kelamin dengan validasi
        this.alamat = alamat;
        this.lastUpdated = getCurrentTime(); // Set waktu update terakhir
    }
    
    // Validasi untuk memastikan NIK terdiri dari 16 digit angka
    private void validateNik(String nik) {
        if (nik == null || nik.length() != 16 || !nik.matches("\\d+")) {
            throw new IllegalArgumentException("NIK harus 16 digit angka");
        }
    }
    
    // Mendapatkan waktu saat ini dalam format "dd-MM-yyyy HH:mm:ss"
    private String getCurrentTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        return sdf.format(new Date());
    }
    
    // Getters untuk mendapatkan nilai atribut penduduk
    public String getNik() { return nik; }
    public String getNama() { return nama; }
    public String getTempatTanggalLahir() { return tempatTanggalLahir; }
    // Menentukan jenis kelamin berdasarkan nilai 'L' atau 'P'
    public String getJenisKelamin() { 
        return jenisKelamin.equalsIgnoreCase("L") ? "Laki-laki" : "Perempuan"; 
    }
    public String getAlamat() { return alamat; }
    public String getLastUpdated() { return lastUpdated; }

    // Setter untuk mengganti tempat dan tanggal lahir
    public void setTempatTanggalLahir(String ttl) {
        this.tempatTanggalLahir = ttl;
        this.lastUpdated = getCurrentTime(); // Set waktu update setiap kali data diubah
    }

    
    // Setter untuk mengganti jenis kelamin dengan validasi
    public void setJenisKelamin(String jenisKelamin) {
        // Validasi agar jenis kelamin hanya bisa 'L' atau 'P'
        if (jenisKelamin.equalsIgnoreCase("L") || jenisKelamin.equalsIgnoreCase("P")) {
            this.jenisKelamin = jenisKelamin.toUpperCase();
        } else {
            throw new IllegalArgumentException("Jenis kelamin harus 'P' atau 'L'.");
        }
        this.lastUpdated = getCurrentTime();  // Set waktu update setiap kali data diubah
    }

    
    // Metode abstrak yang harus diimplementasikan oleh subclass
    public abstract String getKategori();
    public abstract int getUmur();
    
     // Override toString untuk menampilkan informasi penduduk
    @Override
    public String toString() {
        return String.format("NIK: %s, Nama: %s, TTL: %s, Jenis Kelamin: %s, Alamat: %s, Kategori: %s, Update Terakhir: %s",
                nik, nama, tempatTanggalLahir, getJenisKelamin(), alamat, getKategori(), lastUpdated);
    }
}

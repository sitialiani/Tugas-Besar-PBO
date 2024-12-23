//class PendudukDewasa
import java.text.SimpleDateFormat;
import java.util.Date;

public class PendudukDewasa extends Penduduk {
    private String pekerjaan; // Pekerjaan dari penduduk dewasa
    private String statusPerkawinan; // Status perkawinan dari penduduk dewasa
    
   // Konstruktor untuk menginisialisasi data penduduk dewasa
    public PendudukDewasa(String nik, String nama, String ttl, String jenisKelamin, 
                         String alamat, String pekerjaan, String statusPerkawinan) {
        super(nik, nama, ttl, jenisKelamin, alamat); // Memanggil konstruktor superclass Penduduk
        this.pekerjaan = pekerjaan; // Inisialisasi pekerjaan
        this.statusPerkawinan = statusPerkawinan; // Inisialisasi status perkawinan
    }
    
    public String getPekerjaan() { return pekerjaan; } // Getter untuk mendapatkan pekerjaan
    public String getStatusPerkawinan() { return statusPerkawinan; } // Getter untuk mendapatkan status perkawinan
    
    // Override metode getKategori() untuk menentukan kategori penduduk
    @Override
    public String getKategori() {
        return "Dewasa"; // Kategori untuk objek ini adalah "Dewasa"
    }
    
    // Override metode getUmur() untuk menghitung umur berdasarkan tanggal lahir
    @Override
    public int getUmur() {
        try {
            String[] parts = getTempatTanggalLahir().split(", "); 
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // format tanggal yang digunakan
            Date birthDate = sdf.parse(parts[1]); // Mengonversi string tanggal lahir ke objek Date
            Date now = new Date();
             // Menghitung umur berdasarkan selisih waktu antara tanggal lahir dan tanggal sekarang
            return (int) ((now.getTime() - birthDate.getTime()) / (1000L * 60 * 60 * 24 * 365));
        } catch (Exception e) {
            return 0;  // Jika terjadi kesalahan, kembalikan 0 sebagai umur
        }
    }
    
    // Override metode toString() untuk menampilkan informasi penduduk dewasa dengan format tambahan
    @Override
    public String toString() {
        // Memanggil toString() dari superclass Penduduk dan menambahkan informasi pekerjaan dan status perkawinan
        return super.toString() + String.format(", Pekerjaan: %s, Status: %s", pekerjaan, statusPerkawinan);
    }

    public String getKategoriDewasa() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getKategoriDewasa'");
    }
}
//PendudukAnak
import java.text.SimpleDateFormat;
import java.util.Date;

public class PendudukAnak extends Penduduk {
    private String sekolah; // Nama sekolah tempat anak bersekolah
    private String namaWali; // Nama wali anak
    
    // Konstruktor untuk menginisialisasi data penduduk anak
    public PendudukAnak(String nik, String nama, String ttl, String jenisKelamin, 
                       String alamat, String sekolah, String namaWali) {
        super(nik, nama, ttl, jenisKelamin, alamat);  // Konstruktor untuk menginisialisasi data penduduk anak
        this.sekolah = sekolah;  // Inisialisasi nama sekolah
        this.namaWali = namaWali;  // Inisialisasi nama wali
    }
    
    public String getSekolah() { return sekolah; }  // Getter untuk mendapatkan nama sekolah
    public String getNamaWali() { return namaWali; }  // Getter untuk mendapatkan nama wali
    
     // Override metode getKategori() untuk menentukan kategori penduduk
    @Override
    public String getKategori() {
        return "Anak";  // Kategori untuk objek ini adalah "Anak"
    }
    
    // Override metode getUmur() untuk menghitung umur berdasarkan tanggal lahir
    @Override
    public int getUmur() {
        try {
            String[] parts = getTempatTanggalLahir().split(", ");  // Mendapatkan tanggal lahir dari tempat dan tanggal lahir yang dipisahkan oleh koma
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy"); // Format tanggal yang digunakan
            Date birthDate = sdf.parse(parts[1]); // Mengonversi string tanggal lahir ke objek Date
            Date now = new Date(); // Mendapatkan tanggal sekarang
            // Menghitung umur berdasarkan selisih waktu antara tanggal lahir dan tanggal sekarang
            return (int) ((now.getTime() - birthDate.getTime()) / (1000L * 60 * 60 * 24 * 365));
        } catch (Exception e) {
            return 0; // Jika terjadi kesalahan, kembalikan 0 sebagai umur
        }
        }

     // Override metode toString() untuk menampilkan informasi penduduk anak dengan format tambahan
    @Override
    public String toString() {
        // Memanggil toString() dari superclass Penduduk dan menambahkan informasi sekolah dan nama wali
        return super.toString() + String.format(", Sekolah: %s, Wali: %s", sekolah, namaWali);
    }
    // Metode ini belum diimplementasikan dan akan melemparkan exception jika dipanggil
    public String getKategoriAnak() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getKategoriAnak'");
    }
}
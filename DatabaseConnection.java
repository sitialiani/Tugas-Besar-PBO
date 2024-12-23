import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

// Kelas utama untuk mengelola koneksi dan operasi database
public class DatabaseConnection{

    static final String DB_URL = "jdbc:mysql://localhost:3306/data_penduduk";   // URL untuk koneksi ke database MySQL lokal
    static final String USER = "root";   // Nama pengguna database
    static final String PASSWORD = "password";  // Kata sandi pengguna database

     // Metode untuk mendapatkan koneksi ke database
     public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
    public static void main(String[] args) {
        // Blok try-with-resources untuk memastikan koneksi ditutup otomatis
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);  // Membuka koneksi ke database
             Statement stmt = conn.createStatement()) {  // Membuat objek Statement untuk menjalankan perintah SQL
            String sql = "CREATE DATABASE `data_penduduk`";  // Perintah SQL untuk membuat database 'data_penduduk'
            stmt.executeUpdate(sql);  // Menjalankan perintah SQL
            System.out.println("Database 'data_Penduduk' Created Successfully...");  // Menampilkan pesan sukses jika database berhasil dibuat
        } catch (SQLException e) {  // Menangkap dan mencetak kesalahan SQL jika ada
            e.printStackTrace();
        }
    }

   
}

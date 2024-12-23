//class Main, Kelas utama untuk menjalankan aplikasi sistem manajemen data penduduk
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);  // Scanner untuk membaca input dari pengguna
    private static PendudukService manajemen = new ManajemenPenduduk();  // Instance dari service manajemen penduduk

    public static void main(String[] args) {
        Main main = new Main(); // Membuat instance Main untuk memanggil metode non-static

         // Login sebelum masuk ke menu utama
         if (!main.login()) {
            System.out.println("Akses ditolak. Program akan keluar.");
            return;
        }

        try {
            while (true) {
                main.tampilkanMenu();  // Menampilkan menu utama
                int pilihan = scanner.nextInt();  // Membaca pilihan pengguna
                scanner.nextLine(); 

                // Menjalankan menu berdasarkan pilihan
                switch (pilihan) {
                    case 1:
                        tambahPenduduk();
                        break;
                    case 2:
                        hapusPenduduk();
                        break;
                    case 3:
                        updatePenduduk();
                        break;
                    case 4:
                        tampilkanSemuaPenduduk();
                        break;
                    case 5:
                        cariPenduduk();
                        break;
                    case 6:
                        tampilkanStatistik();
                        break;
                    case 7:
                        // Keluar dari program
                        System.out.println("-------Terima kasih telah menggunakan sistem manajemen data penduduk ini-------");
                        System.out.println();
                        System.out.println();
                        System.out.println("-------Program Dibuat Oleh Siti Aliani Husnah.F NIM 2311522006-------");
                        return;
                    default:
                        System.out.println("Pilihan tidak valid!");
                }
            }
        } catch (Exception e) {
            System.out.println("Terjadi kesalahan: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();  // Menutup scanner untuk menghindari kebocoran sumber daya
        }
    }

    // Metode untuk login ke sistem
    public boolean login() {
        final String USERNAME = "siti";
        final String PASSWORD = "Siti123";

        while (true) {
            System.out.println("\n=== LOGIN MASUK KE PROGRAM ===");

            // Input username
            System.out.print("Masukkan Username: ");
            String usernameInput = scanner.nextLine();

            // Input password
            System.out.print("Masukkan Password: ");
            String passwordInput = scanner.nextLine();

            // Generate captcha
            String captcha = generateCaptcha(6);
            System.out.println("Captcha: " + captcha);
            System.out.print("Masukkan Captcha: ");
            String captchaInput = scanner.nextLine();

            // Validasi login
            if (usernameInput.equalsIgnoreCase(USERNAME) && passwordInput.equals(PASSWORD) && captcha.equals(captchaInput)) {
                System.out.println("Login berhasil!");
                return true;
            } else {
                System.out.println("Login gagal! Periksa username, password, atau captcha kamu.");
                System.out.print("Coba lagi? (y/n): ");
                String retry = scanner.nextLine();
                if (!retry.equalsIgnoreCase("y")) {
                    return false;
                }
            }
        }
    }

    // Metode untuk menghasilkan captcha acak
    private static String generateCaptcha(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder captcha = new StringBuilder();
        Random random = new Random();

        // Menambahkan karakter acak ke captcha
        for (int i = 0; i < length; i++) {
            int index = random.nextInt(chars.length());
            captcha.append(chars.charAt(index));
        }

        return captcha.toString();
    }

    // Menampilkan menu utama
    public void tampilkanMenu() {
        System.out.println( "-------Selamat Datang di Sistem Manajemen Data Penduduk Kota Pariaman-------");
        System.out.println("Silahkan memilih menu dibawah untuk menjalankan sistem");
        System.out.println("1. Tambah Penduduk");
        System.out.println("2. Hapus Penduduk");
        System.out.println("3. Update Penduduk");
        System.out.println("4. Tampilkan Semua Penduduk");
        System.out.println("5. Cari Penduduk");
        System.out.println("6. Tampilkan Statistik");
        System.out.println("7. Keluar");
        System.out.print("Pilih menu (1-7): ");
    }

    // Menambahkan data penduduk baru
    private static void tambahPenduduk() throws SQLException {
        System.out.println("\n=== Tambah Data Penduduk ===");

       // Loop untuk validasi NIK
       String nik;
       while (true) {
           System.out.print("Masukkan NIK (16 digit): ");
           nik = scanner.nextLine();
           try {
               if (!nik.matches("\\d{16}")) {
                   throw new IllegalArgumentException("System mengalami Error: NIK harus berupa angka dan berjumlah 16 digit.");
               }
               break; // Input valid, keluar dari loop
           } catch (IllegalArgumentException e) {
               System.out.println(e.getMessage());
           }
       }

       // Input nama data penduduk 
        System.out.print("Masukkan Nama Penduduk: ");
        String nama = scanner.nextLine();
        System.out.print("Tempat, Tanggal Lahir (Kota, DD-MM-YYYY): ");
        String ttl = scanner.nextLine();

        
        // Loop untuk validasi Jenis Kelamin
        String jenisKelamin;
        while (true) {
            System.out.print("Masukkan Jenis Kelamin (L/P): ");
            jenisKelamin = scanner.nextLine().toUpperCase();
            try {
                if (!jenisKelamin.equals("L") && !jenisKelamin.equals("P")) {
                    throw new IllegalArgumentException("Error: Jenis kelamin harus 'L' atau 'P'.");
                }
                break; // Input valid, keluar dari loop
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage());
            }
        }
        
        //masukkan alamat penduduk
        System.out.print("Masukkan Alamat Tinggal: ");
        String alamat = scanner.nextLine();

        //input data tambahan berdasarkan kategori
        System.out.print("Pilih Kategori Penduduk (1: Dewasa, 2: Anak): ");
        int kategori = scanner.nextInt();
        scanner.nextLine();
      

        Penduduk penduduk;
        if (kategori == 1) {
            System.out.print("Pekerjaan: ");
            String pekerjaan = scanner.nextLine();
            System.out.print("Status Perkawinan: ");
            String status = scanner.nextLine();
            penduduk = new PendudukDewasa(nik, nama, ttl, jenisKelamin, alamat, pekerjaan, status);
        } else {
            System.out.print("Sekolah yang Ditempuh Saat Ini: ");
            String sekolah = scanner.nextLine();
            System.out.print("Nama Wali: ");
            String wali = scanner.nextLine();
            penduduk = new PendudukAnak(nik, nama, ttl, jenisKelamin, alamat, sekolah, wali);
        }
        // Menambahkan penduduk ke database
        manajemen.tambah(penduduk);
    }
    
    // Menghapus data penduduk berdasarkan NIK
    private static void hapusPenduduk() throws SQLException {
        System.out.print("\nMasukkan NIK penduduk yang akan dihapus: ");
        String nik = scanner.nextLine();
        System.out.print("Anda yakin ingin menghapus? (y/n): ");  // Konfirmasi penghapusan data
        if (scanner.nextLine().equalsIgnoreCase("y")) {
            manajemen.hapus(nik);
        }
    }

    // Memperbarui data penduduk berdasarkan NIK
    private static void updatePenduduk() throws SQLException {
        System.out.print("\nMasukkan NIK penduduk yang akan diupdate: ");
        String nik = scanner.nextLine();
        
        // Mencari data penduduk berdasarkan NIK
        Penduduk penduduk = manajemen.getById(nik);
        if (penduduk == null) {
            System.out.println("Maaf, NIK Penduduk tidak ditemukan!");
            return;
        }

        System.out.println("Data saat ini: " + penduduk);
        // Menampilkan opsi yang dapat diupdate
        System.out.println("\n=== Pilih Data yang Ingin di Update ===");
        System.out.println("1. Nama");
        System.out.println("2. TTL");
        System.out.println("3. Jenis Kelamin");
        System.out.println("4. Alamat");
        System.out.print("Pilih menu (1-4): ");
        
        int pilihan = scanner.nextInt();
        scanner.nextLine(); // Clear the newline character

        // Mendeklarasikan variabel untuk kolom yang akan diperbarui
        String kolom = ""; // Menyimpan kolom yang akan diperbarui
        String nilaiBaru = ""; // Menyimpan nilai baru

         // Mengatur kolom dan nilai baru berdasarkan pilihan
        switch (pilihan) {
            case 1:
                kolom = "nama";
                System.out.print("Masukkan nama baru: ");
                nilaiBaru = scanner.nextLine();
                break;
            case 2:
                kolom = "ttl";
                System.out.print("Masukkan tempat dan tanggal lahir baru (contoh: Padang, 01-01-2000): ");
                nilaiBaru = scanner.nextLine();
                break;
            case 3:
                kolom = "jenis_kelamin";
                System.out.print("Masukkan jenis kelamin baru (L/P): ");
                nilaiBaru = scanner.nextLine().toUpperCase();
                if (!nilaiBaru.equals("L") && !nilaiBaru.equals("P")) {
                    System.out.println("Jenis kelamin harus 'L' atau 'P'. Pembaruan dibatalkan.");
                    return;
                }
                break;
            case 4:
                kolom = "alamat";
                System.out.print("Masukkan alamat baru: ");
                nilaiBaru = scanner.nextLine();
                break;
            default:
                System.out.println("Pilihan kamu tidak valid. Pembaruan dibatalkan.");
                return;
        }

        // Memperbarui data di database
        String queryUpdate = "UPDATE penduduk SET " + kolom + " = ?, last_updated = CURRENT_TIMESTAMP WHERE nik = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement psUpdate = conn.prepareStatement(queryUpdate)) {
            psUpdate.setString(1, nilaiBaru);
            psUpdate.setString(2, nik);

            // Eksekusi pembaruan data
            int rowsAffected = psUpdate.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data berhasil diperbarui.");
            } else {
                System.out.println("Pembaruan gagal. Silakan coba lagi.");
            }
        }
    }
        
    // Menampilkan semua data penduduk
    private static void tampilkanSemuaPenduduk() throws SQLException {
        System.out.println("\n=== Data Semua Penduduk ===");
        // Mendapatkan semua data dari manajemen
        List<Penduduk> pendudukList = manajemen.getAll();
        if (pendudukList.isEmpty()) {
            System.out.println("Tidak ada data penduduk.");
        } else {
            for (Penduduk p : pendudukList) {  // Menampilkan data satu per satu
                System.out.println(p);
            }
        }
    }

     // Mencari data penduduk berdasarkan NIK atau nama
    private static void cariPenduduk() throws SQLException {
        System.out.print("\nMasukkan kata kunci pencarian (NIK/Nama): ");
        String keyword = scanner.nextLine();
        // Melakukan pencarian
        List<Penduduk> hasil = manajemen.cariPenduduk(keyword);
        if (hasil.isEmpty()) {
            System.out.println("Tidak ada hasil yang ditemukan.");
        } else {
            System.out.println("\nHasil pencarian:");
            for (Penduduk p : hasil) {
                System.out.println(p);
            }
        }
    }

    // Menampilkan statistik penduduk
    private static void tampilkanStatistik() throws SQLException {
        System.out.println("\n=== Statistik Penduduk ===");
        // Mendapatkan statistik dari manajemen
        Map<String, String> statistik = manajemen.getStatistikPenduduk();
        for (Map.Entry<String, String> entry : statistik.entrySet()) {
            System.out.printf("Jumlah %s: %s orang%n", 
                entry.getKey(), entry.getValue());
        }
    }
}
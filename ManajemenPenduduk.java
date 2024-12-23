import java.sql.*;
import java.util.*;

public class ManajemenPenduduk implements PendudukService {
    // Menambah data penduduk ke dalam database
    @Override
    public void tambah(Penduduk penduduk) throws SQLException {
        String query = "INSERT INTO penduduk (nik, nama, ttl, jenis_kelamin, alamat, kategori, pekerjaan, status_perkawinan, sekolah, nama_wali) " +
                      "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            // Mengatur parameter-parameter untuk query insert
            ps.setString(1, penduduk.getNik());
            ps.setString(2, penduduk.getNama());
            ps.setString(3, penduduk.getTempatTanggalLahir());
            ps.setString(4, penduduk.getJenisKelamin());
            ps.setString(5, penduduk.getAlamat());
            ps.setString(6, penduduk.getKategori());

            // Menangani kasus jika penduduk adalah Dewasa atau Anak
            if (penduduk instanceof PendudukDewasa) {
                PendudukDewasa pendudukDewasa = (PendudukDewasa) penduduk;
                ps.setString(7, pendudukDewasa.getPekerjaan());
                ps.setString(8, pendudukDewasa.getStatusPerkawinan());
                ps.setNull(9, Types.VARCHAR); // kosongkan kolom sekolah
                ps.setNull(10, Types.VARCHAR); // kosongkan kolom nama_wali
            } else if (penduduk instanceof PendudukAnak) {
                PendudukAnak pendudukAnak = (PendudukAnak) penduduk;
                ps.setNull(7, Types.VARCHAR); // kosongkan kolom pekerjaan
                ps.setNull(8, Types.VARCHAR); // kosongkan kolom status_perkawinan
                ps.setString(9, pendudukAnak.getSekolah());
                ps.setString(10, pendudukAnak.getNamaWali());
            }

            // Menjalankan query untuk menyimpan data
            ps.executeUpdate();
            System.out.println("Penduduk berhasil ditambahkan.");
        }
    }

    // Menghapus data penduduk berdasarkan NIK
    @Override
    public void hapus(String nik) throws SQLException {
        String query = "DELETE FROM penduduk WHERE nik = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nik);
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Penduduk berhasil dihapus.");
            } else {
                throw new SQLException("Penduduk dengan NIK " + nik + " tidak ditemukan.");
            }
        }
    }

    // Memperbarui data penduduk berdasarkan objek Penduduk yang diterima
    @Override
    public void update(Penduduk penduduk) throws SQLException {
        String query = "UPDATE penduduk SET ttl = ?, jenis_kelamin = ?, alamat = ?, kategori = ?, pekerjaan = ?, status_perkawinan = ?, sekolah = ?, nama_wali = ? WHERE nik = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            // Mengatur parameter-parameter untuk query update
            ps.setString(1, penduduk.getTempatTanggalLahir());
            ps.setString(2, penduduk.getJenisKelamin());
            ps.setString(3, penduduk.getAlamat());
            ps.setString(4, penduduk.getKategori());

            // Menangani kasus jika penduduk adalah Dewasa atau Anak
            if (penduduk instanceof PendudukDewasa) {
                PendudukDewasa pendudukDewasa = (PendudukDewasa) penduduk;
                ps.setString(5, pendudukDewasa.getPekerjaan());
                ps.setString(6, pendudukDewasa.getStatusPerkawinan());
                ps.setNull(7, Types.VARCHAR); // kosongkan kolom sekolah
                ps.setNull(8, Types.VARCHAR); // kosongkan kolom nama_wali
            } else if (penduduk instanceof PendudukAnak) {
                PendudukAnak pendudukAnak = (PendudukAnak) penduduk;
                ps.setNull(5, Types.VARCHAR); // kosongkan kolom pekerjaan
                ps.setNull(6, Types.VARCHAR); // kosongkan kolom status_perkawinan
                ps.setString(7, pendudukAnak.getSekolah());
                ps.setString(8, pendudukAnak.getNamaWali());
            }

            // Menentukan parameter untuk NIK yang akan diperbarui
            ps.setString(9, penduduk.getNik());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Data penduduk berhasil diperbarui.");
            } else {
                throw new SQLException("Penduduk dengan NIK " + penduduk.getNik() + " tidak ditemukan.");
            }
        }
    }

      // Mengambil data penduduk berdasarkan NIK
    @Override
    public Penduduk getById(String nik) throws SQLException {
        String query = "SELECT * FROM penduduk WHERE nik = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, nik);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return createPendudukFromResultSet(rs); // Membuat objek Penduduk dari hasil query
                }
            }
        }
        return null;  // Jika tidak ditemukan
    }

     // Mengambil semua data penduduk dari database
    @Override
    public List<Penduduk> getAll() throws SQLException {
        List<Penduduk> pendudukList = new ArrayList<>();
        String query = "SELECT * FROM penduduk";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                pendudukList.add(createPendudukFromResultSet(rs)); // Menambahkan penduduk ke dalam list
            }
        }
        return pendudukList;
    }

    // Membuat objek Penduduk dari hasil ResultSet
    private Penduduk createPendudukFromResultSet(ResultSet rs) throws SQLException {
        String kategori = rs.getString("kategori");
        String jenisKelamin = rs.getString("jenis_kelamin");
    
        // Pastikan hanya P atau L yang diterima
        if (jenisKelamin == null || (!jenisKelamin.equals("P") && !jenisKelamin.equals("L"))) {
            jenisKelamin = "L";  // Atau bisa ganti dengan default jika diperlukan
        }
    
        if ("Dewasa".equals(kategori)) {
            return new PendudukDewasa(
                rs.getString("nik"),
                rs.getString("nama"),
                rs.getString("ttl"),
                jenisKelamin,
                rs.getString("alamat"),
                rs.getString("pekerjaan"),
                rs.getString("status_perkawinan")
            );
        } else {
            return new PendudukAnak(
                rs.getString("nik"),
                rs.getString("nama"),
                rs.getString("ttl"),
                jenisKelamin,
                rs.getString("alamat"),
                rs.getString("sekolah"),
                rs.getString("nama_wali")
            );
        }
    }
    
    // Mengambil daftar penduduk berdasarkan kategori (Dewasa atau Anak)
    @Override
    public List<Penduduk> getByKategori(String kategori) throws SQLException {
        List<Penduduk> pendudukList = new ArrayList<>();
        String query = "SELECT * FROM penduduk WHERE kategori = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, kategori);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) { 
                    pendudukList.add(createPendudukFromResultSet(rs));  // Menambahkan penduduk ke dalam list
                }
            }
        }
        return pendudukList;
    }

     // Mengambil statistik jumlah penduduk berdasarkan kategori
    @Override
    public Map<String, String> getStatistikPenduduk() throws SQLException {
        Map<String, String> statistik = new HashMap<>();
        String query = "SELECT kategori, COUNT(*) as jumlah FROM penduduk GROUP BY kategori";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                statistik.put(rs.getString("kategori"), rs.getString("jumlah"));  // Menyimpan hasil statisti
            }
        }
        return statistik;
    }

    // Mencari penduduk berdasarkan keyword (NIK atau Nama)
    @Override
    public List<Penduduk> cariPenduduk(String keyword) throws SQLException {
        List<Penduduk> pendudukList = new ArrayList<>();
        String query = "SELECT * FROM penduduk WHERE nama LIKE ? OR nik LIKE ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            String searchPattern = "%" + keyword + "%";  // Mencocokkan nama atau nik dengan keyword
            ps.setString(1, searchPattern);
            ps.setString(2, searchPattern);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    pendudukList.add(createPendudukFromResultSet(rs));  // Menambahkan hasil pencarian ke dalam list
                }
            }
        }
        return pendudukList;
    }
}

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

// Interface PendudukService yang mengextends interface DataManagement untuk operasi data pada objek Penduduk
public interface PendudukService extends DataManagement<Penduduk> {
     
    // Mendapatkan daftar penduduk berdasarkan kategori tertentu (misalnya Dewasa atau Anak)
    List<Penduduk> getByKategori(String kategori) throws SQLException;
    
    // Mendapatkan statistik penduduk berdasarkan kategori, mengembalikan map dengan kategori sebagai key dan jumlah sebagai value
    Map<String, String> getStatistikPenduduk() throws SQLException;
    
     // Mencari penduduk berdasarkan keyword (bisa berupa nama atau NIK)
    List<Penduduk> cariPenduduk(String keyword) throws SQLException;
}
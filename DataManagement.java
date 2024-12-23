import java.sql.SQLException;
import java.util.List;

public interface DataManagement<T> {
     /**
     * Menambahkan data baru ke dalam sistem atau database.
     * @param data Data yang akan ditambahkan.
     * @throws SQLException Jika terjadi kesalahan saat operasi database.
     */
    void tambah(T data) throws SQLException;
   
    /**
     * Menghapus data berdasarkan ID tertentu.
     * @param id ID dari data yang akan dihapus.
     * @throws SQLException Jika terjadi kesalahan saat operasi database.
     */
    void hapus(String id) throws SQLException;
   
    /**
     * Memperbarui data yang ada dalam sistem atau database.
     * @param data Data yang diperbarui dengan informasi baru.
     * @throws SQLException Jika terjadi kesalahan saat operasi database.
     */
    void update(T data) throws SQLException;
    
    /**
     * Mengambil data berdasarkan ID tertentu.
     * @param id ID dari data yang ingin diambil.
     * @return Data yang sesuai dengan ID yang diberikan.
     * @throws SQLException Jika terjadi kesalahan saat operasi database.
     */
    T getById(String id) throws SQLException;

    /**
     * Mengambil semua data yang tersedia dalam sistem atau database.
     * @return Daftar semua data.
     * @throws SQLException Jika terjadi kesalahan saat operasi database.
     */
    List<T> getAll() throws SQLException;
}
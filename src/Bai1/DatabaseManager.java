package Bai1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

// Chế độ Auto-Commit mặc định
// Thiếu tính chất Atomicity

public class DatabaseManager {
    private static String URL = "jdbc:mysql://localhost:3306/bai1?CreateDatabaseIfNotExist=true";
    private static String USER = "root";
    private static String PASS = "LLL2006";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public void capPhatThuoc(int medicineId, int patientId) {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try{
            conn = DatabaseManager.getConnection();

            conn.setAutoCommit(false);

            String sqlUpdate = "UPDATE Medicine_Inventory SET quantity = quantity - 1 WHERE medicine_id = ?";
            ps1 = conn.prepareStatement(sqlUpdate);
            ps1.setInt(1, medicineId);
            ps1.executeUpdate();

            String sqlInsert = "INSERT INTO Prescription_History (patient_id, medicine_id, date) VALUES (?, ?, NOW())";
            ps2 = conn.prepareStatement(sqlInsert);
            ps2.setInt(1, patientId);
            ps2.setInt(2, medicineId);
            ps2.executeUpdate();

            conn.commit();
            System.out.println("Cấp phát thaành coong");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

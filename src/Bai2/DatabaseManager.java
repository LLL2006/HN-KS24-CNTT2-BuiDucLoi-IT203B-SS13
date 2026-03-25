package Bai2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// Vi phạm nguyên tắc Atomicity
// Quên rollback

public class DatabaseManager {
    private static String URL = "jdbc:mysql://localhost:3306/bai2?CreateDatabaseIfNotExist=true";
    private static String USER = "root";
    private static String PASS = "LLL2006";

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public void thanhToanVienPhi(int patientId, int invoiceId, double amount) {
        Connection conn = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;

        try{
            conn = DatabaseManager.getConnection();
            conn.setAutoCommit(false);

            String sqlDeductWallet = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            ps1 = conn.prepareStatement(sqlDeductWallet);
            ps1.setDouble(1, amount);
            ps1.setInt(2, patientId);
            ps1.executeUpdate();

            String sqlUpdateInvoice = "UPDATE Invoicesss SET status = 'PAID' WHERE invoice_id = ?";
            ps2 = conn.prepareStatement(sqlUpdateInvoice);
            ps2.setInt(1, invoiceId);
            ps2.executeUpdate();

            conn.commit();
            System.out.println("Thanh toán hoàn tất");
        } catch (SQLException e) {
            if(conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
 }

package BTTHTH;

import DAO.DatabaseConnectionManager;

import java.sql.*;

public class HospitalManagement {

    public void thucHienXuatVien(int maBN, double soTien) {
        Connection conn = null;
        PreparedStatement psInvoice = null;
        PreparedStatement psPatient = null;
        PreparedStatement psBed = null;

        try {
            conn = DatabaseConnectionManager.openConnection();
            if (conn == null) return;

            conn.setAutoCommit(false);

            String sql1 = "INSERT INTO INVOICES (MaBN, SoTien, NgayLap) VALUES (?, ?, NOW())";
            psInvoice = conn.prepareStatement(sql1);
            psInvoice.setInt(1, maBN);
            psInvoice.setDouble(2, soTien);
            psInvoice.executeUpdate();

            String sql2 = "UPDATE PATIENTS SET TrangThai = 'Đã xuất viện' WHERE MaBN = ?";
            psPatient = conn.prepareStatement(sql2);
            psPatient.setInt(1, maBN);
            psPatient.executeUpdate();

            String sql3 = "UPDATE BEDS SET MaBN = NULL, TrangThai = 'Trống' WHERE MaBN = ?";
            psBed = conn.prepareStatement(sql3);
            psBed.setInt(1, maBN);
            psBed.executeUpdate();

            conn.commit();
            System.out.println("THÀNH CÔNG: Bệnh nhân " + maBN + " đã hoàn tất thủ tục xuất viện.");

        } catch (SQLException e) {
            System.err.println("LỖI HỆ THỐNG: " + e.getMessage());
            try {
                if (conn != null) {
                    System.out.println("Đang thực hiện Rollback... Toàn bộ thay đổi đã bị hủy bỏ.");
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
                if (psInvoice != null) psInvoice.close();
                if (psPatient != null) psPatient.close();
                if (psBed != null) psBed.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

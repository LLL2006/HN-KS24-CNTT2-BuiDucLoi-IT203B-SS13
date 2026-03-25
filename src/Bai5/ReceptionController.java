package Bai5;

import DAO.DatabaseConnectionManager;

import java.sql.*;

public class ReceptionController {
    public void tiepNhanNoiTru(String ten, int tuoi, int maGiuong, double tien) {
        Connection conn = null;
        try {
            conn = DatabaseConnectionManager.openConnection();
            conn.setAutoCommit(false);

            String sql1 = "INSERT INTO BenhNhan(ten_bn, tuoi, ma_giuong) VALUES(?,?,?)";
            PreparedStatement ps1 = conn.prepareStatement(sql1, Statement.RETURN_GENERATED_KEYS);
            ps1.setString(1, ten);
            ps1.setInt(2, tuoi);
            ps1.setInt(3, maGiuong);
            ps1.executeUpdate();

            ResultSet rs = ps1.getGeneratedKeys();
            int maBN = rs.next() ? rs.getInt(1) : 0;

            String sql2 = "UPDATE GiuongBenh SET trang_thai = 'CO_NGUOI' WHERE ma_giuong = ? AND trang_thai = 'TRONG'";
            PreparedStatement ps2 = conn.prepareStatement(sql2);
            ps2.setInt(1, maGiuong);
            if (ps2.executeUpdate() == 0) throw new Exception("Giường đã có người hoặc không tồn tại!");

            String sql3 = "INSERT INTO TaiChinh(ma_bn, so_tien_tam_ung) VALUES(?,?)";
            PreparedStatement ps3 = conn.prepareStatement(sql3);
            ps3.setInt(1, maBN);
            ps3.setDouble(2, tien);
            ps3.executeUpdate();

            conn.commit();
            System.out.println("Tiếp nhận thành công! Bệnh nhân đã có giường.");

        } catch (Exception e) {
            try { if (conn != null) conn.rollback(); } catch (SQLException ex) {}
            System.err.println("LỖI HỆ THỐNG: " + e.getMessage() + ". Đã hoàn tác giao dịch.");
        }
    }
}

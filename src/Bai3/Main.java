package Bai3;

import DAO.DatabaseConnectionManager;

import java.sql.*;

public class Main {

    public void xuatVienVaThanhToan(int maBenhNhan, double tienVienPhi) {
        Connection conn = null;
        PreparedStatement psCheck = null;
        PreparedStatement psUpdateWallet = null;
        PreparedStatement psUpdateBed = null;
        PreparedStatement psUpdatePatient = null;

        try {
            conn = DatabaseConnectionManager.openConnection();
            if (conn == null) return;

            conn.setAutoCommit(false);

            String sqlCheck = "SELECT balance FROM Patient_Wallet WHERE patient_id = ?";
            psCheck = conn.prepareStatement(sqlCheck);
            psCheck.setInt(1, maBenhNhan);
            ResultSet rs = psCheck.executeQuery();

            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if (currentBalance < tienVienPhi) {
                    throw new Exception("Tài khoản bệnh nhân không đủ tiền (Thiếu: " + (tienVienPhi - currentBalance) + ")");
                }
            } else {
                throw new Exception("Không tìm thấy thông tin ví của bệnh nhân ID: " + maBenhNhan);
            }

            String sql1 = "UPDATE Patient_Wallet SET balance = balance - ? WHERE patient_id = ?";
            psUpdateWallet = conn.prepareStatement(sql1);
            psUpdateWallet.setDouble(1, tienVienPhi);
            psUpdateWallet.setInt(2, maBenhNhan);
            int row1 = psUpdateWallet.executeUpdate();
            if (row1 == 0) throw new Exception("Lỗi trừ tiền - Bệnh nhân không tồn tại!");

            String sql2 = "UPDATE Beds SET status = 'EMPTY' WHERE patient_id = ?";
            psUpdateBed = conn.prepareStatement(sql2);
            psUpdateBed.setInt(1, maBenhNhan);
            int row2 = psUpdateBed.executeUpdate();
            if (row2 == 0) throw new Exception("Lỗi giải phóng giường - Không tìm thấy giường của bệnh nhân!");

            String sql3 = "UPDATE Patients SET status = 'DISCHARGED' WHERE id = ?";
            psUpdatePatient = conn.prepareStatement(sql3);
            psUpdatePatient.setInt(1, maBenhNhan);
            int row3 = psUpdatePatient.executeUpdate();
            if (row3 == 0) throw new Exception("Lỗi trạng thái - Không thể cập nhật trạng thái xuất viện!");

            conn.commit();
            System.out.println("THÀNH CÔNG: Bệnh nhân đã hoàn tất thủ tục xuất viện.");

        } catch (Exception e) {
            System.err.println("THẤT BẠI: " + e.getMessage());
            try {
                if (conn != null) {
                    System.out.println("Hệ thống đang thực hiện Rollback");
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
                if (psCheck != null) psCheck.close();
                if (psUpdateWallet != null) psUpdateWallet.close();
                if (psUpdateBed != null) psUpdateBed.close();
                if (psUpdatePatient != null) psUpdatePatient.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

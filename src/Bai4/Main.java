package Bai4;

public class Main {
//    public List<BenhNhanDTO> getDashboardData() {
//
//        Map<Integer, BenhNhanDTO> mapBenhNhan = new LinkedHashMap<>();
//
//        String sql = "SELECT bn.id, bn.ten, dv.id_dv, dv.ten_dv, dv.gia " +
//                "FROM BenhNhan bn " +
//                "LEFT JOIN DichVuSuDung dv ON bn.id = dv.maBenhNhan " +
//                "ORDER BY bn.id"; // Gom nhóm bệnh nhân lại gần nhau
//
//        try (Connection conn = DatabaseConnectionManager.openConnection();
//             PreparedStatement pstmt = conn.prepareStatement(sql);
//             ResultSet rs = pstmt.executeQuery()) {
//
//            while (rs.next()) {
//                int patientId = rs.getInt("id");
//
//
//                BenhNhanDTO dto = mapBenhNhan.get(patientId);
//                if (dto == null) {
//                    dto = new BenhNhanDTO();
//                    dto.setId(patientId);
//                    dto.setTen(rs.getString("ten"));
//                    dto.setDsDichVu(new ArrayList<>());
//                    mapBenhNhan.put(patientId, dto);
//                }
//
//
//                int serviceId = rs.getInt("id_dv");
//                if (!rs.wasNull()) {
//
//                    DichVu dv = new DichVu();
//                    dv.setId(serviceId);
//                    dv.setTenDichVu(rs.getString("ten_dv"));
//                    dv.setGia(rs.getDouble("gia"));
//
//                    dto.getDsDichVu().add(dv); // Map dịch vụ vào đúng bệnh nhân
//                }
//                // Nếu rs.wasNull() == true, list dsDichVu vẫn sẽ trống, không bị mất bệnh nhân.
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return new ArrayList<>(mapBenhNhan.values());
//    }
}

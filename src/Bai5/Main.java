package Bai5;

import java.util.Scanner;

public class Main {
    private Scanner sc = new Scanner(System.in);
    private ReceptionController controller = new ReceptionController();

    public void displayMenu() {
        while (true) {
            System.out.println("1. Xem giường trống");
            System.out.println("2. Tiếp nhận bệnh nhân mới (1 chạm)");
            System.out.println("3. Thoát");
            System.out.print("Chọn chức năng: ");

            String choice = sc.nextLine();
            switch (choice) {
                case "1": // Gọi hàm in danh sách giường
                    break;
                case "2":
                    handleInput();
                    break;
                case "3":
                    System.exit(0);
                default:
                    System.out.println("⚠Vui lòng chọn lại!");
            }
        }
    }

    private void handleInput() {
        try {
            System.out.print("Nhập tên BN: "); String ten = sc.nextLine();
            System.out.print("Nhập tuổi: "); int tuoi = Integer.parseInt(sc.nextLine());
            System.out.print("Mã giường: "); int giuong = Integer.parseInt(sc.nextLine());
            System.out.print("Tiền tạm ứng: "); double tien = Double.parseDouble(sc.nextLine());

            controller.tiepNhanNoiTru(ten, tuoi, giuong, tien);
        } catch (NumberFormatException e) {
            System.err.println("Lỗi: Tuổi, Mã giường, Số tiền phải là con số!");
        }
    }
}
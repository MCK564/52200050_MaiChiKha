package com.lab020;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.lab020.models.student;
import com.lab020.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.util.Scanner;

@RequiredArgsConstructor
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.println("---------------------------------------o0o---------------------------------------");
			System.out.println(
					".-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-\n"+
					"| 1. Hiển thị danh sách sinh viên.                  |\n" +
					"| 2. Hiển thị danh sách sinh viên theo MSSV         |\n" +
					"| 3. Thêm Sinh viên với MSSV tự động (1,2,...)      |\n" +
					"| 4. Cập nhật thông tin SV                          |\n" +
					"| 5. Xóa SV                                         |\n" +
							"| 6. Thoát                                          |\n" +
							".-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-.-"
					);
			System.out.print("Mời bạn nhập lựa chọn: ");
			int selectNumber = sc.nextInt();
            switch (selectNumber) {
                case 1 -> StudentService.showAllStudent();
                case 2 -> {
						System.out.print("Nhập vào chính xác mssv cần tìm: ");
						sc.nextLine();
						String mssv = sc.nextLine();
						StudentService.showStudentByMssv(Long.parseLong(mssv));
                }
                case 3 -> {
					   sc.nextLine();
					   System.out.println("Để thêm sinh viên mới yêu cầu nhập các thông tin sau: ");
					   System.out.print("Nhập giới tính: ");
					   String sex = sc.nextLine();
					   System.out.print("Nhập tuổi: ");
					   String age = sc.nextLine();
					   System.out.print("Nhập điểm trung bình: ");
					   String average = sc.nextLine();
					   try{
						   StudentService.createNewStudent(student.builder()
								   .sex(sex)
								   .age(Integer.parseInt(age))
								   .average(Double.parseDouble(average))
								   .build()
						   );
					   }catch(Exception e){
						   System.out.println(e.getMessage());
					   }

                }
                case 4 -> {
					sc.nextLine();
					System.out.println("Để cập nhật sinh viên mới yêu cầu nhập các thông tin sau: ");
					System.out.print("Nhập mssv cần cập nhật: ");
					String mssv = sc.nextLine();
					System.out.print("Nhập giới tính: ");
					String sex = sc.nextLine();
					System.out.print("Nhập tuổi: ");
					String age = sc.nextLine();
					System.out.print("Nhập điểm trung bình: ");
					String average = sc.nextLine();
					try{
						StudentService.updateStudentByMssv(student.builder()
										.id(Long.parseLong(mssv))
								.sex(sex)
								.age(Integer.parseInt(age))
								.average(Double.parseDouble(average))
								.build()
						);
					}catch(Exception e){
						System.out.println(e.getMessage());
					}
                }
                case 5 -> {
					sc.nextLine();
					System.out.print("Nhập vào mssv cần xóa: ");
					String mssv = sc.nextLine();
					StudentService.deleteStudent(Long.parseLong(mssv));
                }
                case 6 -> {
                    System.out.println("Chương trình kết thúc");
                    return;
                }
            }

		}
	}

}

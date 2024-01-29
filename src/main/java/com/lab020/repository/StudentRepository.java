package com.lab020.repository;

import com.lab020.Utils.ConnectionUtil;
import com.lab020.models.student;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


@Repository
@RequiredArgsConstructor
public class StudentRepository {
    private final static String queryAll = "SELECT * FROM student.SinhVien sv WHERE 1=1";
    private final static String queryByMssv = "SELECT * FROM student.SinhVien sv WHERE sv.MSSV = ";
    private final static String create = "INSERT INTO student.SinhVien(GTINH,TUOI,DTB) VALUES (?,?,?)";
    private final static String updateByMssv = "UPDATE student.SinhVien SET GTINH = ?, TUOI = ? , DTB = ? WHERE MSSV = ? ";
    private final static String deleteByMssv = "DELETE FROM student.SinhVien sv WHERE sv.MSSV = ? ";


    public static List<student> findAllStudent (){
        List<student> students = new ArrayList<>();
        try(Connection c = ConnectionUtil.getConnection()){
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(queryAll);
            while(resultSet.next()){
                students.add(
                        student.builder()
                        .id(resultSet.getLong("MSSV"))
                        .sex(resultSet.getString("GTINH"))
                        .age((resultSet.getInt("TUOI")))
                        .average(resultSet.getDouble("DTB"))
                        .build());
            }
            return students;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static List<student> findByStudentMssv(Long mssv){
        List<student> students = new ArrayList<>();
        try(Connection c = ConnectionUtil.getConnection()){
            Statement statement = c.createStatement();
            ResultSet resultSet = statement.executeQuery(queryByMssv+mssv.toString());
            while(resultSet.next()){
                students.add(
                        student.builder()
                                .id(resultSet.getLong("MSSV"))
                                .sex(resultSet.getString("GTINH"))
                                .age((resultSet.getInt("TUOI")))
                                .average(resultSet.getDouble("DTB"))
                                .build());
            }
            return students;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public static int createNewStudent (student newStudent){
        try(Connection c = ConnectionUtil.getConnection()){
            PreparedStatement pst = c.prepareStatement(create);
            pst.setString((int)1, newStudent.getSex());
            pst.setInt((int)2, newStudent.getAge());
            pst.setDouble((int)3, newStudent.getAverage());

            if(pst.executeUpdate()>0){
                return 1;
            }
           return 0;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }
    }

    public static int updateStudentByMssv(student newStudent){
        try(Connection c = ConnectionUtil.getConnection()){
            PreparedStatement pst = c.prepareStatement(updateByMssv);
            pst.setString((int)1,newStudent.getSex());
            pst.setString((int)2,newStudent.getAge()+"");
            pst.setString((int)3,newStudent.getAverage()+"");
            pst.setString((int)4, newStudent.getId().toString());
            if(pst.executeUpdate()>0){
                return 1;
            }
            return 0;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }

    }
    public static int deleteStudentByMssv(Long mssv){
        try(Connection c = ConnectionUtil.getConnection()){
            PreparedStatement pst = c.prepareStatement(deleteByMssv);
            pst.setString((int)1,mssv.toString());
            if(pst.executeUpdate()>0){
                return 1;
            }
            return 0;
        }catch(Exception e){
            System.out.println(e.getMessage());
            return 0;
        }

    }



}

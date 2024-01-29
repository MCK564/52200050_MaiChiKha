package com.lab020.service;

import com.lab020.constants.MessageKey;
import com.lab020.models.student;
import com.lab020.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class StudentService {
    public static void showAllStudent(){
        List<student> students = StudentRepository.findAllStudent();
        if(students!=null){
            System.out.println("Number of students: "+ students.size());
        }
        students.forEach(System.out::println);
    }

    public static void showStudentByMssv(Long mssv){
        List<student> students = StudentRepository.findByStudentMssv(mssv);
        if(students!=null){
            System.out.println("Number of students: "+ students.size());
        }
        students.forEach(System.out::println);
    }

    public static void createNewStudent(student newStudent){
        if(StudentRepository.createNewStudent(newStudent)>0){
            System.out.println(MessageKey.create_successfully);
        }
        else System.out.println(MessageKey.create_failure);

    }

    public static void updateStudentByMssv(student requestStudent){
        if(!StudentRepository.findByStudentMssv(requestStudent.getId()).isEmpty()){
            if(StudentRepository.updateStudentByMssv(requestStudent)==1){
                System.out.println(MessageKey.update_successfully);
            }
           else System.out.println(MessageKey.update_failure);
        }
        else System.out.println(MessageKey.student_not_found+" với mssv = " + requestStudent.getId());
    }
    public static void deleteStudent(Long mssv){
        if(!StudentRepository.findByStudentMssv(mssv).isEmpty()){
            if(StudentRepository.deleteStudentByMssv(mssv)==1){
                System.out.println(MessageKey.delete_successfully);
            }
            else System.out.println(MessageKey.delete_failure);
        }
        else System.out.println(MessageKey.student_not_found+" với mssv = " + mssv.toString());
    }
}

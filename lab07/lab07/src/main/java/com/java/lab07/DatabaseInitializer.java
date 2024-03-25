package com.java.lab07;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.lang.System.out;

@Component
@RequiredArgsConstructor
public class DatabaseInitializer implements CommandLineRunner {
    private final StudentRepository studentRepository;

    @Override
    public void run(String... args) throws Exception {

        Student student1 = Student.builder()
                .name("John")
                .email("john@example.com")
                .ielts(7.5)
                .age(20)
                .build();

        Student student2 = Student.builder()
                .name("Alice")
                .email("alice@example.com")
                .ielts(6.8)
                .age(17)
                .build();

        Student student3 = Student.builder()
                .name("Bob")
                .email("bob@example.com")
                .ielts(6.0)
                .age(18)
                .build();

        studentRepository.saveAll(List.of(student1, student2, student3));


        out.println("Student List:");
        List<Student> students = studentRepository.findAll();
        students.forEach(out::println);


        Student studentToUpdate = students.get(0);
        studentToUpdate.setIelts(8.0);
        studentRepository.save(studentToUpdate);


        out.println("Student List after updating:");
        studentRepository.findAll().forEach(out::println);


        List<Student> existingStudents = studentRepository.findAllByAgeEquals(18);
        existingStudents.forEach(out::println);

        out.println(studentRepository.countStudentsByIeltsScore(5));

        List<Student> s = studentRepository.findAllByNameContains("B");
        s.forEach(out::println);


//        sorting and paging
        Sort sort= Sort.by(
                Sort.Order.desc("age"),
                Sort.Order.asc("ielts")
        );

        List<Student> sortList = studentRepository.findAll(sort);
        sortList.forEach(out::println);


        PageRequest pageRequest = PageRequest.of(0,10);
        Page<Student> page = studentRepository.findAll(pageRequest);

        if(page.getTotalElements()>5){
            List<Student> studentss = page.getContent().subList(3,6);
            studentss.forEach(out::println);
        }
    }

}

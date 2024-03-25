package com.java.lab07;

import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student,Long>, PagingAndSortingRepository<Student,Long> {
//Read a list of students whose age is greater than or equal to x, where x is the input
//parameter of the method
    List<Student> findAllByAgeEquals(int age);

//    @Query("SELECT COUNT(s) FROM Student s WHERE s.ielts = :ielts")
//    long countStudentsByIELTSScore(@Param("ielts") double ieltsScore);

    @Query("SELECT COUNT(s) FROM Student s WHERE s.ielts = :ielts")
    long countStudentsByIeltsScore(@Param("ielts") double ieltsScore);

    List<Student> findAllByNameContains(String name);


//    ex5
    @Query("SELECT s FROM Student s WHERE s.name LIKE '%:name%'")
    List<Student> customFindByName(@Param("name")String name);

    @Query("SELECT s FROM Student s WHERE s.age = :age")
    List<Student> customFindByAgeEqualge(@Param("age")String name);

    List<Student> findAll(Sort sort);
    Page<Student> findAll(Pageable pageable);


}

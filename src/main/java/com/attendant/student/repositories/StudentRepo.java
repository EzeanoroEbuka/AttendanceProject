package com.attendant.student.repositories;

import com.attendant.student.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentRepo extends JpaRepository<Student, Integer> {
    Student findStudentByFirstNameIgnoreCase(String firstName);
    Student findStudentByLastNameIgnoreCase(String lastName);
    Student findStudentByPhone(String phone);

}

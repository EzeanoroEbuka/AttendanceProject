package com.attendant.student.service;

import com.attendant.student.dtos.request.CreateStudentRequest;
import com.attendant.student.dtos.request.DeleteRequest;
import com.attendant.student.dtos.request.UpdateStudentRequest;
import com.attendant.student.dtos.response.AllStudent;
import com.attendant.student.dtos.response.CreateStudentResponse;
import com.attendant.student.dtos.response.DeleteResponse;
import com.attendant.student.dtos.response.UpdateStudentResponse;
import com.attendant.student.exceptions.StudentException;
import com.attendant.student.model.Student;
import com.attendant.student.repositories.StudentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentServiceImpl  implements StudentService{

    private final StudentRepo studentRepo;

    @Override
    public CreateStudentResponse createStudent(CreateStudentRequest student) {
        Student foundStudent = findStudentByPhone(student.getPhone());
        Student foundByFirstName = findStudentByFirstName(student.getFirstName());
        Student foundByLastName = findStudentByLastName(student.getLastName());
        if (foundStudent != null) {
            throw new StudentException("Student With this number already exists");
        }
        if(validateName(foundByFirstName,foundByLastName)){
            throw new StudentException("Student already exists");
        }
        Student newStudent = new Student();
        newStudent.setFirstName(student.getFirstName());
        newStudent.setLastName(student.getLastName());
        newStudent.setPhone(student.getPhone());
        Student savedStudent = studentRepo.save(newStudent);
        CreateStudentResponse response = new CreateStudentResponse();
        response.setId(savedStudent.getId());
        response.setMessage(student.getFirstName() + " Saved Successfully");
        return response;
    }

    @Override
    public UpdateStudentResponse updateStudent(UpdateStudentRequest update) {
        Student foundStudent = findStudentById(update.getStudentId());
        foundStudent.setFirstName(update.getNewFirstName());
        foundStudent.setLastName(update.getNewLastName());
        foundStudent.setPhone(update.getNewPhone());
        Student savedStudent = studentRepo.save(foundStudent);
        UpdateStudentResponse response = new UpdateStudentResponse();
        response.setId(savedStudent.getId());
        response.setMessage("Student Updated Successfully");
        return response;
    }

    @Override
    public DeleteResponse deleteStudent(DeleteRequest delete) {
        Student foundStudent = findStudentById(delete.getId());
        studentRepo.delete(foundStudent);
        DeleteResponse response = new DeleteResponse();
        response.setMessage("Student Deleted Successfully");
        return response;
    }

    @Override
    public AllStudent getAllStudent() {
        AllStudent students = new AllStudent();
        students.setStudents(studentRepo.findAll());
        students.setMessage("All students");
        return students;
    }

    @Override
    public void deleteAllStudents() {
        studentRepo.deleteAll();
    }

    private Student findStudentById(int id) {
        return studentRepo.findById(id).orElseThrow(() -> new StudentException("Student not found"));
    }
    private Student findStudentByPhone(String phone){
        return studentRepo.findStudentByPhone(phone);
    }
    private Student findStudentByFirstName(String name) {
        return studentRepo.findStudentByFirstNameIgnoreCase(name);
    }
    private Student findStudentByLastName(String name) {
        return studentRepo.findStudentByLastNameIgnoreCase(name);
    }
    private boolean validateName(Student foundByFirstName, Student foundByLastName) {
        if(foundStudentsNotNull(foundByFirstName, foundByLastName)){
            return foundByFirstName.getId() == foundByLastName.getId();
        }
        return false;
    }
    private boolean foundStudentsNotNull(Student foundByFirstName, Student foundByLastName) {
        return foundByFirstName != null && foundByLastName != null;
    }
}

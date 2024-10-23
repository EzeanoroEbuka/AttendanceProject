package com.attendant.student.service;


import com.attendant.student.dtos.request.CreateStudentRequest;
import com.attendant.student.dtos.request.DeleteRequest;
import com.attendant.student.dtos.request.UpdateStudentRequest;
import com.attendant.student.dtos.response.AllStudent;
import com.attendant.student.dtos.response.CreateStudentResponse;
import com.attendant.student.dtos.response.DeleteResponse;
import com.attendant.student.dtos.response.UpdateStudentResponse;

public interface StudentService {
    CreateStudentResponse createStudent(CreateStudentRequest student);

    UpdateStudentResponse updateStudent(UpdateStudentRequest update);

    DeleteResponse deleteStudent(DeleteRequest delete);

    AllStudent getAllStudent();

    void deleteAllStudents();

}
package com.attendant.student.service;

import com.attendant.student.dtos.request.CreateStudentRequest;
import com.attendant.student.dtos.request.DeleteRequest;
import com.attendant.student.dtos.request.UpdateStudentRequest;
import com.attendant.student.dtos.response.CreateStudentResponse;
import com.attendant.student.dtos.response.DeleteResponse;
import com.attendant.student.dtos.response.UpdateStudentResponse;
import com.attendant.student.exceptions.MyExceptions;
import com.attendant.student.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

class StudentServiceImplTest {

    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @BeforeEach
    void setUp() {
        studentServiceImpl.deleteAllStudents();
    }

    @Test
    void testThatNoStudentCreated() {
        assertEquals(0,studentServiceImpl.getAllStudent().getStudents().size());
    }
        @Test
    void createStudent() {
            CreateStudentRequest request = new CreateStudentRequest();
            request.setFirstName("First Name");
            request.setLastName("Last Name");
            request.setPhone("0903284343");
            CreateStudentResponse response = studentServiceImpl.createStudent(request);
            assertNotNull(response);
    }

    @Test
    void updateStudent() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setFirstName("First Name");
        request.setLastName("Last Name");
        request.setPhone("0903284343");
        CreateStudentResponse response = studentServiceImpl.createStudent(request);
        assertNotNull(response);
        UpdateStudentRequest request1 = new UpdateStudentRequest();
        request1.setStudentId(response.getId());
        request1.setNewFirstName("misha");
        request1.setNewLastName("job");
        UpdateStudentResponse newResponse = studentServiceImpl.updateStudent(request1);
        assertNotNull(newResponse);
    }

    @Test
    void deleteStudent() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setFirstName("First Name");
        request.setLastName("Last Name");
        request.setPhone("0903284343");
        CreateStudentResponse response = studentServiceImpl.createStudent(request);
        assertNotNull(response);
        DeleteRequest request1 = new DeleteRequest();
        request1.setId(response.getId());
        DeleteResponse deleteResponse = studentServiceImpl.deleteStudent(request1);
        assertEquals("Student Deleted Successfully",deleteResponse.getMessage());

    }

    @Test
    void getAllStudent() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setFirstName("First Name");
        request.setLastName("Last Name");
        request.setPhone("0903284343");
        studentServiceImpl.createStudent(request);
        new CreateStudentRequest();
        request.setFirstName("samuel");
        request.setLastName("Tin");
        request.setPhone("0809284343");
        studentServiceImpl.createStudent(request);
        assertEquals(2,studentServiceImpl.getAllStudent().getStudents().size());
    }

    @Test
    void testThatStudentCantBeRegisteredTwiceWithSamePhoneNumber() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setFirstName("samuel");
        request.setLastName("tin");
        request.setPhone("0903284343");
        studentServiceImpl.createStudent(request);
        CreateStudentRequest request2 = new CreateStudentRequest();
        request2.setFirstName("samuel");
        request2.setLastName("Tin");
        request2.setPhone("0903284343");
        assertThrows(MyExceptions.class, () -> studentServiceImpl.createStudent(request2),
                "Student With this number already exists");
        assertEquals(1, studentServiceImpl.getAllStudent().getStudents().size());
    }

    @Test
    void testThatStudentCantBeRegisteredTwiceWithSameNameInSameOrder() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setFirstName("samuel");
        request.setLastName("tin");
        request.setPhone("0903284343");
        studentServiceImpl.createStudent(request);
        CreateStudentRequest request2 = new CreateStudentRequest();
        request2.setFirstName("samuel");
        request2.setLastName("tin");
        request2.setPhone("0803284343");
        assertThrows(MyExceptions.class, () -> studentServiceImpl.createStudent(request2));
        assertEquals(1, studentServiceImpl.getAllStudent().getStudents().size());
    }
}

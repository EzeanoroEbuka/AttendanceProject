package com.attendant.student.service;

import com.attendant.student.dtos.request.*;
import com.attendant.student.dtos.response.AttendanceResponse;
import com.attendant.student.exceptions.MyExceptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AttendanceServiceImplTest {

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private StudentServiceImpl studentServiceImpl;

    @BeforeEach
    void setUp() {
        attendanceService.deleteAllAttendance();
    }

    @Test
    void testThatNoAttendanceIsYetCreated() {
        assertEquals(0, attendanceService.getAllAttendance().getAttendance().size());
    }


    @Test
    void testThatANewAttendanceIsCreated() {
        AttendanceRequest attendance = new AttendanceRequest();
        attendance.setStudentFirstName("Jane");
        attendance.setStudentLastName("Samuel");
        attendance.setPresent(true);
        AttendanceResponse response = attendanceService.createAttendance(attendance);
        assertEquals(1, attendanceService.getAllAttendance().getAttendance().size());
        assertEquals("Successfully created attendance", response.getMessage());
    }

    @Test
    void updateAttendance() {
        AttendanceRequest attendance = new AttendanceRequest();
        attendance.setStudentFirstName("Janet");
        attendance.setStudentLastName("Samuel");
        attendance.setPresent(true);
        AttendanceResponse response = attendanceService.createAttendance(attendance);
        assertEquals(1, attendanceService.getAllAttendance().getAttendance().size());
        assertEquals("Successfully created attendance", response.getMessage());
        UpdateRequest update = new UpdateRequest();
        update.setId(response.getId());
        update.setNewFirstName("david");
        update.setNewLastName("joy");
        update.setPresent(false);
        AttendanceResponse result = attendanceService.updateAttendance(update);
        assertEquals("Update successful", result.getMessage());
    }

    @Test
    void deleteAttendance() {
        AttendanceRequest attendance = new AttendanceRequest();
        attendance.setStudentFirstName("Janet");
        attendance.setStudentLastName("Samuel");
        attendance.setPresent(true);
        AttendanceResponse response = attendanceService.createAttendance(attendance);
        assertEquals(1, attendanceService.getAllAttendance().getAttendance().size());
        assertEquals("Successfully created attendance", response.getMessage());
        DeleteRequest delete = new DeleteRequest();
        delete.setId(response.getId());
        AttendanceResponse response1 = attendanceService.deleteAttendance(delete);
        assertEquals("Delete successful", response1.getMessage());
        assertEquals(0, attendanceService.getAllAttendance().getAttendance().size());
    }

    @Test
    void getAllAttendance() {
        AttendanceRequest attendance = new AttendanceRequest();
        attendance.setStudentFirstName("Janet");
        attendance.setStudentLastName("Samuel");
        attendance.setPresent(true);
        attendanceService.createAttendance(attendance);
        AttendanceRequest attendance2 = new AttendanceRequest();
        attendance2.setStudentFirstName("Janet");
        attendance2.setStudentLastName("Samuel");
        attendance2.setPresent(true);
        attendanceService.createAttendance(attendance2);
        assertEquals(2, attendanceService.getAllAttendance().getAttendance().size());

    }

    @Test
    void testThatAllAttendanceTakenInASimilarDateIsRetrieved() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setFirstName("david");
        request.setLastName("chima");
        request.setPhone("0903284343");
        studentServiceImpl.createStudent(request);
        CreateStudentRequest request2 = new CreateStudentRequest();
        request2.setFirstName("joshua");
        request2.setLastName("gideon");
        request2.setPhone("07054565343");
        studentServiceImpl.createStudent(request2);
        AttendanceRequest attendance = new AttendanceRequest();
        attendance.setStudentFirstName("david");
        attendance.setStudentLastName("chima");
        attendanceService.createAttendance(attendance);
        AttendanceRequest attendance2 = new AttendanceRequest();
        attendance2.setStudentFirstName("joshua");
        attendance2.setStudentLastName("gideon");
        attendanceService.createAttendance(attendance2);
        assertEquals(2, attendanceService.getAllAttendance().getAttendance().size());
        GetByDate dateSearch = new GetByDate();
        dateSearch.setDate("22-10-2024");
        assertEquals(2, attendanceService.getAttendanceByDate(dateSearch).getAttendance().size());
    }

    @Test
    void testThatAllAttendanceISNotTakenBySamePersonTwice() {
        CreateStudentRequest request = new CreateStudentRequest();
        request.setFirstName("david");
        request.setLastName("chima");
        request.setPhone("0903284343");
        studentServiceImpl.createStudent(request);
        CreateStudentRequest request2 = new CreateStudentRequest();
        request2.setFirstName("joshua");
        request2.setLastName("gideon");
        request2.setPhone("07054565343");
        studentServiceImpl.createStudent(request2);
        AttendanceRequest attendance = new AttendanceRequest();
        attendance.setStudentFirstName("joshua");
        attendance.setStudentLastName("gideon");
        attendanceService.createAttendance(attendance);
        AttendanceRequest attendance2 = new AttendanceRequest();
        attendance2.setStudentFirstName("joshua");
        attendance2.setStudentLastName("gideon");
        assertThrows(MyExceptions.class, () -> attendanceService.createAttendance(attendance2));
        assertEquals(1, attendanceService.getAllAttendance().getAttendance().size());
    }

}
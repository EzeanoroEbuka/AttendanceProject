package com.attendant.student.web;

import com.attendant.student.dtos.request.*;
import com.attendant.student.service.AttendanceService;
import com.attendant.student.service.StudentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
public class Controller {

    private final AttendanceService attendanceService;
    private final StudentService studentService;

    @PostMapping("/add_student")
    private ResponseEntity<?> addStudent(@RequestBody CreateStudentRequest newStudent){
        try{
            return new ResponseEntity<>(studentService.createStudent(newStudent), HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/create/attendance")
    private ResponseEntity<?> createAttendance(@RequestBody AttendanceRequest request){
        try{
            return new ResponseEntity<>(attendanceService.createAttendance(request), HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get_all/student")
    private ResponseEntity<?> getAllStudents(){
        try{
            return new ResponseEntity<>(studentService.getAllStudent(), HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/get_all/attendance")
    private ResponseEntity<?> getAllAttendances(){
        try{
            return new ResponseEntity<>(attendanceService.getAllAttendance(), HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("/update/student")
    private ResponseEntity<?> updateStudent(@RequestBody UpdateStudentRequest updateStudent){
        try{
            return new ResponseEntity<>(studentService.updateStudent(updateStudent), HttpStatus.ACCEPTED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PatchMapping("/update/attendance")
    private ResponseEntity<?> updateAttendance(@RequestBody UpdateRequest update){
        try{
            return new ResponseEntity<>(attendanceService.updateAttendance(update), HttpStatus.ACCEPTED);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get/attendance_by_date")
    private ResponseEntity<?> getAttendancesByDate(@RequestBody GetByDate date){
        try{
            return new ResponseEntity<>(attendanceService.getAttendanceByDate(date), HttpStatus.FOUND);
        }
        catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }




}


package com.attendant.student.service;

import com.attendant.student.dtos.request.GetByDate;
import com.attendant.student.exceptions.StudentException;
import com.attendant.student.model.Attendance;
import com.attendant.student.dtos.request.AttendanceRequest;
import com.attendant.student.dtos.request.DeleteRequest;
import com.attendant.student.dtos.request.UpdateRequest;
import com.attendant.student.dtos.response.AllAttendance;
import com.attendant.student.dtos.response.AttendanceResponse;
import com.attendant.student.model.Student;
import com.attendant.student.repositories.AttendanceRepo;
import com.attendant.student.repositories.StudentRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@AllArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepo repo;

    private final StudentRepo studentRepo;

    @Override
    public AttendanceResponse createAttendance(AttendanceRequest attendance) {

        if(validateStudent(attendance.getStudentFirstName(), attendance.getStudentLastName())) {
            if(signedInToday(attendance)){
                throw new StudentException("Student already Signed in Today");
            }
            Attendance newAttendance = new Attendance();
            newAttendance.setStudentFirstName(attendance.getStudentFirstName());
            newAttendance.setStudentLastName(attendance.getStudentLastName());
            newAttendance.setPresent(true);
            Attendance result = repo.save(newAttendance);
            AttendanceResponse attendanceResponse = new AttendanceResponse();
            attendanceResponse.setId(result.getId());
            attendanceResponse.setMessage("Successfully created attendance");
            return attendanceResponse;
        }
        throw new IllegalArgumentException("You are not Registered");
    }

    @Override
    public AttendanceResponse updateAttendance(UpdateRequest attendance) {
        Attendance foundAttendance = findById(attendance.getId());
        foundAttendance.setStudentFirstName(attendance.getNewFirstName());
        foundAttendance.setStudentLastName(attendance.getNewLastName());
        foundAttendance.setPresent(attendance.isPresent());
        repo.save(foundAttendance);
        AttendanceResponse response =  new AttendanceResponse();
        response.setId(foundAttendance.getId());
        response.setMessage("Update successful");
        return response;
    }

    @Override
    public AttendanceResponse deleteAttendance(DeleteRequest attendance) {
        Attendance foundAttendance = findById(attendance.getId());
        repo.delete(foundAttendance);
        AttendanceResponse response =  new AttendanceResponse();
        response.setMessage("Delete successful");
        return response;
    }

    @Override
    public AllAttendance getAllAttendance() {
        AllAttendance allAttendance = new AllAttendance();
        allAttendance.setAttendance(repo.findAll());
        allAttendance.setMessage("All attendance found");
        return allAttendance;
    }

    @Override
    public void deleteAllAttendance() {
        repo.deleteAll();
    }

    @Override
    public AllAttendance getAttendanceByDate(GetByDate request) {
        LocalDate date = LocalDate.parse(request.getDate(), DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        AllAttendance allAttendance = new AllAttendance();
        allAttendance.setAttendance(repo.findAttendanceByDate(date));
        allAttendance.setMessage("All attendance in the provided Date found");
        return allAttendance;
    }

    private Attendance findById(int id) {
       return repo.findById(id).orElseThrow(() -> new RuntimeException("Attendance not found"));
    }

    private boolean validateStudent(String firstName, String lastName) {
       Student foundStudentByFirstName = findByFirstName(firstName);
        Student foundStudentByLastName = findByLastName(lastName);
        return foundStudentByLastName.getId() == foundStudentByFirstName.getId();
    }

    private Student findByLastName(String name) {
        Student foundAStudentByLastName = studentRepo.findStudentByLastNameIgnoreCase(name);
        if(foundAStudentByLastName == null){
            throw new StudentException("Student not found");
        }
        return foundAStudentByLastName;
    }

    private Student findByFirstName(String name) {
        Student foundStudentByFirstName = studentRepo.findStudentByFirstNameIgnoreCase(name);
        if(foundStudentByFirstName == null){
            throw new StudentException("Student not found");
        }
        return foundStudentByFirstName;
    }

    private boolean signedInToday(AttendanceRequest request) {
        List<Attendance> allAttendance = repo.findAttendanceByDate(request.getDate());
        for (Attendance attendance : allAttendance){
            if(attendance.getStudentFirstName().equalsIgnoreCase(request.getStudentFirstName()) && attendance.getStudentLastName().equalsIgnoreCase(request.getStudentLastName())){
                return true;
            }
        }
        return false;
    }

}
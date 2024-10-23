package com.attendant.student.repositories;

import com.attendant.student.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepo extends JpaRepository<Attendance, Integer> {
    List<Attendance> findAttendanceByDate(LocalDate date);
}

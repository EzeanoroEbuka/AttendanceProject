package com.attendant.student.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Setter
@Getter
@Entity
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String studentFirstName;
    private String studentLastName;
    private LocalDate date = LocalDate.now();
    private LocalTime time = LocalTime.now();
    private boolean isPresent = true;
}

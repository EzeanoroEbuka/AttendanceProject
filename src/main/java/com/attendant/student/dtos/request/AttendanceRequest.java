package com.attendant.student.dtos.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class AttendanceRequest {

    private String studentFirstName;
    private String studentLastName;
    private boolean isPresent;
    private LocalDate date = LocalDate.now();
}

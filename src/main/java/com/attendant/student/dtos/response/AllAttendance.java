package com.attendant.student.dtos.response;

import com.attendant.student.model.Attendance;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AllAttendance {

    private List<Attendance> attendance;
    private String message;
}

package com.attendant.student.dtos.response;

import com.attendant.student.model.Student;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AllStudent {

    private List<Student> students;
    private String message;
}

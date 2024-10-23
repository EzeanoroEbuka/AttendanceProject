package com.attendant.student.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateStudentRequest {
    private int studentId;
    private String newFirstName;
    private String newLastName;
    private String newPhone;
}

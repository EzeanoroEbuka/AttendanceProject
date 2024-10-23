package com.attendant.student.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CreateStudentRequest {
    private String firstName;
    private String lastName;
    private String phone;
}

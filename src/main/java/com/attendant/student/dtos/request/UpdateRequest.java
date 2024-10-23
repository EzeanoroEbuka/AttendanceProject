package com.attendant.student.dtos.request;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UpdateRequest {
    private int id;
    private String newFirstName;
    private String newLastName;
    private boolean isPresent;
}

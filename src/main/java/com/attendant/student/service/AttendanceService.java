package com.attendant.student.service;

import com.attendant.student.dtos.request.AttendanceRequest;
import com.attendant.student.dtos.request.DeleteRequest;
import com.attendant.student.dtos.request.GetByDate;
import com.attendant.student.dtos.request.UpdateRequest;
import com.attendant.student.dtos.response.AllAttendance;
import com.attendant.student.dtos.response.AttendanceResponse;

public interface AttendanceService {
    AttendanceResponse createAttendance(AttendanceRequest attendance);
    AttendanceResponse updateAttendance(UpdateRequest attendance);
    AttendanceResponse deleteAttendance(DeleteRequest attendance);
    AllAttendance getAllAttendance();
    void deleteAllAttendance();

    AllAttendance getAttendanceByDate(GetByDate request);
}

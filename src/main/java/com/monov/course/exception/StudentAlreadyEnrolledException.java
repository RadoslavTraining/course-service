package com.monov.course.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAlreadyEnrolledException extends RuntimeException{

    private String courseId;
    private String studentId;

}

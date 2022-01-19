package com.monov.course.exception;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseNotFoundException extends RuntimeException{

    private Long courseId;

}

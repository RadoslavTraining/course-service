package com.monov.course.exception;

import com.monov.course.response.CourseResponseHandler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CourseResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(CourseNotFoundException.class)
    public ResponseEntity<String> handleCourseNotFoundException(CourseNotFoundException exception) {
        return CourseResponseHandler.generateErrorResponse(String.format("Course with id %d not found",exception.getCourseId()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StudentAlreadyEnrolledException.class)
    public ResponseEntity<String> handleStudentEnrolledException(StudentAlreadyEnrolledException exception){
        return CourseResponseHandler.generateErrorResponse(String.format("Student with id %d already enrolled in " +
                        "course with id %d" ,exception.getStudentId(),exception.getCourseId()),
                HttpStatus.CONFLICT);
    }

}

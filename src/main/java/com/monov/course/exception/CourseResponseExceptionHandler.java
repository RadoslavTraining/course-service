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
    public ResponseEntity<String> handleCourseNotFoundException() {
        return CourseResponseHandler.generateErrorResponse("Course with this id not found", HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StudentAlreadyEnrolledException.class)
    public ResponseEntity<String> handleStudentEnrolledException(){
        return CourseResponseHandler.generateErrorResponse("Student already enrolled in this course",
                HttpStatus.CONFLICT);
    }

}

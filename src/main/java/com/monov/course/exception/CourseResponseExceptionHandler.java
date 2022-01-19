package com.monov.course.exception;

import com.monov.commons.exceptions.ItemNotFoundException;
import com.monov.course.response.CourseResponseHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CourseResponseExceptionHandler extends ResponseEntityExceptionHandler {

    @Value("${exceptions.item-not-found.message}")
    private String itemNotFoundMessage;

    @Value("${exceptions.student-already-enrolled.message}")
    private String studentEnrolledMessage;

    @ExceptionHandler(ItemNotFoundException.class)
    public ResponseEntity<String> handleCourseNotFoundException(ItemNotFoundException exception) {
        return CourseResponseHandler.generateErrorResponse(
                String.format(
                        itemNotFoundMessage,
                        exception.getId()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(StudentAlreadyEnrolledException.class)
    public ResponseEntity<String> handleStudentEnrolledException(StudentAlreadyEnrolledException exception){
        return CourseResponseHandler.generateErrorResponse(
                String.format(
                        studentEnrolledMessage,
                        exception.getStudentId(),
                        exception.getCourseId()
                ),
                HttpStatus.CONFLICT);
    }

}

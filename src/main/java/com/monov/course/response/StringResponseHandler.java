package com.monov.course.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class StringResponseHandler {

    public static ResponseEntity<List<String>> generateListSuccessResponse(HttpStatus status,
                                                                         List<String> response) {
        return new ResponseEntity<>(response,status);
    }

    public static ResponseEntity<String> generateErrorResponse(String message, HttpStatus status){
        return new ResponseEntity<>(message,status);
    }


}
